package test;

import com.github.sarxos.webcam.Webcam;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

import javax.swing.JFrame;


public class MainClass extends Canvas{

    VideoMake v;
    boolean running = true;
    JFrame f;

    public void update(Graphics g) {
        try {
            g.drawString("Bruh",50,50);
            ImageIcon im = new ImageIcon(this.v.bufImage);
            im.paintIcon(this, g, 50, 60);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        MainClass m = new MainClass();
        m.f=new JFrame();
        m.f.add(m);
        m.v = new VideoMake();
        Thread t = new Thread(m.v);
        t.start();
        m.f.setSize(1000,800);
        m.f.setVisible(true);
        m.f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        while(m.running) {
            Graphics g = m.getGraphics();
            m.update(g);
        }
    }

}

class VideoMake extends Thread {

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
