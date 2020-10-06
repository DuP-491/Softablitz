package user;

import chat.ChatMessage;

import java.time.LocalDateTime;

public class Viewer extends User {
    private Streamer currentlyWatching;
    private User[] subList;
    private User[] followList;

    public Viewer(String username, String name, String bio, String dpLocation, Status status) {
        super(username, name, bio, dpLocation, status);
    }

    //Called whenever viewer starts watching anyone, or changes to someone else
    public void startWatching(Streamer streamer) {
        this.currentlyWatching = streamer;
        //And call stream viewing window
    }

    //will be called when viweing sub list or renewing/cancelling sub
    public void getSubList() {
        //query for subs in DB and update this.subList
    }

    //Will be called when viewing follow list or getting notifications in home screen
    public void getFollowList() {
        followList = uToDB.getFollowList(this);
        //query for follows in DB and update this.followList
    }

    public void sendMessagetoAll(String message) {
        ChatMessage mes = new ChatMessage(this, currentlyWatching.getStream(), message, LocalDateTime.now(), 0);
        currentlyWatching.getStream().pushMessage(mes);
        //Create message, add to DB and push it to streamer's stream
    }

    public void sendMessagetoSub(String message) {
        ChatMessage mes = new ChatMessage(this, currentlyWatching.getStream(), message, LocalDateTime.now(), 1);
        currentlyWatching.getStream().pushMessage(mes);
        //Create message, add to DB and push it to streamer's stream
    }
}
