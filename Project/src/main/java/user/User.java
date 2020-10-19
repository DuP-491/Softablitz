package user;


import java.io.DataOutputStream;
import java.net.Socket;

public class User {
    protected String username;
    protected String name;
    protected String bio;
    protected String dpLocation;
    protected Status status;
    protected Socket socket;

    public User(String username, String name, String bio, String dpLocation, Status status) {
        this.username = username;
        this.name = name;
        this.bio = bio;
        this.dpLocation = dpLocation;
        this.status = status;
    }

    public String getUsername() {
        return this.username;
    }

    public void startServerConnection() {
        try {
            socket = new Socket("localhost", 5436);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(username);
            out.flush();
            out.close();
            //Starts the connection and send username to server
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
