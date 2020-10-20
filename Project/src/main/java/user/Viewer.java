package user;

import chat.ChatMessage;
import connections.ServerRequests;
import stream.Category;
import stream.LiveStream;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;

public class Viewer extends User {
    private LiveStream currentlyWatching;
    private User[] subList;
    private User[] followList;

    public Viewer(String username, String name, String bio, Status status) {
        super(username, name, bio, status);
    }

    //Called whenever viewer starts watching anyone, or changes to someone else
    public void startWatching(String streamerUsername) {

        try {
            dos.writeInt(ServerRequests.GETSTREAM.geti()); dos.flush();

            dos.writeUTF(streamerUsername);
            dos.flush();

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            currentlyWatching = (LiveStream) ois.readObject();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        currentlyWatching.startWatching();
    }

    public void stopWatching() {
        currentlyWatching.stopWatching();
        currentlyWatching = null;
    }

    //will be called when viweing sub list or renewing/cancelling sub
    public void getSubList() {
        //query for subs in DB and update this.subList
    }

    //Will be called when viewing follow list or getting notifications in home screen
    public void getFollowList() {
        //followList = uToDB.getFollowList(this);
        //query for follows in DB and update this.followList
    }

}
