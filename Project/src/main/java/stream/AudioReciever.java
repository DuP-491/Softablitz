package stream;

import javax.sound.sampled.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class AudioReciever {
    private String group;
    private MulticastSocket msocket;
    private DatagramPacket dpacket;
    private InetAddress ia;
    private static int PORT = 4444;

    SourceDataLine speakers;

    public static int CHUNK_SIZE = 1024;
    private byte[] data;

    private boolean running;

    public AudioReciever() {
        try {
            this.group = group;
            ia = InetAddress.getByName(group);
            running = true;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void recieveAndPlay() {
        try {
            byte[] buffer = new byte[CHUNK_SIZE];
            dpacket = new DatagramPacket(buffer, buffer.length);
            msocket.receive(dpacket);
            data = dpacket.getData();
            
            speakers.write(data,0,CHUNK_SIZE);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopThread() {
        running = false;
    }

    public void run() {
        try {
            AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            speakers.open(format);
            speakers.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        while(running) {
            recieveAndPlay();
        }
    }
}

