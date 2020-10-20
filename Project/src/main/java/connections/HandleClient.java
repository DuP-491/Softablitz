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

    private boolean running;

    public HandleClient(Socket socket, IDAssigner assigner, DBHandler dbHandler) {
        this.socket = socket;
        this.assigner = assigner;
        this.dbHandler = dbHandler;
        running = true;

        try {
            DataInputStream din = new DataInputStream(socket.getInputStream());
            username = din.readUTF();
            din.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(running) {
            try {
                DataInputStream din = new DataInputStream(socket.getInputStream());
                int request = din.readInt();
                din.close();

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
        DataOutputStream out;
        try {
            out = new DataOutputStream(socket.getOutputStream());
            out.write(answer);
            out.flush();
            out.close();
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

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            title = dis.readUTF();
            category = dis.readInt();
            dis.close();

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
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String streamerUsername = dis.readUTF();
            dis.close();

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            LiveStream livestream = dbHandler.getStream(streamerUsername);
            oos.writeObject(livestream);
            oos.flush(); oos.close();
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
