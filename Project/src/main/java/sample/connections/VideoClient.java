package sample.connections;

import com.github.sarxos.webcam.Webcam;
import com.xuggle.xuggler.demos.DecodeAndPlayAudioAndVideo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.Buffer;

public class VideoClient extends Canvas {

    private BufferedImage currentFrame;
    private ImageIcon im;
    private Webcam webcam;
    private Socket socket;
    private JFrame j;
    ObjectOutputStream objectOutputStream;

    public VideoClient() {
        try {
            webcam = Webcam.getDefault();
            webcam.setViewSize(new Dimension(640,480));
            webcam.open();
            socket = new Socket("localhost", 5436);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        }
        catch(Exception e) {

        }
    }

    public static void main(String args[]) {
        VideoClient v = new VideoClient();
        v.j = new JFrame();
        v.j.add(v);
        v.j.setSize(1000,800);
        v.j.setVisible(true);
        v.j.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        while(true) {
            v.captureCam();
            v.sendFrameToServer();
            v.update(v.getGraphics());
            //System.out.println("Done");
        }
    }

    public void sendFrameToServer()  {
        try {
            im = new ImageIcon(currentFrame);
            objectOutputStream.writeUnshared(im);
            objectOutputStream.flush();
            objectOutputStream.reset();
            //System.out.println("Sent");
        }
        catch(Exception e) {

        }
    }

    public void captureCam() {
        //Keep updating currentFrame by capturing webcam photos
        try {
            this.currentFrame = webcam.getImage();
        }
        catch(Exception e) {
            System.out.println("Meme");
        }
    }

    public void update(Graphics g) {
        try {
            g.drawString("Client",50,50);
            //System.out.println(currentFrame);
            im.paintIcon(this, g, 50, 60);
        } catch (Exception e) {
            System.out.println("Newmemesssss");
            e.printStackTrace();
        }
    }
}
