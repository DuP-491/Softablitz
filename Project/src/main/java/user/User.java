package user;


import java.io.*;
import java.net.Socket;

public class User implements Serializable {
    protected String username;
    protected String name;
    protected String bio;
    protected Status status;
    protected Socket socket;

    protected ObjectOutputStream oos;
    protected ObjectInputStream ois;

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
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            oos.writeUTF(username);
            oos.flush();
            //Starts the connection and send username to server

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void end() {
        try {
            socket.close();
            oos.close();
            ois.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
