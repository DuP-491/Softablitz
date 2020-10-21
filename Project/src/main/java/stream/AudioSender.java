package stream;

import javax.sound.sampled.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class AudioSender extends Thread {
    private String group;
    private MulticastSocket msocket;
    private DatagramPacket dpacket;
    private InetAddress ia;
    private static int PORT = 4444;

    TargetDataLine microphone;

    public static int CHUNK_SIZE = 1024;
    private byte[] data;

    private boolean running;

    private int numBytesRead;

    public AudioSender(String group) {
        try {
            this.group = group;
            ia = InetAddress.getByName(group);
            running = true;
        }
        catch(Exception e) {
           // e.printStackTrace();
        }
    }

    public void captureAudio() {
        numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
    }

    public void sendAudio() {
        try {
            msocket = new MulticastSocket();
            msocket.setTimeToLive(2);
            dpacket = new DatagramPacket(data, numBytesRead, ia, PORT);
            msocket.send(dpacket);
        }
        catch (Exception e) {
           // e.printStackTrace();
        }
    }

    public void stopThread() {
        microphone.close();
        running = false;
    }

    public void run() {
        try {
            AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);
            microphone.start();

            data = new byte[microphone.getBufferSize() / 5];
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Sending me dikkat");
        }
        while(running) {
            captureAudio();
            sendAudio();
        }
    }
}
