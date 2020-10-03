import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import java.io.InputStream;
import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

public class VideoMake extends Thread {

    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    VideoCapture capture;
    BufferedImage bufImage;

    public VideoMake() {
        capture = new VideoCapture();
        capture.open(0);
    }

    public void run()  {
        while(true) {
            Mat frame = new Mat();
            this.capture.read(frame);
            MatOfByte buffer = new MatOfByte();
            try {
                Imgcodecs.imencode(".jpg", frame, buffer);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            byte[] byteArray = buffer.toArray();
            //Preparing the Buffered Image
            InputStream in = new ByteArrayInputStream(byteArray);
            try {
                bufImage = ImageIO.read(in);
            } catch (Exception E) {
                System.out.println("Meme");
            }
        }
    }
}
