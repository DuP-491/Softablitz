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
        myStream.stopStream();
    }

    public void startStreaming() {
        //Handle start streaming here
        myStream.startStream();
    }

    public void setMode(int mode) {
        myStream.setMode(mode);
    }

    @Override
    public String toString() {
        return (this.username + ": " + this.myStream.getTitle());
    }

    //Above three functions are explicitly declared here instead of calling LiveStream functions directly so that other methods related to display can be called from here
}
