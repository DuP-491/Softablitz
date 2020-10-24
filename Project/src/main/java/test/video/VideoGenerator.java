package test.video;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.Timestamp;
import java.sql.Time;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import stream.AudioSender;

public class VideoGenerator {
    private final static int  VIDEO_STREAM_INDEX=0;
    private final static int AUDIO_STREAM_INDEX=1;

    private final static ICodec.ID VIDEO_OUTPUT_FILE_FORMAT=ICodec.ID.CODEC_ID_MPEG4;
    private final static ICodec.ID AUDIO_OUTPUT_FILE_FORMAT=ICodec.ID.CODEC_ID_MP3;

    private static int AUDIO_SAMPLE_RATE = 44100;
    private static int AUDIO_CHANNEL_CONFIG = 1;

    private int videoStreamIndex;
    private int audioStreamIndex;
    private IMediaWriter writer;
    private int videoHeight=0;
    private int videoWidth=0;

    VideoGenerator(int videoWidth ,int videoHeight,String outputFilePath){
        writer = ToolFactory.makeWriter(outputFilePath);
        videoStreamIndex=writer.addVideoStream(VIDEO_STREAM_INDEX, 0, VIDEO_OUTPUT_FILE_FORMAT,videoWidth, videoHeight);
        audioStreamIndex=writer.addAudioStream(AUDIO_STREAM_INDEX,1,AUDIO_OUTPUT_FILE_FORMAT,AUDIO_CHANNEL_CONFIG,AUDIO_SAMPLE_RATE);
//        System.out.println("videoStreamIndex "+videoStreamIndex);
//        System.out.println("audioStreamIndex "+audioStreamIndex);
    }
    public static void main(String[] args) throws AWTException, LineUnavailableException {
        AudioFormat format = new AudioFormat(44100.0f, 16, 1, true, true);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);
        microphone.open(format);
        microphone.start();
        VideoGenerator videoGenerator=new VideoGenerator(400,400,"C:/Users/siddh/Desktop/myVideo.mp4");
        long base=System.currentTimeMillis();
        for (long index = 0; index <200; index++) {
            BufferedImage screen = getImage(0);
            long timeStamp=System.currentTimeMillis();
            System.out.println(timeStamp-base);
            videoGenerator.encodeImage(screen,timeStamp);
            int ready=microphone.available();
            byte [] buffer=new byte[ready];
            microphone.read(buffer,0,ready);
            videoGenerator.encodeAudio(buffer);
        }
        videoGenerator.saveVideo();

    }

    /**
     *  To convert image from normal RGB t BGR Format for xuggler to encode video from Image
     * @param sourceImage
     * @param targetType
     * @return
     */
    public static BufferedImage convertToType(BufferedImage sourceImage, int targetType) {
        BufferedImage image;
        if (sourceImage.getType() == targetType) {
            image = sourceImage;
        }
        else {
            image = new BufferedImage(sourceImage.getWidth(),
            sourceImage.getHeight(), targetType);
            image.getGraphics().drawImage(sourceImage, 0, 0, null);
        }
        return image;
    }

    /**
     * temporary function to get screen captured image
     * @param offset
     * @return
     * @throws AWTException
     */
    private static BufferedImage getImage(int offset) throws AWTException {
        BufferedImage image=new Robot().createScreenCapture(new Rectangle(400+offset,400)).getSubimage(offset,0,400,400);
        return image;
    }

    /**
     * To encode image to Video
     * Provide an image with its time stamp;
     * It is important to provide a time stamp as zero while encoding for the first time;
     *
     * @param image
     * @param timeStamp
     */
    public void encodeImage(BufferedImage image,long timeStamp) {
        BufferedImage imageConvertedToCorrectFormat = convertToType(image, BufferedImage.TYPE_3BYTE_BGR);
        writer.encodeVideo(videoStreamIndex, imageConvertedToCorrectFormat, timeStamp, TimeUnit.MILLISECONDS);
    }
    public void encodeAudio(byte[] bytes){
        if(bytes.length>1) {
            short[] shorts = new short[bytes.length / 2];
            ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).asShortBuffer().get(shorts);
            writer.encodeAudio(audioStreamIndex, shorts);
        }
    }
    public void saveVideo(){
        writer.close();
        System.out.println("Video saved");

    }


}