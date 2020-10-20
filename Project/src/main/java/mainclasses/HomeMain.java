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
        selfViewer.startServerConnection();
        selfStreamer = new Streamer(user.getUsername(),user.getName(),user.getBio(),user.getStatus());

    }

    public void run() {
        JFrame frame = new JFrame();
        frame.add(new HomePage(selfViewer, selfStreamer));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(800,600);
        frame.setTitle("Revenge Live: HomePage for " + selfViewer.getUsername());
    }
}
