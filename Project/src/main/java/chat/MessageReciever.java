package chat;

import stream.LiveStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MessageReciever extends Thread {
    private String group;
    private MulticastSocket msocket;
    private DatagramPacket dpacket;
    private InetAddress ia;
    private static int PORT = 4444;
    private LiveStream stream;
    private boolean running;

    public MessageReciever(String group, LiveStream stream) {
        try {
            this.group = group;
            this.stream = stream;
            ia = InetAddress.getByName(group);
            running = true;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void recieveMessage() {
        try {
            byte[] buffer = new byte[65507];
            dpacket = new DatagramPacket(buffer, buffer.length);
            msocket.receive(dpacket);
            byte[] data = dpacket.getData();

            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(bis);
            ChatMessage message = (ChatMessage) ois.readObject();

            stream.pushMessage(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopThread() {
        running = false;
    }

    public void run() {
        while(running) {
            recieveMessage();
        }
    }
}
