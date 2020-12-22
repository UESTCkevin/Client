package test;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

public class ImageCut {
    public Mat cutImage(Mat src, Rect rect){
        Mat src_roi = new Mat(src, rect);
        Mat cutImage = new Mat();
        src_roi.copyTo(cutImage);
        return cutImage;
    }
}
