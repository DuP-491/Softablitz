package user;

import connections.ServerRequests;
import stream.Category;
import stream.LiveStream;
import stream.LiveStreamSource;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Streamer extends User {
    private LiveStreamSource myStream;

    public Streamer(String username, String name, String bio, Status status) {
        super(username, name, bio, status);
    }

    public void stopStreaming() {
        //Handle stop streaming here

        //Ask IDAssigner to free his id
        try {
            dos.writeInt(ServerRequests.RETURNID.geti());
            dos.flush();

            dos.writeInt(ServerRequests.REMOVESTREAMFROMDB.geti());
            dos.flush();
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
            dos.writeInt(ServerRequests.ASSIGNID.geti());
            dos.flush();

            id = dis.readInt();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        myStream = new LiveStreamSource(title, cat, id, this);

        try {
            dos.writeInt(ServerRequests.ADDSTREAMTODB.geti());
            dos.flush();

            dos.writeUTF(title); dos.flush();
            dos.writeInt(cat.geti()); dos.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        myStream.startStreaming();
    }

    public void setMode(int mode) {
        myStream.setMode(mode);
    }

    public LiveStream getStream() {
        return this.myStream;
    }
}
