package stream;

import chat.ChatMessage;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

import chat.MessageReciever;
import chat.MessageSender;
import test.connections.Message;
import user.Streamer;
import user.Viewer;

import javax.imageio.ImageIO;
import javax.swing.*;


public class LiveStream implements Runnable, Serializable {
    static {
        ImageIO.setUseCache(false);
    }
    protected int viewCount;
    protected String streamerUsername;
    protected LocalDateTime startedAtTime;
    protected Category category;
    protected String title;
    protected StreamMode mode;
    protected boolean running;
    protected LinkedList<ChatMessage> allUsersMessageQueue;
    protected LinkedList<ChatMessage> subOnlyMessageQueue;

    protected int ID;

    protected ImageReciever imageReciever;
    protected AudioReciever audioReciever;
    protected MessageReciever messageReciever;

    protected MessageSender messageSender;

    public static int MESSAGE_REFRESH_INTERVAL = 100000;

    protected int chatCapacity = 200;

    protected StreamWindow j;

    private String messageBlock;


    public LiveStream(String title, Category cat, int id, String streamerUsername) {

        this.ID = id;
        this.streamerUsername = streamerUsername;
        this.category = cat;
        this.title = title;
        allUsersMessageQueue = new LinkedList<ChatMessage>();
        subOnlyMessageQueue = new LinkedList<ChatMessage>();

    }

    public void setStartedAtTime(LocalDateTime s) {
        startedAtTime = s;
    }

    //This will be called on the machine where we want to view
    public void startWatching() {
        String imageGroup = "225.4.6." + Integer.toString(ID);
        String audioGroup = "225.4.6." + Integer.toString(ID+1);
        String messageGroup = "225.4.6." + Integer.toString(ID+2);

        imageReciever = new ImageReciever(imageGroup, this);
        audioReciever = new AudioReciever(audioGroup, this);
        messageReciever = new MessageReciever(messageGroup, this);

        messageSender = new MessageSender(messageGroup, this);

        Thread t = new Thread(imageReciever); t.start(); //start recieveing images on new thread
        t = new Thread(audioReciever); t.start(); //start recieving audio on new thread
        t = new Thread(messageReciever); t.start(); // start recieving messages on new thread

        j = new StreamWindow();
        j.setSize(1000,800);
        j.setVisible(true);
        j.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        j.setTitle(this.toString());

        running = true;
    }

    public void stopWatching() {
        running = false;

        imageReciever.stopThread();
        imageReciever = null;

        audioReciever.stopThread();
        audioReciever = null;

        messageReciever.stopThread();
        messageReciever = null;

        System.out.println("Stopped watching");
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

    public void refreshMessages() {
        //update both sub only and all user chat window
        messageBlock = "";
        for(int i = 0; i < allUsersMessageQueue.size(); i++) {
            messageBlock += allUsersMessageQueue.get(i).toString();
            messageBlock += "\n";
        }
        j.setMessageBlockText(messageBlock);
        //System.out.println("refreshed");
    }

    public String getTitle() {
        return this.title;
    }

    public StreamMode getMode() { return this.mode; }

    @Override
    public void run() {
        int count = 0;
        System.out.println(running);
        while(running) {
            count++;
            if(count == MESSAGE_REFRESH_INTERVAL) {
                refreshMessages();
                count = 0;
            }
            update();
        }
    }

    public void update() {
        try {
            long a=audioReciever.getCurrentTimestamp();
            long b=imageReciever.WhatsTheLatestTimeStamp();
            if(a>=b){
                j.setIcon(imageReciever.getLatestImage());
            }

        } catch (Exception e) {
            //System.out.println("Cant get currentframe");
        }
    }

    @Override
    public String toString() {
        String ans = streamerUsername + ": " + title + " in " + category;
        return ans;
    }

    public Category getCategory() {
        return this.category;
    }

    public int getID() {
        return this.ID;
    }

    public void markAsViewer(Viewer self) {
        j.markAsViewer(self);
    }

    public void sendMessage(ChatMessage message) {
        System.out.println(message);
        messageSender.sendMessage(message);
    }
}
