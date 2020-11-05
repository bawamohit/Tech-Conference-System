import java.time.LocalDateTime;

public class Message {
    private String sender;
    private String receiver;
    private LocalDateTime time;
    private String content;

    public Message(String sender, String receiver, LocalDateTime time, String content){
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
        this.content = content;
    }

    public String getSender() { return sender; }
    public String getReceiver() { return receiver; }
    public LocalDateTime getTime() { return time; }
    public String getContent() { return content; }
}
