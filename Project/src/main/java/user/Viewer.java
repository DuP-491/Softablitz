package user;

import chat.ChatMessage;
import connections.ServerRequests;
import stream.Category;
import stream.LiveStream;

import java.io.Serializable;
import java.time.LocalDateTime;


public class Viewer extends User implements Serializable {
    private LiveStream currentlyWatching;
    private User[] subList;
    private User[] followList;

    public Viewer(String username, String name, String bio, Status status) {
        super(username, name, bio, status);
    }

    public String[] getStreamsByCategory(Category cat) {
        try {
            oos.writeInt(ServerRequests.BROWSECATEGORY.geti());
            oos.flush();

            oos.writeInt(cat.geti());
            oos.flush();

            String streams[] = (String[]) ois.readObject();
            return streams;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Called whenever viewer starts watching anyone, or changes to someone else
    public void startWatching(String streamerUsername) {

        try {
            oos.writeInt(ServerRequests.GETSTREAM.geti()); oos.flush();

            oos.writeUTF(streamerUsername);
            oos.flush();

            currentlyWatching = (LiveStream) ois.readObject();
            System.out.println(currentlyWatching.getTitle());
            System.out.println(currentlyWatching.getCategory());
            System.out.println(currentlyWatching.getID());

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        currentlyWatching.startWatching();
        currentlyWatching.markAsViewer(this);
        Thread t = new Thread(currentlyWatching);
        t.start();

    }

    public void stopWatching() {
        currentlyWatching.stopWatching();
        currentlyWatching = null;
    }

    //will be called when viweing sub list or renewing/cancelling sub
    public void getSubList() {
        //query for subs in DB and update this.subList
    }

    //Will be called when viewing follow list or getting notifications in home screen
    public void getFollowList() {
        //followList = uToDB.getFollowList(this);
        //query for follows in DB and update this.followList
    }

    public void sendMessage(String content) {
        System.out.println(content);
        currentlyWatching.sendMessage(new ChatMessage(this.getUsername(),content,0));
    }

}
