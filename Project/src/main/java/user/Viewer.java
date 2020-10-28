package user;

import chat.ChatMessage;
import connections.ServerRequests;
import stream.Category;
import stream.LiveStream;

import javax.swing.*;
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
        try {
            oos.writeInt(ServerRequests.STOPWATCHING.geti());
            oos.flush();

            System.out.println("Sent " + ServerRequests.STOPWATCHING.geti());

            currentlyWatching.stopWatching();
            currentlyWatching = null;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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

    public int checkOutUser(String username) {
        try {
            oos.writeInt(ServerRequests.CHECKOUTUSER.geti());
            oos.flush();

            oos.writeUTF(username);
            oos.flush();

            User user = (User) ois.readObject();
            UserProfile userProfile = new UserProfile(user, this);
            userProfile.setVisible(true);
            userProfile.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            return 0;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void updateUserInfo(String newName, String newBio) {
        try {
            oos.writeInt(ServerRequests.UPDATEUSERINFO.geti());
            oos.flush();

            oos.writeUTF(newName);
            oos.flush();

            oos.writeUTF(newBio);
            oos.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void follow(String streamerUsername) {
        try {
            oos.writeInt(ServerRequests.FOLLOW.geti());
            oos.flush();

            oos.writeUTF(streamerUsername);
            oos.flush();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getNotifications() {
        String block = "";
        try {
            oos.writeInt(ServerRequests.GETNOTIFICATIONS.geti());
            oos.flush();

            String[] streamers = (String[]) ois.readObject();
            System.out.println(streamers.length);
            for(int i = 0; i < streamers.length; i++) { if(streamers[i]==null) break; block += streamers[i] + " is livestreaming.\n"; }

            return block;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return block;
    }

    public void unpause() {
        currentlyWatching.unpause();
    }

    public void pause() {
        currentlyWatching.pause();
    }

    public void unmute() {
        currentlyWatching.unmute();
    }

    public void mute() {
        currentlyWatching.mute();
    }

}
