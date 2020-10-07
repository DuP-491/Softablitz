package sample.connections;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class VideoHandleClient implements Runnable {
    private Socket socket;
    ObjectInputStream objectInputStream;
    Displayer d;

    public VideoHandleClient(Socket socket) {
        this.socket = socket;
        try {
            objectInputStream = new ObjectInputStream(this.socket.getInputStream());
            d = new Displayer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        d.j.add(d);
        d.j.setSize(1000,800);
        d.j.setVisible(true);
        d.j.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        System.out.println("Newmemes");
        while (true) {
            try {
                d.im = (ImageIcon)objectInputStream.readUnshared();
                d.update();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
        }
    }
}

class Displayer extends Canvas {
    JFrame j;
    ImageIcon im;
    public Displayer() {
        j = new JFrame();
    }
    public void update() {
        try {
            Graphics g = this.getGraphics();
            g.drawString("Server",50,50);
            //System.out.println(b);
            im.paintIcon(this, g, 50, 60);
        } catch (Exception e) {
            System.out.println("Newmeme");
            e.printStackTrace();
        }
    }
}