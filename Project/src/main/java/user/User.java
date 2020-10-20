package user;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class User implements Serializable {
    protected String username;
    protected String name;
    protected String bio;
    protected Status status;
    protected Socket socket;

    protected DataOutputStream dos;
    protected DataInputStream dis;

    public User(String username, String name, String bio, Status status) {
        this.username = username;
        this.name = name;
        this.bio = bio;
        this.status = status;
    }

    public String getUsername() {
        return this.username;
    }
    public String getName() {
        return this.name;
    }
    public String getBio() {
        return this.bio;
    }
    public Status getStatus() { return this.status; }

    public void startServerConnection() {
        try {
            socket = new Socket("localhost", 5436);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());

            dos.writeUTF(username);
            dos.flush();
            //Starts the connection and send username to server

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
