package connections.login;

import connections.ServerRequests;
import connections.db.LoginDB;
import user.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LoginClient extends Thread {
    private Socket socket;
    private LoginDB ldb;
    private boolean running;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public LoginClient(Socket socket, LoginDB ldb) {
        try {
            this.socket = socket;
            this.ldb = ldb;
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

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
                int request = ois.readInt();
                System.out.println(request);

                if(ServerRequests.REGISTERUSER.compare(request)) { register(); }
                else if(ServerRequests.LOGIN.compare(request)) { login(); }
                else if(ServerRequests.ENDCONNECTION.compare(request)) { endConnection(); }
            }
            catch (Exception e) {
                e.printStackTrace();
                endConnection();
            }
        }
    }

    public void login() {//Unfinished
        try {
            String username = ois.readUTF();
            String password = ois.readUTF();

            User self = ldb.loginUser(username, password);
            if(self==null) {
                oos.writeInt(-1);
                oos.flush();
            }
            else {
                oos.writeInt(0);
                oos.writeObject(self);
                oos.flush();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void register() {
        try {
            String username = ois.readUTF();
            String name = ois.readUTF();
            String password = ois.readUTF();

            int result =  ldb.registerUser(username, name, password);

            oos.writeInt(result);
            oos.flush();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void endConnection() {
        running = false;
    }
}
