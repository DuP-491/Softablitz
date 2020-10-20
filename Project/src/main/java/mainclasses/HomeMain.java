package mainclasses;

import user.Streamer;
import user.User;
import user.Viewer;

import javax.swing.*;

public class HomeMain extends Thread {
    private Viewer selfViewer;
    private Streamer selfStreamer;

    public HomeMain(User user) {
        selfViewer = new Viewer(user.getUsername(),user.getName(),user.getBio(),user.getStatus());
        selfStreamer = new Streamer(user.getUsername(),user.getName(),user.getBio(),user.getStatus());
    }

    public void run() {
        JFrame frame = new JFrame();
        frame.add(new HomePage());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(640,480);
        frame.setTitle("Revenge Live: HomePage for " + selfViewer.getUsername());
    }
}
