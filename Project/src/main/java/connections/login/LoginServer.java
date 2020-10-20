package connections.login;

import connections.db.LoginDB;
import connections.login.LoginClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LoginServer extends Thread {
    private LoginDB ldb;

    public LoginServer() {
        ldb = new LoginDB();
    }

    public void run() {
        ServerSocket serverSocket;
        Socket socket;
        System.out.println("Server started");
        try {
            serverSocket = new ServerSocket(5434);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        while (true) {
            try {
                socket = serverSocket.accept();
                Thread t = new Thread(new LoginClient(socket, this.ldb));
                t.start();
                System.out.println("Connection started");

            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

}
