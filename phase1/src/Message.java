import java.time.LocalDateTime;
import java.util.UUID;

public class Message {
    private String sender;
    private String receiver;
    private LocalDateTime time;
    private String content;
    private UUID id;

    public Message(String sender, String receiver, String content){
        this.sender = sender;
        this.receiver = receiver;
        this.time = LocalDateTime.now();
        this.content = content;
        this.id = UUID.randomUUID();
    }

    public String getSender() { return sender; }
    public String getReceiver() { return receiver; }
    public LocalDateTime getTime() { return time; }
    public String getContent() { return content; }
    public UUID getID() { return id; }
}
