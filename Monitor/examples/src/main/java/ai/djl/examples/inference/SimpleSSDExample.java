package ai.djl.examples.inference;

import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.mxnet.zoo.MxModelZoo;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SimpleSSDExample {
    public static void main(String[] args) throws Exception{
        // Get image file path
        Path imageFile = Paths.get("E:\\Monitor\\examples\\src\\test\\resources\\pikachu.jpg");
        Image img = ImageFactory.getInstance()
                .fromFile(imageFile);
        //Get resnet model from model zoo.
        ZooModel<Image, DetectedObjects> model =
                MxModelZoo.SSD.loadModel(new ProgressBar());
        //Create a new predictor from model and predict on image.
        DetectedObjects predictResult = model.newPredictor().predict(img);
        // Draw Bounding boxes on image
        img.drawBoundingBoxes(predictResult);
        //Save result
        String outputDir = "build/model";
        Path out = Paths.get(outputDir).resolve("ssd.png");
        img.save(Files.newOutputStream(out), "png");
        model.close();
    }
}