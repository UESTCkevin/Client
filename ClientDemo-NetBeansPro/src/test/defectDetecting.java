package test;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.Arrays;

public class defectDetecting {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    public static void main(String[] args) {
        Mat grayImage_std = Imgcodecs.imread("E:\\Pic\\1.jpg", Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        Mat grayImage_detect = Imgcodecs.imread("E:\\Pic\\2.jpg", Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        Mat hist_1 = new Mat();
        Mat hist_2 = new Mat();
        MatOfFloat ranges = new MatOfFloat(0f, 256f);//颜色范围
        MatOfInt histSize = new MatOfInt(10000000);//直方图大小， 越大匹配越精确
        Imgproc.calcHist(Arrays.asList(grayImage_std), new MatOfInt(0), new Mat(), hist_1, histSize,ranges);
        Imgproc.calcHist(Arrays.asList(grayImage_detect), new MatOfInt(0), new Mat(), hist_2, histSize,ranges);
        ImageViewer imageViewer1 = new ImageViewer(grayImage_std);
        imageViewer1.imshow();
        ImageViewer imageViewer2 = new ImageViewer(grayImage_detect);
        imageViewer2.imshow();
        double result = Imgproc.compareHist(hist_1, hist_2,Imgproc.CV_COMP_CORREL);
        System.out.println(result);
    }
}
