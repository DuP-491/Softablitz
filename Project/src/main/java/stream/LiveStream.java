package stream;

import chat.ChatMessage;
import com.xuggle.xuggler.demos.DisplayWebcamVideo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

import com.github.sarxos.webcam.Webcam;

enum Category {
    LEAGUEOFLEGENDS,
    DOTA2,
    ROCKETLEAGUE,
    GTAV,
    MINECRAFT,
    INDIEGAMES,
    IRL
}

enum StreamMode {
    WEBCAM,
    SCREENCAP
}

public class LiveStream extends Thread {
    private BufferedImage currentFrame;
    private int viewCount;
    private LocalDateTime startedAtTime;
    private Category category;
    private String title;
    private StreamMode mode;
    private boolean running;
    private LinkedList<ChatMessage> allUsersMessageQueue;
    private LinkedList<ChatMessage> subOnlyMessageQueue;

    private int chatCapacity = 200;

    private Robot robot;
    private Webcam webcam;

    public LiveStream(String title, Category cat) {
        this.startedAtTime = LocalDateTime.now();
        this.category = cat;
        this.title = title;
        allUsersMessageQueue = new LinkedList<>();
        subOnlyMessageQueue = new LinkedList<>();
        try{
            robot = new Robot();
            webcam = Webcam.getDefault();
        }
        catch(Exception e) {}
    }

    public void stopStream() {
        running = false;
    }

    public void startStream() {
        running = true;
    }

    public void setMode(int mode) {
        if(mode == 1) { this.mode = StreamMode.WEBCAM; webcam.open(); }
        else this.mode = StreamMode.SCREENCAP;
    }

    public void pushMessage(ChatMessage message) {
        int access = message.getAccess();
        if(access == 0) {
            if(allUsersMessageQueue.size()==chatCapacity) allUsersMessageQueue.removeFirst();
            allUsersMessageQueue.addLast(message);
        }
        else {
            if(subOnlyMessageQueue.size()==chatCapacity) subOnlyMessageQueue.removeFirst();
            subOnlyMessageQueue.addLast(message);
        }
    }

    public void captureScreen() {
        //Keep updating currentFrame by capturing screenshots
        try {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle screenRectangle = new Rectangle(screenSize);
            this.currentFrame = robot.createScreenCapture(screenRectangle);

        } catch (Exception E) {
            System.out.println("Meme");
        }
    }

    public void captureCam() {
        //Keep updating currentFrame by capturing webcam photos
        try {
            currentFrame = webcam.getImage();
        }
        catch(Exception e) {
            System.out.println("Meme");
        }
    }

    public void sendFrametoServer() {
        //keep sending updated frame to server
    }

    public void refreshMessages() {
        //update both sub only and all user chat window
    }

    public void run() {
        while(running) {
            if (mode == StreamMode.SCREENCAP) this.captureScreen();
            if (mode == StreamMode.WEBCAM) this.captureCam();
            this.sendFrametoServer();
            //At some interval, call this.refreshMessages()
        }
    }

}
