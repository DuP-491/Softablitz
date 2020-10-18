package chat;

import stream.LiveStream;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MessageSender {
    private String group;
    private MulticastSocket msocket;
    private DatagramPacket dpacket;
    private InetAddress ia;
    private static int PORT = 4444;
    private LiveStream stream;

    public MessageSender(String group, LiveStream stream) {
        try {
            this.group = group;
            this.stream = stream;
            ia = InetAddress.getByName(group);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(ChatMessage message) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeUnshared(message);
            oos.flush();

            byte[] data = bos.toByteArray();
            msocket = new MulticastSocket();
            msocket.setTimeToLive(2);
            dpacket = new DatagramPacket(data, data.length, ia, PORT);
            msocket.send(dpacket);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
