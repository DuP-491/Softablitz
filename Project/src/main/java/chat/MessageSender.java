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
    ByteArrayOutputStream bos;
    ObjectOutputStream oos;

    public MessageSender(String group, LiveStream stream) {
        try {
            this.group = group;
            this.stream = stream;
            ia = InetAddress.getByName(group);
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);

            msocket = new MulticastSocket(PORT);
            msocket.joinGroup(ia);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(ChatMessage message) {
        try {
            oos.writeObject(message);
            oos.flush();

            byte[] data = bos.toByteArray();
            msocket = new MulticastSocket();
            msocket.setTimeToLive(2);
            dpacket = new DatagramPacket(data, data.length, ia, PORT);
            msocket.send(dpacket);

            msocket.close();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
