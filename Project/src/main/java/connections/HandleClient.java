package connections;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class HandleClient extends Thread {
    private IDAssigner assigner;
    private Socket socket;
    private String username;

    private boolean running;

    public HandleClient(Socket socket, IDAssigner assigner) {
        this.socket = socket;
        this.assigner = assigner;
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

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

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

    public void returnID() {
        assigner.freeID(username);
    }

    public void getID(String streamerUsername) {
        int answer = assigner.getID(streamerUsername);
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

    public void endConnection() {
        running = false;
    }
}
