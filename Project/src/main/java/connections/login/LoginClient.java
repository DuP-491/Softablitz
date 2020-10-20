package connections.login;

import connections.ServerRequests;
import connections.db.LoginDB;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class LoginClient extends Thread {
    private Socket socket;
    private LoginDB ldb;
    private boolean running;
    DataInputStream dis;
    DataOutputStream dos;

    public LoginClient(Socket socket, LoginDB ldb) {
        try {
            this.socket = socket;
            this.ldb = ldb;
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            running = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(running) {
            try {
                System.out.println("Waiting");
                int request = dis.readInt();
                System.out.println(request);

                if(ServerRequests.REGISTERUSER.compare(request)) { register(); }
                else if(ServerRequests.ENDCONNECTION.compare(request)) { endConnection(); }
            }
            catch (Exception e) {
                e.printStackTrace();
                endConnection();
            }
        }
    }

    public int login() {//Unfinished
        try {
            String username = dis.readUTF();
            String password = dis.readUTF();
            dis.close();

            return 0;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public void register() {
        try {
            String username = dis.readUTF();
            String name = dis.readUTF();
            String password = dis.readUTF();

            int result =  ldb.registerUser(username, name, password);

            dos.writeInt(result);
            dos.flush();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void endConnection() {
        running = false;
    }
}
