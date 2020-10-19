package user;

import chat.ChatMessage;
import stream.LiveStream;

import java.time.LocalDateTime;

public class Viewer extends User {
    private LiveStream currentlyWatching;
    private User[] subList;
    private User[] followList;

    public Viewer(String username, String name, String bio, String dpLocation, Status status) {
        super(username, name, bio, dpLocation, status);
    }

    //Called whenever viewer starts watching anyone, or changes to someone else
    public void startWatching(Streamer streamer) {

        //currentlyWatching = uToDB.getLiveStream(streamer);
        currentlyWatching.startWatching();
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
