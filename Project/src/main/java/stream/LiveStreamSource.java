package stream;

import user.Streamer;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LiveStreamSource extends LiveStream {
    private Streamer streamer;
    private ImageSender imageSender;
    private AudioSender audioSender;

    public LiveStreamSource(String title, Category cat, int id, Streamer streamer) {
        super(title, cat, id, streamer.getUsername());
        this.streamer = streamer;
        mode = StreamMode.WEBCAM;

    }

    public void startStreaming() {
        startWatching();

        String imageGroup = "225.4.6." + Integer.toString(ID);
        String audioGroup = "225.4.6." + Integer.toString(ID+1);

        imageSender = new ImageSender(imageGroup, this);
        audioSender = new AudioSender(audioGroup);

        Thread t = new Thread(imageSender); t.start();
        t = new Thread(audioSender); t.start();

        j.markAsStreamer(this.streamer);

    }

    public void setMode(int mode) {
        if(mode == 1) { this.mode = StreamMode.WEBCAM; }
        else this.mode = StreamMode.SCREENCAP;
    }

    public void changeMode() {
        if(mode == StreamMode.SCREENCAP) mode = StreamMode.WEBCAM;
        else mode = StreamMode.SCREENCAP;
    }

    public void stopStreaming() {
        imageSender.stopThread();
        imageSender = null;

        audioSender.stopThread();
        audioSender = null;

        //stopWatching();
        System.out.println("Stopped streaming");
    }

    public void mute() {
        audioSender.mute();
        super.mute();
    }

    public void unmute() {
        audioSender.unmute();
        super.unmute();
    }
}
