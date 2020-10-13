package stream;

import user.Streamer;

public class LiveStreamSource extends LiveStream {
    private ImageSender imageSender;
    private AudioSender audioSender;

    public LiveStreamSource(String title, Category cat, int id, Streamer streamer) {
        super(title, cat, id, streamer);
    }

    public void startStreaming() {
        String imageGroup = "225.4.6." + Integer.toString(ID);
        String audioGroup = "225.4.6." + Integer.toString(ID+1);

        imageSender = new ImageSender(imageGroup, this);
        audioSender = new AudioSender(audioGroup);

        Thread t = new Thread(imageSender); t.start();
        t = new Thread(audioSender); t.start();
    }

    public void setMode(int mode) {
        if(mode == 1) { this.mode = StreamMode.WEBCAM; }
        else this.mode = StreamMode.SCREENCAP;
    }

    public void stopStreaming() {
        imageSender.stopThread();
        imageSender = null;

        audioSender.stopThread();
        audioSender = null;

        stopWatching();
    }
}
