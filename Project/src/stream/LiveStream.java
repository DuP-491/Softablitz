package stream;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

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

    public LiveStream(String title, Category cat) {
        this.startedAtTime = LocalDateTime.now();
        this.category = cat;
        this.title = title;
    }

    public void setMode(int mode) {
        if(mode == 1) this.mode = StreamMode.WEBCAM;
        else this.mode = StreamMode.SCREENCAP;
    }

    public void captureScreen() {
        //Keep updating currentFrame by capturing screenshots
    }

    public void captureCam() {
        //Keep updating currentFrame by capturing webcam photos
    }

    public void run() {
        while(true) {
            if (mode == StreamMode.SCREENCAP) captureScreen();
            if (mode == StreamMode.WEBCAM) captureCam();
        }
    }

}
