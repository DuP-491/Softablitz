package connections;

import connections.db.DBHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private IDAssigner assigner;
    private DBHandler dbHandler;

    public Server() {
        assigner = new IDAssigner();
        dbHandler = new DBHandler(assigner);
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
                Thread t = new Thread(new HandleClient(socket, this.assigner, this.dbHandler));
                t.start();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

        }
    }

}
