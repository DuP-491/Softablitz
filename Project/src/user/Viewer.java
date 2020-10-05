package user;

public class Viewer extends User {
    private Streamer currentlyWatching;
    private User[] subList;
    private User[] followList;

    public Viewer(String username, String password, String name, String bio, String dpLocation, Status status) {
        super(username, password, name, bio, dpLocation, status);
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
        //query for follows in DB and update this.followList
    }

    public void sendMessagetoAll() {
        //Create message, add to DB and push it to streamer's stream
    }

    public void sendMessagetoSub() {
        //Create message, add to DB and push it to streamer's stream
    }
}
