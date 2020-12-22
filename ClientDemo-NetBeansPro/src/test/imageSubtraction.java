package test;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class imageSubtraction {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    public static void main(String[] args) {
        Mat image_std = Imgcodecs.imread("E:\\Pic\\3.jpg");
        Mat image = Imgcodecs.imread("E:\\Pic\\cutResult3.jpg");
        Mat image_detect = new Mat();
        Imgproc.resize(image, image_detect, image_std.size(), 0, 0);
        System.out.println(image_std.height() * image_std.width());
        System.out.println(image_detect.height() * image_detect.width());
        Mat image_blur_std = new Mat();
        Mat image_blur_detect = new Mat();
        Mat image_blur_gray_std = new Mat();
        Mat image_blur_gray_detect = new Mat();
        Imgproc.medianBlur(image_std,image_blur_std,5);
        Imgproc.medianBlur(image_detect,image_blur_detect,5);
        Imgproc.cvtColor(image_blur_std, image_blur_gray_std, Imgproc.COLOR_BGR2GRAY,0);
        Imgproc.cvtColor(image_blur_detect, image_blur_gray_detect, Imgproc.COLOR_BGR2GRAY,0);
        Mat image_sub = image_blur_gray_std.clone();
//        Core.subtract(image_blur_gray_std, image_blur_gray_detect, image_sub);
        Core.absdiff(image_blur_gray_std, image_blur_gray_detect, image_sub);
//        Mat erodeImage = new Mat();
//        Mat kernel = Imgproc.getStructuringElement(1, new Size(4,6));
//        Imgproc.erode(image_sub, erodeImage, kernel,new Point(-1,-1),1);
//        Imgcodecs.imwrite("E:\\Pic\\3.jpg", erodeImage);
//        int rowNumber = image_blur_gray_std.rows();
//        int colNumber = image_blur_gray_std.cols();
//        for(int j = 0; j < rowNumber; j++) {
//            for (int k = 0; k < colNumber; k++) {
//                if (image_blur_gray_std.get(j,k)[0] - image_blur_gray_detect.get(j,k)[0] < 0)
//                    image_sub.put(j,k,image_blur_gray_detect.get(j,k)[0] - image_blur_gray_std.get(j,k)[0]);
//                else
//                    image_sub.put(j,k,image_blur_gray_std.get(j,k)[0] - image_blur_gray_detect.get(j,k)[0]);
//                System.out.println(image_sub.get(j,k)[0]);
//            }
//        }

        //框出缺陷区域
        Mat img1 = image_std.clone();
        Mat threshImage = new Mat();
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();
        //检测边缘
        Imgproc.threshold(image_sub, threshImage, 150, 255, Imgproc.THRESH_BINARY);
        //找到轮廓
        Imgproc.findContours(threshImage, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
        List<Rect> boundRect = new ArrayList<Rect>(contours.size());
        for(int i = 0; i < contours.size(); i++) {
            //根据轮廓生成外包络矩形
            Rect rect = Imgproc.boundingRect(contours.get(i));
            boundRect.add(rect);
        }
        for(int i = 0; i < contours.size(); i++){
            Scalar color = new Scalar(0, 0, 255);
            //绘制矩形
            Imgproc.rectangle(img1, boundRect.get(i).tl(), boundRect.get(i).br(), color, 1, Core.LINE_8, 0);
        }

        ImageViewer imageViewer1 = new ImageViewer(image_blur_gray_std);
        ImageViewer imageViewer2 = new ImageViewer(image_blur_gray_detect);
        ImageViewer imageViewer3 = new ImageViewer(img1);
        imageViewer1.imshow();
        imageViewer2.imshow();
        imageViewer3.imshow();
    }
}
