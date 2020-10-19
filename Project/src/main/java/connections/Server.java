package connections;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private IDAssigner assigner;

    public Server() {
        assigner = new IDAssigner();
    }

    public void run() {
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
                Thread t = new Thread(new HandleClient(socket, this.assigner));
                t.start();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

        }
    }

}
