import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageManager {
    private HashMap<User, HashMap<User, ArrayList<Message>>> conversations;
    private List<User> userList; //if not needed, consider deleting
    private List<Event> eventList; //ArrayList probably?

    public MessageManager(List<User> userList) {
        this.conversations = new HashMap<>();
        this.userList = userList;
        for (User user : userList) {
            HashMap<User, ArrayList<Message>> receivers = new HashMap<>();
            conversations.put(user, receivers);
        }
    }

    //check if receiver valid in TechConferenceSystem
    protected void sendMessage(User sender, User receiver, LocalDateTime time, String content) {
        Message message = new Message(sender, receiver, time, content);
        addReceiverConversation(sender, receiver);
        conversations.get(sender).get(receiver).add(message);
    }

    /**
    * Precondition: Exists a message from sender to receiver on time
     */
    protected void deleteMessage(User sender, User receiver, LocalDateTime time){
        ArrayList conversation = conversations.get(sender).get(receiver);
        int messageIndex = binarySearchMessage(conversation, 0 , conversation.size(), time);
        conversation.remove(messageIndex); //store edit history?
    }

    protected ArrayList<Message> getConversation(User firstUser, User secondUser) {
        return conversations.get(firstUser).get(secondUser);
    }

    private void addReceiverConversation(User sender, User receiver){
        if (!conversations.get(sender).containsKey(receiver)) {
            ArrayList<Message> conversation = new ArrayList<>();
            conversations.get(sender).put(receiver, conversation);
        }
    }

    private int binarySearchMessage(ArrayList conversation, int startIndex, int endIndex, LocalDateTime time){
        int midIndex = (startIndex + endIndex) / 2;
        LocalDateTime midMessageTime = ((Message)conversation.get(midIndex)).getTime();
        if(midMessageTime.isEqual(time)){
            return midIndex;
        } else if(midMessageTime.isBefore(time)){
            return binarySearchMessage(conversation, startIndex, midIndex, time);
        } else{
            return binarySearchMessage(conversation, midIndex + 1, endIndex, time);
        }
    }
}
