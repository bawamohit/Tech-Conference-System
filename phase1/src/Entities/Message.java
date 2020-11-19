package Entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Message implements Serializable {
    private String sender;
    private String receiver;
    private LocalDateTime time;
    private String content;

    public Message(String sender, String receiver, String content){
        this.sender = sender;
        this.receiver = receiver;
        this.time = LocalDateTime.now();
        this.content = content;
    }

    public String getSender() { return sender; }
    public String getReceiver() { return receiver; }
    public LocalDateTime getTime() { return time; }
    public String getContent() { return content; }
}
