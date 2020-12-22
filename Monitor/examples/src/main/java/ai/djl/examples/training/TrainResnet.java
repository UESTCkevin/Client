package ai.djl.examples.training;

import ai.djl.*;
import ai.djl.basicdataset.Cifar10;
import ai.djl.basicmodelzoo.BasicModelZoo;
import ai.djl.basicmodelzoo.cv.classification.ResNetV1;
import ai.djl.examples.ImageFolder;
import ai.djl.examples.training.transferlearning.TrainResnetWithCifar10;
import ai.djl.examples.training.util.Arguments;
import ai.djl.inference.Predictor;
import ai.djl.metric.Metrics;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.transform.Normalize;
import ai.djl.modality.cv.transform.Resize;
import ai.djl.modality.cv.transform.ToTensor;
import ai.djl.modality.cv.translator.ImageClassificationTranslator;
import ai.djl.ndarray.types.Shape;
import ai.djl.nn.Block;
import ai.djl.nn.Blocks;
import ai.djl.nn.SequentialBlock;
import ai.djl.nn.SymbolBlock;
import ai.djl.nn.core.Linear;
import ai.djl.repository.Repository;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.DefaultTrainingConfig;
import ai.djl.training.EasyTrain;
import ai.djl.training.Trainer;
import ai.djl.training.TrainingResult;
import ai.djl.training.evaluator.Accuracy;
import ai.djl.training.listener.TrainingListener;
import ai.djl.training.loss.Loss;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.Pipeline;
import ai.djl.translate.TranslateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class TrainResnet {
    private static final Logger logger = LoggerFactory.getLogger(TrainResnetWithCifar10.class);
    public static void main(String[] args) {

    }
    protected TrainingResult train(Arguments arguments) throws IOException, TranslateException, ModelException {
        String trainingDatasetRoot = "src/test/resources/dataset/train";
        String validateDatasetRoot = "src/test/resources/dataset/validate";

        ImageFolder trainingDataset = new ImageFolder.Builder()
            .setRepository(Repository.newInstance("trainingDataset", Paths.get(trainingDatasetRoot)))
            .optPipeline(
                // create preprocess pipeline you want
                new Pipeline().add(new Resize(136, 102)).add(new ToTensor())).
            setSampling(32, true).
            build();
        // call prepare before using
        trainingDataset.prepare();

        ImageFolder validateDataset = new ImageFolder.Builder()
                .setRepository(Repository.newInstance("validateDataset", Paths.get(validateDatasetRoot)))
                .optPipeline(
                        // create preprocess pipeline you want
                        new Pipeline().add(new Resize(136, 102)).add(new ToTensor())).
                        setSampling(32, true).
                        build();
        // call prepare before using
        trainingDataset.prepare();

        try (Model model = getModel(arguments)) {
            DefaultTrainingConfig config = setupTrainingConfig(arguments);

            try (Trainer trainer = model.newTrainer(config)) {
                trainer.setMetrics(new Metrics());

                /*
                 * CIFAR10 is 32x32 image and pre processed into NCHW NDArray.
                 * 1st axis is batch axis, we can use 1 for initialization.
                 */
                Shape inputShape = new Shape(1, 3, 136, 102);

                // initialize trainer with proper input shape
                trainer.initialize(inputShape);
                EasyTrain.fit(trainer, arguments.getEpoch(), trainingDataset, validateDataset);

                TrainingResult result = trainer.getTrainingResult();
                model.setProperty("Epoch", String.valueOf(result.getEpoch()));
                model.setProperty(
                        "Accuracy",
                        String.format("%.5f", result.getValidateEvaluation("Accuracy")));
                model.setProperty("Loss", String.format("%.5f", result.getValidateLoss()));

                Path modelPath = Paths.get("build/model");
                model.save(modelPath, "resnetv1");

                Classifications classifications = testSaveParameters(model.getBlock(), modelPath);
                logger.info("Predict result: {}", classifications.topK(3));
                return result;
            }
        }
    }

    private static Model getModel(Arguments arguments)
            throws IOException, ModelNotFoundException, MalformedModelException {
        boolean isSymbolic = arguments.isSymbolic();
        boolean preTrained = arguments.isPreTrained();
        Map<String, String> options = arguments.getCriteria();
        Criteria.Builder<Image, Classifications> builder =
                Criteria.builder()
                        .optApplication(Application.CV.IMAGE_CLASSIFICATION)
                        .setTypes(Image.class, Classifications.class)
                        .optProgress(new ProgressBar())
                        .optArtifactId("resnet");
        if (isSymbolic) {
            // load the model
            builder.optGroupId("ai.djl.mxnet");
            if (options == null) {
                builder.optFilter("layers", "50");
                builder.optFilter("flavor", "v1");
            } else {
                builder.optFilters(options);
            }
            Model model = ModelZoo.loadModel(builder.build());
            SequentialBlock newBlock = new SequentialBlock();
            SymbolBlock block = (SymbolBlock) model.getBlock();
            block.removeLastBlock();
            newBlock.add(block);
            // the original model don't include the flatten
            // so apply the flatten here
            newBlock.add(Blocks.batchFlattenBlock());
            newBlock.add(Linear.builder().setUnits(10).build());
            model.setBlock(newBlock);
            if (!preTrained) {
                model.getBlock().clear();
            }
            return model;
        }
        // imperative resnet50
        if (preTrained) {
            builder.optGroupId(BasicModelZoo.GROUP_ID);
            if (options == null) {
                builder.optFilter("layers", "50");
                builder.optFilter("flavor", "v1");
                builder.optFilter("dataset", "cifar10");
            } else {
                builder.optFilters(options);
            }
            // load pre-trained imperative ResNet50 from DJL model zoo
            return ModelZoo.loadModel(builder.build());
        } else {
            // construct new ResNet50 without pre-trained weights
            Model model = Model.newInstance("resnetv1");
            Block resNet50 =
                    ResNetV1.builder()
                            .setImageShape(new Shape(3, 136, 102))
                            .setNumLayers(50)
                            .setOutSize(10)
                            .build();
            model.setBlock(resNet50);
            return model;
        }
    }

    private static Classifications testSaveParameters(Block block, Path path)
            throws IOException, ModelException, TranslateException {
        String synsetUrl =
                "https://mlrepo.djl.ai/model/cv/image_classification/ai/djl/mxnet/synset_cifar10.txt";
        ImageClassificationTranslator translator =
                ImageClassificationTranslator.builder()
                        .addTransform(new ToTensor())
                        .addTransform(new Normalize(Cifar10.NORMALIZE_MEAN, Cifar10.NORMALIZE_STD))
                        .optSynsetUrl(synsetUrl)
                        .optApplySoftmax(true)
                        .build();

        Image img = ImageFactory.getInstance().fromUrl("src/test/resources/airplane1.png");

        Criteria<Image, Classifications> criteria =
                Criteria.builder()
                        .setTypes(Image.class, Classifications.class)
                        .optModelUrls(path.toUri().toString())
                        .optTranslator(translator)
                        .optBlock(block)
                        .optModelName("resnetv1")
                        .build();

        try (ZooModel<Image, Classifications> model = ModelZoo.loadModel(criteria);
             Predictor<Image, Classifications> predictor = model.newPredictor()) {
            return predictor.predict(img);
        }
    }

    private static DefaultTrainingConfig setupTrainingConfig(Arguments arguments) {
        return new DefaultTrainingConfig(Loss.softmaxCrossEntropyLoss())
                .addEvaluator(new Accuracy())
                .optDevices(Device.getDevices(arguments.getMaxGpus()))
                .addTrainingListeners(TrainingListener.Defaults.logging(arguments.getOutputDir()));
    }
}
