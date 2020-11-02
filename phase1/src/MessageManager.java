import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageManager {
    private HashMap<User, HashMap<User, ArrayList<Message>>> messageList;
    private List<User> userList; //if not needed, consider deleting
    private List<Event> eventList; //ArrayList maybe?

    public MessageManager(List<User> userList) {
        this.messageList = new HashMap<>();
        this.userList = userList;
        for (User user : userList) {
            HashMap<User, ArrayList<Message>> receivers = new HashMap<>();
            messageList.put(user, receivers);
        }
    }

    //check if receiver valid in TechConferenceSystem
    protected void sendMessage(User sender, User receiver, LocalDateTime time, String content) {
        Message message = new Message(sender, receiver, time, content);
        if (!messageList.get(sender).containsKey(receiver)) {
            ArrayList<Message> conversation = new ArrayList<>();
            messageList.get(sender).put(receiver, conversation);
        }

        messageList.get(sender).get(receiver).add(message);
    }

    protected List<Message> getMessageHistory(User firstUser, User secondUser) {
        List<Message> messageHistory = new ArrayList<>();

    }
}
