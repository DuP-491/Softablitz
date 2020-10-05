package user;

import stream.LiveStream;

public class Streamer extends User {
    private LiveStream myStream;

    public Streamer(String username, String password, String name, String bio, String dpLocation, Status status, LiveStream myStream) {
        super(username, password, name, bio, dpLocation, status);
        this.myStream = myStream;
    }

    public void stopStreaming() {
        //Handle stop streaming here
    }

    public void setMode(int mode) {
        myStream.setMode(mode);
    }
}
