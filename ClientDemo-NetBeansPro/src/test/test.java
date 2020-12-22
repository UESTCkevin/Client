package test;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import static org.opencv.imgproc.Imgproc.rectangle;

public class test {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    public static void main(String[] args) {
        Mat mat = Imgcodecs.imread("E:\\Pic\\1606787939822.jpg");
        Mat dst = new Mat();
        //缩小图片
        Imgproc.resize(mat, dst,new Size(mat.cols()/2, mat.rows()/2), 0, 0, Imgproc.INTER_AREA);
        Imgcodecs.imwrite("E:\\Pic\\1606787939822.jpg", dst);
        ImageViewer imageViewer = new ImageViewer(dst);
        imageViewer.imshow();
//        //设置裁剪点
//        Rect rect = new Rect(700,370,100,60);
//        //设置框图点
//        Point leftUpPoint = new Point(700,370);
//        Point rightDownPoint = new Point(800,430);
//        //对目标区域框图
//        rectangle(dst,leftUpPoint,rightDownPoint, new Scalar(0,0,255));
//        Imgcodecs.imwrite("E:\\Pic\\1605772705935.jpg", dst);
//        //裁剪图片
//        ImageCut imageCut = new ImageCut();
//        Mat cutImage = imageCut.cutImage(dst, rect);
//        //保存裁剪的图片
//        Imgcodecs.imwrite("E:\\Pic\\cutResult.jpg", cutImage);
//        //展示框图后的图片
//        ImageViewer imageViewer = new ImageViewer(dst);
//        imageViewer.imshow();
    }
}
