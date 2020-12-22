package test;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class fireDetect {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    public static void main(String[] args) {
        String filePath = "E:\\FireImage\\1607275384(1).png";
        Mat inputImg = Imgcodecs.imread(filePath);

        CheckColor(inputImg);
    }

    public static void CheckColor(Mat inImage){
        Mat fireImg = new Mat();
        fireImg.create(inImage.size(),CvType.CV_8UC1);

        int redThre = 115;
        int saturationTh = 45;
        double[] rgb = new double[3];

        for(int i = 0; i < inImage.rows(); i++){
            for(int j = 0; j < inImage.cols(); j++){
                double B,G,R;
                rgb = inImage.get(i, j).clone();
                B = rgb[0];
                G = rgb[1];
                R = rgb[2];

                double maxValue = Math.max(Math.max(B, G), R);
                double minValue = Math.min(Math.min(B, G), R);

                double S = (1 -3.0 * minValue / (R + G + B));

                if(R > redThre && R >= G && G >= B && S >((255- R) * saturationTh / redThre)) {
                    fireImg.put(i, j, 255);
                }
                else {
                    fireImg.put(i, j, 0);
                }
                //Imgproc.dilate(fireImg, fireImg, new Mat(5, 5, CvType.CV_8UC1));
            }
        }
        Imgproc.dilate(fireImg, fireImg, new Mat());
        //ImageViewer imageViewer = new ImageViewer(fireImg);
        //imageViewer.imshow();
        DrawFire(inImage, fireImg);
    }

    public static void DrawFire(Mat inputImg,Mat foreImg){
        Mat hierarchy = new Mat();
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(foreImg, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);

        Mat result0 = new Mat();
        Scalar holeColor;
        Scalar externalColor;

        List<Rect> boundingRect = new ArrayList<Rect>(contours.size());
//        for(int i = 0; i < contours_set.size(); i++) {
//            //根据轮廓生成外包络矩形
//            Rect rect = Imgproc.boundingRect(contours_set.get(i));
//            boundingRect.add(rect);
//        }
//
//        for(int i = 0; i < contours_set.size(); i++){
//            Scalar color = new Scalar(0, 255, 0);
//            //绘制矩形
//            Imgproc.rectangle(inputImg, boundingRect.get(i).tl(), boundingRect.get(i).br(), color, 1, Core.LINE_8, 0);
//        }
        Point point1 = new Point();
        Point point2 = new Point();
        double a = 0.4;
        double b = 0.75;
        double xmin1 = a * inputImg.cols(), ymin1 = inputImg.rows(), xmax1 = 0, ymax1 = 0;
        double xmin2 = b * inputImg.cols(), ymin2 = inputImg.rows(), xmax2 = a * inputImg.cols(), ymax2 = 0;
        double xmin3 = inputImg.cols(), ymin3 = inputImg.rows(), xmax3 = b * inputImg.cols(), ymax3 = 0;
        Rect finalRect1 = new Rect();
        Rect finalRect2 = new Rect();
        Rect finalRect3 = new Rect();
        List<MatOfPoint2f> contours_set = new ArrayList<>();
        for(MatOfPoint point : contours){
            MatOfPoint2f newPoint = new MatOfPoint2f(point.toArray());
            contours_set.add(newPoint);
        }
        Iterator<MatOfPoint2f> it = contours_set.iterator();
        while(it.hasNext()){
            MatOfPoint2f point2f = it.next();
            Rect rect = Imgproc.boundingRect(point2f);
            float[] radius = new float[contours_set.size()];
            Point center = new Point();
            Imgproc.minEnclosingCircle(point2f, center, radius);
            if(rect.area() > 0){
                //Imgproc.rectangle(inputImg, rect.tl(), rect.br(),new Scalar(0, 255, 0));
                point1.x = rect.x;
                point1.y = rect.y;
                point2.x =point1.x + rect.width;
                point2.y = point1.y + rect.height;
                if (point2.x < a*inputImg.cols())
                {
                    if (point1.x < xmin1)
                        xmin1 = point1.x;
                    if (point1.y < ymin1)
                        ymin1 = point1.y;
                    if (point2.x > xmax1 && point2.x < xmax2)
                        xmax1 = point2.x;
                    if (point2.y > ymax1)
                        ymax1 = point2.y;
                }

                if (point2.x < b*inputImg.cols()&&point2.x > a*inputImg.cols())
                {
                    if (point1.x < xmin2 && point1.x>xmin1)
                        xmin2 = point1.x;
                    if (point1.y < ymin2)
                        ymin2 = point1.y;
                    if (point2.x > xmax2 && point2.x < xmax3)
                        xmax2 = point2.x;
                    if (point2.y > ymax2)
                        ymax2 = point2.y;
                }

                if (point2.x < inputImg.cols()&&point2.x > b*inputImg.cols())
                {
                    if (point1.x < xmin3 && point1.x>xmin2)
                        xmin3 = point1.x;
                    if (point1.y < ymin3)
                        ymin3 = point1.y;
                    if (point2.x > xmax3)
                        xmax3 = point2.x;
                    if (point2.y > ymax3)
                        ymax3 = point2.y;
                }
            }
            else
                contours_set.remove(point2f);
        }
        if (xmin1 == a*inputImg.cols()&& ymin1 == inputImg.rows()&&xmax1 == 0 && ymax1 == 0)
        {
            xmin1 = ymin1 = xmax1 = ymax1 = 0;
        }
        if (xmin2 == b*inputImg.cols()&& ymin2 == inputImg.rows()&& xmax2 == a*inputImg.cols()&& ymax2 == 0)
        {
            xmin2 = ymin2 = xmax2 = ymax2 = 0;
        }
        if (xmin3 == inputImg.cols()&&ymin3 == inputImg.rows()&& xmax3 == b*inputImg.cols()&& ymax3 == 0)
        {
            xmin3 = ymin3 = xmax3 = ymax3 = 0;
        }
        finalRect1 = new Rect((int)xmin1, (int)ymin1, (int)(xmax1 - xmin1), (int)(ymax1 - ymin1));
        finalRect2 = new Rect((int)xmin2, (int)ymin2, (int)(xmax2 - xmin2), (int)(ymax2 - ymin2));
        finalRect3 = new Rect((int)xmin3, (int)ymin3, (int)(xmax3 - xmin3), (int)(ymax3 - ymin3));
        Imgproc.rectangle(inputImg, finalRect1.tl(), finalRect1.br(), new Scalar(0, 255, 0));
        Imgproc.rectangle(inputImg, finalRect2.tl(), finalRect2.br(), new Scalar(0, 255, 0));
        Imgproc.rectangle(inputImg, finalRect3.tl(), finalRect3.br(), new Scalar(0, 255, 0));
        ImageViewer imageViewer = new ImageViewer(inputImg);
        imageViewer.imshow();
    }
}
