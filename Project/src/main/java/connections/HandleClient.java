package connections;

import connections.db.DBHandler;
import stream.LiveStream;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Time;
import java.util.Date;

public class HandleClient extends Thread {
    private IDAssigner assigner;
    private DBHandler dbHandler;
    private Socket socket;
    private String username;
    private DataInputStream dis;
    private DataOutputStream dos;

    private boolean running;

    public HandleClient(Socket socket, IDAssigner assigner, DBHandler dbHandler) {
        this.socket = socket;
        this.assigner = assigner;
        this.dbHandler = dbHandler;
        running = true;

        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());

            username = dis.readUTF();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(running) {
            try {
                int request = dis.readInt();

                if(ServerRequests.ASSIGNID.compare(request)) { assignID(); }
                else if(ServerRequests.RETURNID.compare(request)) { returnID(); }
                else if(ServerRequests.ENDCONNECTION.compare(request)) { endConnection(); }
                else if(ServerRequests.GETSTREAM.compare(request)) { getStreamInfo(); }
                else if(ServerRequests.ADDSTREAMTODB.compare(request)) { addStreamTODB(); }
                else if(ServerRequests.REMOVESTREAMFROMDB.compare(request)) { removeStreamfromDB(); }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Streamer method
    public void assignID() {
        int answer = assigner.assignID(username);
        try {
            dos.write(answer);
            dos.flush();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    //Streamer method
    public void returnID() {
        assigner.freeID(username);
    }

    //Streamer method
    public void addStreamTODB() {
        try {
            String title = null;
            int category;
            Date time = new Date(); //stores current time according to server
            java.sql.Timestamp starttime = new java.sql.Timestamp(time.getTime());

            title = dis.readUTF();
            category = dis.readInt();

            dbHandler.addStreamtoDB(username, title, category, starttime);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Streamer method
    public void removeStreamfromDB() {
        try {
            dbHandler.removeStreamfromDB(username);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Viewer method
    public void getStreamInfo() {
        try {
            String streamerUsername = dis.readUTF();

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            LiveStream livestream = dbHandler.getStream(streamerUsername);
            oos.writeObject(livestream);
            oos.flush();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    //User method
    public void endConnection() {
        running = false;
    }
}
