package test;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.LinkedList;
import java.util.List;

public class akaze {
    private static float nndrRatio = 0.7f;
    private static int matchesPointCount = 0;

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    public static void main(String[] args) throws Exception {
        Mat templateImage = Imgcodecs.imread("E:\\Pic\\3.jpg");
        Mat originalImage = Imgcodecs.imread("E:\\Pic\\testImage3.jpg");
        if (templateImage.empty() || originalImage.empty()) {
            throw new Exception("no file");
        }

        MatOfKeyPoint templateKeyPoints = new MatOfKeyPoint();
        MatOfKeyPoint originalKeyPoints = new MatOfKeyPoint();

        FeatureDetector sifDetecotr = FeatureDetector.create(FeatureDetector.AKAZE);//指定特征点算法AKAZE

        sifDetecotr.detect(templateImage,templateKeyPoints);//在图像1中检测关键点。
        sifDetecotr.detect(originalImage,originalKeyPoints);//在图像2中检测关键点。

        DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.AKAZE);//创建一个描述符提取器。

        Mat templateDescriptors = new Mat(templateImage.rows(), templateImage.cols(), templateImage.type());
        extractor.compute(templateImage,templateKeyPoints,templateDescriptors);//计算在一个图像1中检测到的一组关键点的描述符。
        Mat originalDescriptors = new Mat(originalImage.rows(), originalImage.cols(), originalImage.type());
        extractor.compute(originalImage,originalKeyPoints,originalDescriptors);//计算在一个图像2中检测到的一组关键点的描述符。

        List<MatOfDMatch> matches = new LinkedList<>();
        DescriptorMatcher descriptorMatcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);//创建给定类型的描述符matcher。
        /**
         * knnMatch方法的作用就是在给定特征描述集合中寻找最佳匹配
         * 使用KNN-matching算法，令K=2，则每个match得到两个最接近的descriptor，然后计算最接近距离和次接近距离之间的比值，当比值大于既定值时，才作为最终match。
         */
        descriptorMatcher.knnMatch(templateDescriptors, originalDescriptors, matches, 2);
        LinkedList<DMatch> goodMatchesList = new LinkedList<>();

        //对匹配结果进行筛选，依据distance进行筛选
        matches.forEach(match -> {
            DMatch[] dmatcharray = match.toArray();
            DMatch m1 = dmatcharray[0];
            DMatch m2 = dmatcharray[1];

            if(m1.distance <= m2.distance * nndrRatio){
                goodMatchesList.addLast(m1);
            }
        });

        matchesPointCount = goodMatchesList.size();
        if (matchesPointCount >= 10) {
            System.out.println("模板图在原图匹配成功！");

            List<KeyPoint> templateKeyPointList = templateKeyPoints.toList();
            List<KeyPoint> originalKeyPointList = originalKeyPoints.toList();
            LinkedList<Point> objectPoints = new LinkedList();
            LinkedList<Point> scenePoints = new LinkedList();

            goodMatchesList.forEach(goodMatch -> {
                objectPoints.addLast(templateKeyPointList.get(goodMatch.queryIdx).pt);
                scenePoints.addLast(originalKeyPointList.get(goodMatch.trainIdx).pt);
            });

            MatOfPoint2f objMatOfPoint2f = new MatOfPoint2f();
            objMatOfPoint2f.fromList(objectPoints);
            MatOfPoint2f scnMatOfPoint2f = new MatOfPoint2f();
            scnMatOfPoint2f.fromList(scenePoints);
            //使用 findHomography 寻找匹配上的关键点的变换
            Mat homography = Calib3d.findHomography(objMatOfPoint2f, scnMatOfPoint2f, Calib3d.RANSAC, 3);

            /**
             * 透视变换是将图片投影到一个新的视平面，也称作投影映射。
             */
            Mat templateCorners = new Mat(4, 1, CvType.CV_32FC2);
            Mat templateTransformResult = new Mat(4, 1, CvType.CV_32FC2);
            templateCorners.put(0, 0, new double[]{0, 0});
            templateCorners.put(1, 0, new double[]{templateImage.cols(), 0});
            templateCorners.put(2, 0, new double[]{templateImage.cols(), templateImage.rows()});
            templateCorners.put(3, 0, new double[]{0, templateImage.rows()});
            //使用 perspectiveTransform 将模板图进行透视变以矫正图象得到标准图片
            Core.perspectiveTransform(templateCorners, templateTransformResult, homography);

            //矩形四个顶点  匹配的图片经过旋转之后就这个矩形的四个点的位置就不是正常的abcd了
            double[] pointA = templateTransformResult.get(0, 0);
            double[] pointB = templateTransformResult.get(1, 0);
            double[] pointC = templateTransformResult.get(2, 0);
            double[] pointD = templateTransformResult.get(3, 0);

            Imgproc.rectangle(originalImage, new Point(pointA), new Point(pointC), new Scalar(0, 255, 0));

            MatOfDMatch goodMatches = new MatOfDMatch();
            goodMatches.fromList(goodMatchesList);
            Mat matchOutput = new Mat(originalImage.rows(), originalImage.cols(), Imgcodecs.IMREAD_COLOR);
            Features2d.drawMatches(templateImage, templateKeyPoints, originalImage, originalKeyPoints, goodMatches, matchOutput, new Scalar(0, 255, 0), new Scalar(255, 0, 0), new MatOfByte(), 2);
            ImageViewer imageViewer = new ImageViewer(originalImage);
            imageViewer.imshow();
            Rect rect = new Rect(new Point(pointA), new Point(pointC));
            ImageCut imageCut = new ImageCut();
            Mat cutImage = imageCut.cutImage(originalImage, rect);
            Imgcodecs.imwrite("E:\\Pic\\cutResult3.jpg", cutImage);
        }
//        MatOfDMatch matches = new MatOfDMatch();
//        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);//创建给定类型的描述符matcher。
//
//        matcher.match(descriptor1,descriptor2,matches);//从查询集中找到每个描述符的最佳匹配。
//
//        Mat dst = new Mat();
//        Features2d.drawMatches(templateImage,templateKeyPoints,originalImage,originalKeyPoints,matches,dst);//从两个图像中提取出的关键点匹配。
//
//        System.out.println(templateKeyPoints.toList());
//
//        ImageViewer imageViewer = new ImageViewer(dst);
//        imageViewer.imshow();
    }
}
