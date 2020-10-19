package user;

import chat.ChatMessage;
import stream.Category;
import stream.LiveStream;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.time.LocalDateTime;

public class Viewer extends User {
    private LiveStream currentlyWatching;
    private User[] subList;
    private User[] followList;

    public Viewer(String username, String name, String bio, String dpLocation, Status status) {
        super(username, name, bio, dpLocation, status);
    }

    //Called whenever viewer starts watching anyone, or changes to someone else
    public void startWatching(String streamerUsername) {
        Streamer streamer = null;
        String title = null;
        Category cat = null;
        int id = -1;

        //get title, streamer and category from DB
        //get id from server
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            id = dis.readInt();
            dis.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        currentlyWatching = new LiveStream(title, cat, id, streamer);
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
