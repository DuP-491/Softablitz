package sample;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import java.io.InputStream;
import javax.imageio.ImageIO;
import com.github.sarxos.webcam.Webcam;

public class VideoMake extends Thread {

    Webcam webcam;
    BufferedImage bufImage;

    public VideoMake() {
        webcam = Webcam.getDefault();
        webcam.open();
    }

    public void run()  {
        while(true) {
            try {
                bufImage = webcam.getImage();
            }
            catch(Exception e) {
                System.out.println("Meme");
            }
        }
    }
}
