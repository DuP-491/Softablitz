package test;

import stream.Category;
import user.Status;
import user.Streamer;

public class StreamerDemo {
    public static void main(String args[]) {
        Streamer streamer = new Streamer("causality","diptarag","3325", Status.ONLINE);
        streamer.startStreaming("dicking", Category.DOTA2);

    }
}
