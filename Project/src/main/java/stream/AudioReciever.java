package stream;

import javax.sound.sampled.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class AudioReciever extends Thread {
    private String group;
    private MulticastSocket msocket;
    private DatagramPacket dpacket;
    private InetAddress ia;
    private static int PORT = 4444;

    SourceDataLine speakers;

    public static int CHUNK_SIZE = 1024;
    private byte[] data;

    private boolean running;
    private boolean isMuted;
    private LiveStream stream;

    public AudioReciever(String group, LiveStream stream) {
        try {
            this.group = group;
            ia = InetAddress.getByName(group);
            this.stream = stream;
            running = true;
        }
        catch(Exception e) {
            //e.printStackTrace();
        }
    }

    public void mute() {
        isMuted = true;
    }

    public void unmute() {
        isMuted = false;
    }

    public void recieveAndPlay() {
        try {
            if(isMuted) return;
            byte[] buffer = new byte[CHUNK_SIZE];
            dpacket = new DatagramPacket(buffer, buffer.length);
            msocket.receive(dpacket);
            data = dpacket.getData();
        }
        catch(Exception e) {
            //e.printStackTrace();
            System.out.println("Recieving 1 me dikkat");
        }
        try {
            speakers.write(data,0,CHUNK_SIZE);
        }
        catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Recieving 2 me dikkat");
        }
    }

    public void stopThread() {
        speakers.close();
        running = false;
    }

    public void run() {
        try {
            AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            speakers.open(format);
            speakers.start();

            msocket = new MulticastSocket(PORT);
            msocket.joinGroup(ia);
        }
        catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Recieving start me dikkat");
        }
        while(running) {
            recieveAndPlay();
        }
    }
}

