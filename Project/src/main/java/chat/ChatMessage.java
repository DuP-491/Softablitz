package chat;

import stream.LiveStream;
import user.Viewer;

import java.io.Serializable;
import java.time.LocalDateTime;

enum Access implements Serializable{
    ALLUSERS,
    SUBONLY
}

public class ChatMessage implements Serializable {
    private String senderUsername;
    private String content;
    private Access access;

    public ChatMessage(String sender, String content, int access) {
        this.senderUsername = sender;
        this.content = content;
        if(access == 0) this.access = Access.ALLUSERS;
        else this.access = Access.SUBONLY;
    }

    @Override
    public String toString() {
        String mes = this.senderUsername + ": " + this.content;
        return mes;
    }

    public int getAccess() {
        if(access == Access.ALLUSERS) return 0;
        else return 1;
    }
}
