package sample.connections;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class VideoServer {
    public static void main(String args[]) {
        ServerSocket serverSocket;
        Socket socket;
        System.out.println("Server started");
        try {
            serverSocket = new ServerSocket(5436);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        while (true) {
            try {
                socket = serverSocket.accept();
                Thread t = new Thread(new VideoHandleClient(socket));
                t.start();
                System.out.println("Started");
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

        }
    }
}
