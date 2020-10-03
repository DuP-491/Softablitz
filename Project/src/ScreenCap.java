import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import java.io.InputStream;
import javax.imageio.ImageIO;

import org.opencv.core.Core;
import java.awt.Robot;

public class ScreenCap extends Thread {

    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    Robot robot;
    BufferedImage bufImage;

    public ScreenCap() {
        try {
            robot = new Robot();
        }
        catch(Exception e) {
            System.out.println("bruh");
        }
    }

    public void run()  {
        while(true) {
            try {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                Rectangle screenRectangle = new Rectangle(screenSize);
                bufImage = robot.createScreenCapture(screenRectangle);

            } catch (Exception E) {
                System.out.println("Meme");
            }
        }
    }
}
