import java.time.LocalDateTime;
import java.util.List;

public class Message {

    private String sender;  //placeholder. replace with User class
    private String recipient; //placeholder. replace with User class
    private String content;
    private LocalDateTime timeOfMessage;

    //to avoid long parameter lists, used List (containing sender, recipient, and content)
    // as parameter for this constructor. will need to create this list in the UI class.

    /**
     * The constructor takes in a List of Objects containing the information regarding this Message. It assigns
     * variable sender, recipient, content, and sets timeOfMessage with the current date and time of when this Message
     * was created/sent.
     *
     * @param messageInfo list of Objects containing the information regarding this Message
     */
    public Message(List<Object> messageInfo){
        sender = (String) messageInfo.get(0); //replace String with User class
        recipient = (String) messageInfo.get(1); //replace String with User class
        content = (String) messageInfo.get(2);
        timeOfMessage = LocalDateTime.now();
    }

    /**
     * Implements Getter, getSender, for sender.
     *
     * @return sender of this message
     */
    public String getSender(){ //replace String with User class
        return sender;
    }

    /**
     * Implements Getter, getRecipient, for recipient.
     *
     * @return recipient of this message
     */
    public String getRecipient(){ //replace String with User class
        return recipient;
    }

    /**
     * Implements Getter, getContent, for content.
     *
     * @return content of this message
     */
    public String getContent(){
        return content;
    }

    /**
     * Implements Getter, getTimeofMessage, for timeOfMessage.
     *
     * @return date and time of when this message was created/sent.
     */
    public LocalDateTime getTimeOfMessage(){
        return timeOfMessage;
    }

}