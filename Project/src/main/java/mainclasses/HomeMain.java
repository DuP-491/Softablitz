package mainclasses;

import user.Streamer;
import user.User;
import user.Viewer;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HomeMain extends Thread {
    private Viewer selfViewer;
    private Streamer selfStreamer;

    public HomeMain(User user) {
        selfViewer = new Viewer(user.getUsername(),user.getName(),user.getBio(),user.getStatus());
        selfViewer.startServerConnection();
        selfStreamer = new Streamer(user.getUsername(),user.getName(),user.getBio(),user.getStatus());
        selfStreamer.startServerConnection();

    }

    public void run() {
        JFrame frame = new JFrame();
        HomePage hp = new HomePage(selfViewer, selfStreamer);
        hp.setNotifications(selfViewer.getNotifications()); //Set notificaitons

        frame.add(hp);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(800,600);

        frame.setTitle("Revenge Live: HomePage for " + selfViewer.getUsername());

        frame.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        selfViewer.end();
                        selfViewer = null;
                        selfStreamer.end();
                        selfStreamer = null;

                        super.windowClosing(e);
                    }
                }
        );
    }
}
