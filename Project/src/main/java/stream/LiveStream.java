package stream;

import chat.ChatMessage;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.LinkedList;

import chat.MessageReciever;
import chat.MessageSender;

import javax.swing.*;


public class LiveStream extends Canvas implements Runnable {
    protected int viewCount;
    protected LocalDateTime startedAtTime;
    protected Category category;
    protected String title;
    protected StreamMode mode;
    protected boolean running;
    protected LinkedList<ChatMessage> allUsersMessageQueue;
    protected LinkedList<ChatMessage> subOnlyMessageQueue;

    protected ImageReciever imageReciever;
    protected AudioReciever audioReciever;
    protected MessageReciever messageReciever;

    protected MessageSender messageSender;

    public static int MESSAGE_REFRESH_INTERVAL = 1000000;

    protected int chatCapacity = 200;

    protected JFrame j;


    public LiveStream(String title, Category cat, int id) {
        this.startedAtTime = LocalDateTime.now();
        this.category = cat;
        this.title = title;
        allUsersMessageQueue = new LinkedList<ChatMessage>();
        subOnlyMessageQueue = new LinkedList<ChatMessage>();

        String imageGroup = "225.4.6." + Integer.toString(id);
        String audioGroup = "225.4.6." + Integer.toString(id+1);
        String messageGroup = "225.4.6." + Integer.toString(id+2);

        imageReciever = new ImageReciever(imageGroup, this);
        audioReciever = new AudioReciever(audioGroup, this);
        messageReciever = new MessageReciever(messageGroup, this);

        messageSender = new MessageSender(messageGroup, this);

        Thread t = new Thread(imageReciever); t.start(); //start recieveing images on new thread
        t = new Thread(audioReciever); t.start(); //start recieving audio on new thread
        t = new Thread(messageReciever); t.start(); // start recieving messages on new thread
    }

    public void stopWatching() {
        running = false;

        imageReciever.stopThread();
        imageReciever = null;

        audioReciever.stopThread();
        audioReciever = null;

        messageReciever.stopThread();
        messageReciever = null;
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
    }

    public String getTitle() {
        return this.title;
    }

    public StreamMode getMode() { return this.mode; }

    public void run() {
        int count = 0;
        j = new JFrame();
        j.add(this);
        j.setSize(1000,800);
        j.setVisible(true);
        j.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        while(running) {
            count++;
            if(count == MESSAGE_REFRESH_INTERVAL) {
                refreshMessages();
            }
            update();
        }
    }

    public void update() {
        try {
            Graphics g = this.getGraphics();
            g.drawString(title + ": " + category,50,50);
            //System.out.println(b);
            ImageIcon im = new ImageIcon(imageReciever.currentFrame);
            im.paintIcon(this, g, 50, 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
