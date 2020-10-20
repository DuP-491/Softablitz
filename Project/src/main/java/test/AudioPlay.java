package test;

import javax.sound.sampled.*;

public class AudioPlay {
    TargetDataLine microphone;
    SourceDataLine speakers;

    public static int CHUNK_SIZE = 1024;
    private byte[] data;

    public void recordandPlay() {
        int numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
        speakers.write(data,0,numBytesRead);
    }


    public static void main(String args[]) {
        //Play using headphones, else audio version of infinite zoom meme will occur

        AudioPlay a = new AudioPlay();
        try {
            AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            a.microphone = (TargetDataLine) AudioSystem.getLine(info);
            a.microphone.open(format);
            a.microphone.start();

            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            a.speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            a.speakers.open(format);
            a.speakers.start();

            a.data = new byte[a.microphone.getBufferSize() / 5];
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        while(true) {
            a.recordandPlay();
        }
    }
}
