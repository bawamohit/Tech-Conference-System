import java.time.LocalDateTime;

public class Message {
    private User sender;
    private User receiver;
    private LocalDateTime time;
    private String[] content;

    public Message(User sender, User receiver, LocalDateTime time, String[] content){
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
        this.content = content;
    }

    public User getSender() { return sender; }
    public User getReceiver() { return receiver; }
    public LocalDateTime getTime() { return time; }
    public String[] getContent() { return content; }
}
