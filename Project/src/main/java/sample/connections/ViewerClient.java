package sample.connections;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ViewerClient extends Thread {
    Displayer d;
    public static int HEADER_SIZE = 8;
    public static int SESSION_START = 128;
    public static int SESSION_END = 64;
    private static int DATAGRAM_MAX_SIZE = 65507;

    public String IP_ADDRESS;
    public static int PORT = 4444;

    public ViewerClient(String id) {
        try {
            IP_ADDRESS = "225.4.5." + id;
            d = new Displayer();
            d.j = new JFrame();
            d.j.add(d);
            d.j.setSize(1000,800);
            d.j.setVisible(true);
            d.j.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        boolean debug = false;

        InetAddress ia = null;
        MulticastSocket ms = null;
        try {
            /* Get address */
            ia = InetAddress.getByName(this.IP_ADDRESS);

            /* Setup socket and join group */
            ms = new MulticastSocket(PORT);
            ms.joinGroup(ia);

            int currentSession = -1;
            int slicesStored = 0;
            int[] slicesCol = null;
            byte[] imageData = null;
            boolean sessionAvailable = false;

            /* Setup byte array to store data received */
            byte[] buffer = new byte[DATAGRAM_MAX_SIZE];

            /* Receiving loop */
            while (true) {
                /* Receive a UDP packet */
                DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
                ms.receive(dp);
                byte[] data = dp.getData();

                /* Read header infomation */
                short session = (short) (data[1] & 0xff);
                short slices = (short) (data[2] & 0xff);
                int maxPacketSize = (int) ((data[3] & 0xff) << 8 | (data[4] & 0xff)); // mask

                short slice = (short) (data[5] & 0xff);
                int size = (int) ((data[6] & 0xff) << 8 | (data[7] & 0xff)); // mask

                /* If SESSION_START falg is set, setup start values */
                if ((data[0] & SESSION_START) == SESSION_START) {
                    if (session != currentSession) {
                        currentSession = session;
                        slicesStored = 0;
                        /* Consturct a appropreately sized byte array */
                        imageData = new byte[slices * maxPacketSize];
                        slicesCol = new int[slices];
                        sessionAvailable = true;
                    }
                }

                /* If package belogs to current session */
                if (sessionAvailable && session == currentSession) {
                    if (slicesCol != null && slicesCol[slice] == 0) {
                        slicesCol[slice] = 1;
                        System.arraycopy(data, HEADER_SIZE, imageData, slice
                                * maxPacketSize, size);
                        slicesStored++;
                    }
                }

                /* If image is complete dispay it */
                if (slicesStored == slices) {
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                    d.b = ImageIO.read(bis);
                    d.update();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ms != null) {
                try {
                    /* Leave group and close socket */
                    ms.leaveGroup(ia);
                    ms.close();
                } catch (IOException e) {
                }
            }
        }
    }
}

class Displayer extends Canvas {
    JFrame j;
    BufferedImage b;
    public Displayer() {
        j = new JFrame();
    }
    public void update() {
        try {
            Graphics g = this.getGraphics();
            g.drawString("Viewer",50,50);
            //System.out.println(b);
            ImageIcon im = new ImageIcon(this.b);
            im.paintIcon(this, g, 50, 60);
        } catch (Exception e) {
            System.out.println("Newmeme");
            e.printStackTrace();
        }
    }
}