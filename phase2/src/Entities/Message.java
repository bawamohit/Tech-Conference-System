package Entities;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This class is one of the entity classes for this program, specifically for message.
 *
 */
public class Message implements Serializable {
    private String sender;
    private String receiver;
    private LocalDateTime time;
    private String content;

    /** Creates an instance of Message
     *
     * @param sender Sender of Message
     * @param receiver Receiver of Message
     * @param content Content of Message
     */
    public Message(String sender, String receiver, String content){
        this.sender = sender;
        this.receiver = receiver;
        this.time = LocalDateTime.now();
        this.content = content;
    }

    /** Returns the username of the Sender
     *
     * @return Username of Sender
     */
    public String getSender() { return sender; }

    /** Returns the username of the Receiver
     *
     * @return Username of Receiver
     */
    public String getReceiver() { return receiver; }

    /** Returns the time of the Message
     *
     * @return Time of Message
     */
    public LocalDateTime getTime() { return time; }

    /** Returns the content of the Message
     *
     * @return Content of Message
     */
    public String getContent() { return content; }
}
