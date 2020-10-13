package user;

import stream.Category;
import stream.LiveStream;
import stream.LiveStreamSource;

public class Streamer extends User {
    private LiveStreamSource myStream;

    public Streamer(String username, String name, String bio, String dpLocation, Status status) {
        super(username, name, bio, dpLocation, status);
    }

    public void stopStreaming() {
        //Handle stop streaming here

        //Ask IDAssigner to free his id
        myStream.stopStreaming();
        uToDB.removeStreamfromDB(this);
    }

    public void startStreaming(String title, Category cat) {
        //Handle start streaming here

        //Ask IDAssigner for id
        int id = 0;
        myStream = new LiveStreamSource(title, cat, id);
        uToDB.addStreamtoDB(this);
    }

    public void setMode(int mode) {
        myStream.setMode(mode);
    }

    public LiveStream getStream() {
        return this.myStream;
    }

    @Override
    public String toString() {
        return (this.username + ": " + this.myStream.getTitle());
    }

}
