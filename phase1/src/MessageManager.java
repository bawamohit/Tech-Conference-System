import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageManager {
    private HashMap<User, HashMap<User, List<Message>>> messageList;
    private List<User> userList; //if not needed, consider deleting
    private List<Event> eventList;

    public MessageManager(List<User> userList) {
        this.messageList = new HashMap<>();
        this.userList = userList;
        for (User user : userList) {
            messageList.put(user, null);
        }
    }

    //check if receiver valid in TechConferenceSystem
    protected void sendMessage(User sender, User receiver, LocalDateTime time, String content) {
        Message message = new Message(sender, receiver, time, content);
        List<Message> messageValue = new ArrayList<>();
        messageValue.add(message);
        messageList.putIfAbsent(sender, HashMap<>());
    }

    protected List<Message> getMessageHistory(User firstUser, User secondUser) {
        List<Message> messageHistory = new ArrayList<>();

    }
}
