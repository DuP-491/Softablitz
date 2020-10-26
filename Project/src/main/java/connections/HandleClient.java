package connections;

import connections.db.DBHandler;
import stream.Category;
import stream.LiveStream;

import java.io.DataInput;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.Time;
import java.util.Date;

public class HandleClient extends Thread {
    private IDAssigner assigner;
    private DBHandler dbHandler;
    private Socket socket;
    private String username;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private boolean running;

    public HandleClient(Socket socket, IDAssigner assigner, DBHandler dbHandler) {
        this.socket = socket;
        this.assigner = assigner;
        this.dbHandler = dbHandler;
        running = true;

        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            username = ois.readUTF();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        dbHandler.setOnline(username);

        while(running) {
            try {
                int request = ois.readInt();
                System.out.println(request);

                if(ServerRequests.ASSIGNID.compare(request)) { assignID(); }
                else if(ServerRequests.RETURNID.compare(request)) { returnID(); }
                else if(ServerRequests.ENDCONNECTION.compare(request)) { endConnection(); }
                else if(ServerRequests.GETSTREAM.compare(request)) { getStreamInfo(); }
                else if(ServerRequests.ADDSTREAMTODB.compare(request)) { addStreamTODB(); }
                else if(ServerRequests.REMOVESTREAMFROMDB.compare(request)) { removeStreamfromDB(); }
                else if(ServerRequests.BROWSECATEGORY.compare(request)) { getStreamsByCategory(); }
            }
            catch (Exception e) {
                e.printStackTrace();
                endConnection(); //Temporary
            }
        }
    }

    //Viewer method
    public void getStreamsByCategory() {
        try {
            int catno = ois.readInt();
            Category cat = Category.IRL;
            for(Category i : Category.values()) { if(i.compare(catno)) { cat = i; break; } }

            String ans[] = dbHandler.browseCategory(cat);
            oos.writeObject(ans);
            oos.flush();
        }
        catch (Exception e) {

        }
    }

    //Streamer method
    public void assignID() {
        int answer = assigner.assignID(username);
        try {
            oos.writeInt(answer);
            oos.flush();
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

            title = ois.readUTF();
            category = ois.readInt();

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
            String streamerUsername = ois.readUTF();

            LiveStream livestream = dbHandler.getStream(streamerUsername);
            System.out.println(livestream.getTitle());
            System.out.println(livestream.getCategory());
            System.out.println(livestream.getID());

            oos.writeObject(livestream);
            oos.flush();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    //User method
    public void endConnection() {
        try {
            dbHandler.setOffline(username);

            running = false;
            socket.close();
            oos.close();
            ois.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
