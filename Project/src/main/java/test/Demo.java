package test;

import stream.*;
import user.Status;
import user.Streamer;

public class Demo {

    public static void main(String[] args) {
        Streamer me = new Streamer("causality", "Diptarag", "WRGWG", null, Status.ONLINE);
        LiveStreamSource l = new LiveStreamSource("Memeing",Category.GTAV, 0, me);
        l.startStreaming();
        l.setMode(1);
        Thread t = new Thread(l);
        t.start();
    }
}
