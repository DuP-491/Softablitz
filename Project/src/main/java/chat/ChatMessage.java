package chat;

import stream.LiveStream;
import user.Viewer;

import java.io.Serializable;
import java.time.LocalDateTime;

enum Access {
    ALLUSERS,
    SUBONLY
}

public class ChatMessage implements Serializable {
    private Viewer sender;
    private LiveStream streamSentOn;
    private String content;
    private LocalDateTime sentAt;
    private Access access;

    public ChatMessage(Viewer sender, LiveStream streamSentOn, String content, LocalDateTime sentAt, int access) {
        this.sender = sender;
        this.streamSentOn = streamSentOn;
        this.content = content;
        this.sentAt = sentAt;
        if(access == 0) this.access = Access.ALLUSERS;
        else this.access = Access.SUBONLY;
    }

    @Override
    public String toString() {
        String mes = this.sender + ": " + this.content;
        return mes;
    }

    public int getAccess() {
        if(access == Access.ALLUSERS) return 0;
        else return 1;
    }
}
