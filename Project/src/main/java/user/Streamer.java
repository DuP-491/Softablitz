package user;

import connections.ServerRequests;
import stream.Category;
import stream.LiveStream;
import stream.LiveStreamSource;

public class Streamer extends User {
    private LiveStreamSource myStream;

    public Streamer(String username, String name, String bio, Status status) {
        super(username, name, bio, status);
    }

    public void stopStreaming() {
        //Handle stop streaming here

        //Ask IDAssigner to free his id
        try {//Remove stream from db
            oos.writeInt(ServerRequests.RETURNID.geti());
            oos.flush();

            oos.writeInt(ServerRequests.REMOVESTREAMFROMDB.geti());
            oos.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        myStream.stopStreaming();
        myStream = null;
    }

    public void startStreaming(String title, Category cat) {
        //Handle start streaming here

        //Ask IDAssigner for id
        int id = -1;
        try {

            oos.writeInt(ServerRequests.ASSIGNID.geti());
            oos.flush();

            id = ois.readInt();

        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("id meme");
        }
        myStream = new LiveStreamSource(title, cat, id, this);

        try {//Add stream to db
            oos.writeInt(ServerRequests.ADDSTREAMTODB.geti());
            oos.flush();

            oos.writeUTF(title); oos.flush();
            oos.writeInt(cat.geti()); oos.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("add stream to db meme");
        }

        myStream.startStreaming();
        Thread t = new Thread(myStream);
        t.start();
    }

    public void changeMode() {
        myStream.changeMode();
    }

    public LiveStream getStream() {
        return this.myStream;
    }

    public void unmute() {
        myStream.unmute();
    }

    public void mute() {
        myStream.mute();
    }
}
