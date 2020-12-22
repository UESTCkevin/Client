package ai.djl.examples.inference;

import ai.djl.MalformedModelException;
import ai.djl.examples.training.TrainPikachu;
import ai.djl.translate.TranslateException;

import java.io.IOException;

public class PikachuDetection {
    public static void main(String[] args) throws MalformedModelException, TranslateException, IOException {
        TrainPikachu trainPikachu = new TrainPikachu();
        trainPikachu.predict("build/model", "E:\\Monitor\\examples\\src\\test\\resources\\pikachu.jpg");
    }
}
