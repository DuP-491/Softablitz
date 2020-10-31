package stream;

import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class ImageSender extends Thread {


    private static final int CAPTURE_SCREEN_WIDTH = 1366;
    private static final int CAPTURE_SCREEN_HEIGHT = 768;
    private BufferedImage currentFrame;
    private ImageIcon im;
    private Webcam webcam;
    private Robot robot;
    private LiveStream stream;

    public static int HEADER_SIZE = 16;
    public static int MAX_PACKETS = 255;
    public static int SESSION_START = 128;
    public static int SESSION_END = 64;
    public static int DATAGRAM_MAX_SIZE = 65507 - HEADER_SIZE;
    public static int MAX_SESSION_NUMBER = 255;
    public static String OUTPUT_FORMAT = "jpg";

    public static int COLOUR_OUTPUT = BufferedImage.TYPE_INT_RGB;
    public static double SCALING = 0.5;
    public static int FRAMES_PER_SEC = 50;
    public String IP_ADDRESS;
    public static int PORT = 4444;
    public static boolean SHOW_MOUSEPOINTER = true;

    private boolean running;

    public ImageSender(String id, LiveStream stream) {
        try {
            IP_ADDRESS = id;
            this.stream = stream;
            webcam = Webcam.getDefault();
            webcam.setViewSize(new Dimension(640, 480));
            webcam.open();
            robot = new Robot();
            running = true;

        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    public static byte[] bufferedImageToByteArray(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, format, baos);
        return baos.toByteArray();
    }

    public static BufferedImage scale(BufferedImage source, int w, int h) {
        Image image = source.getScaledInstance(w, h, Image.SCALE_AREA_AVERAGING);
        BufferedImage result = new BufferedImage(w, h, COLOUR_OUTPUT);
        Graphics2D g = result.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return result;
    }

    public static BufferedImage shrink(BufferedImage source, double factor) {
        int w = (int) (source.getWidth() * factor);
        int h = (int) (source.getHeight() * factor);
        return scale(source, w, h);
    }

    public static BufferedImage copyBufferedImage(BufferedImage image) {
        BufferedImage copyOfIm = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D g = copyOfIm.createGraphics();
        g.drawRenderedImage(image, null);
        g.dispose();
        return copyOfIm;
    }

    private boolean sendImage(byte[] imageData, String multicastAddress,
                              int port) {
        InetAddress ia;
        boolean ret = false;
        int ttl = 2;

        try {
            ia = InetAddress.getByName(multicastAddress);
        } catch (UnknownHostException e) {
            System.out.println("image sending ip fucked");
            return ret;
        }

        MulticastSocket ms = null;

        try {
            ms = new MulticastSocket();
            ms.setTimeToLive(ttl);
            DatagramPacket dp = new DatagramPacket(imageData, imageData.length,
                    ia, port);
            ms.send(dp);
            ret = true;
        } catch (IOException e) {
            System.out.println("Image sending fucked 1");
            ret = false;
        } finally {
            if (ms != null) {
                ms.close();
            }
        }

        return ret;
    }

    public void stopThread() {
        webcam.close();
        running = false;
    }

    public void run() {
        int sessionNumber = 0;
        try {
            while (running) {
                if (stream.getMode() == StreamMode.WEBCAM) this.captureCam();
                else this.captureScreen();

                //currentFrame = scale(currentFrame, 640,480);

                byte[] imageByteArray = bufferedImageToByteArray(this.currentFrame, OUTPUT_FORMAT);
                int packets = (int) Math.ceil(imageByteArray.length / (float) DATAGRAM_MAX_SIZE);

                if (packets > MAX_PACKETS) {
                    System.out.println("Image is too large to be transmitted!");
                    continue;
                }
                long timeStamp=System.currentTimeMillis();
                byte[] timeStampArray=ByteBuffer.allocate(8).putLong(timeStamp).array();
                for (int i = 0; i <= packets; i++) {
                    int flags = 0;
                    flags = i == 0 ? flags | SESSION_START : flags;
                    flags = (i + 1) * DATAGRAM_MAX_SIZE > imageByteArray.length ? flags | SESSION_END : flags;

                    int size = (flags & SESSION_END) != SESSION_END ? DATAGRAM_MAX_SIZE : imageByteArray.length - i * DATAGRAM_MAX_SIZE;

                    byte[] data = new byte[HEADER_SIZE + size];
                    data[0] = (byte) flags;
                    data[1] = (byte) sessionNumber;
                    data[2] = (byte) packets;
                    data[3] = (byte) (DATAGRAM_MAX_SIZE >> 8);
                    data[4] = (byte) DATAGRAM_MAX_SIZE;
                    data[5] = (byte) i;
                    data[6] = (byte) (size >> 8);
                    data[7] = (byte) size;
                    data[8]=timeStampArray[0];
                    data[9]=timeStampArray[1];
                    data[10]=timeStampArray[2];
                    data[11]=timeStampArray[3];
                    data[12]=timeStampArray[4];
                    data[13]=timeStampArray[5];
                    data[14]=timeStampArray[6];
                    data[15]=timeStampArray[7];

                    System.arraycopy(imageByteArray, i * DATAGRAM_MAX_SIZE, data, HEADER_SIZE, size);
                    this.sendImage(data, this.IP_ADDRESS, PORT);

                    if ((flags & SESSION_END) == SESSION_END) break;
                }

                Thread.sleep(1000 / FRAMES_PER_SEC);
                sessionNumber = sessionNumber < MAX_SESSION_NUMBER ? ++sessionNumber : 0;
            }
        } catch (Exception e) {
            System.out.println("Image sending fucked 2");
        }
    }

    public void captureCam() {
        //Keep updating currentFrame by capturing  webcam photos
        try {
            this.currentFrame = webcam.getImage();
            im = new ImageIcon(currentFrame);
        } catch (Exception e) {
            System.out.println("Meme");
        }
    }

    public void captureScreen() {
        try {
            Dimension screenSize = new Dimension(CAPTURE_SCREEN_WIDTH,CAPTURE_SCREEN_HEIGHT); //Temporary, because scale method is very slow
            Rectangle screenRectangle = new Rectangle(screenSize);
            this.currentFrame = robot.createScreenCapture(screenRectangle);

        } catch (Exception e) {
            System.out.println("CApture screen fucked");
        }
    }

}
