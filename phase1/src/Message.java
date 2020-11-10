import java.time.LocalDateTime;
import java.util.UUID;

public class Message {
    private String sender;
    private LocalDateTime time;
    private String content;
    private UUID id;

    public Message(String sender, String content){
        this.sender = sender;
        this.time = LocalDateTime.now();
        this.content = content;
        this.id = UUID.randomUUID();
    }

    public String getSender() { return sender; }

    public LocalDateTime getTime() { return time; }

    public String getContent() { return content; }

    public UUID getID() { return id; }
}
