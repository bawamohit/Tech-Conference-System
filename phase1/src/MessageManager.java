import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageManager {
    private HashMap<String, HashMap<String, ArrayList<Message>>> conversations;
    private ArrayList<String> usernameList; //if not needed, consider deleting

    // controller does everything ??????
    public MessageManager(List<String> userList) {
        this.conversations = new HashMap<>();
        usernameList = new ArrayList<>();
        for (String user : userList) {
            usernameList.add(user);
            HashMap<String, ArrayList<Message>> receivers = new HashMap<>();
            conversations.put(user, receivers);
        }
    }

    // GROUP CHAT MOTHERCUKERS ?????????????
    protected void sendMessage(String sender, String receiver, LocalDateTime time, String content) {
        addMessage(sender, receiver, time, content);
        addMessage(receiver, sender, time, content);
    }

    /**
    * Precondition: Exists a message from sender to receiver on time
     */
    protected void deleteMessage(String sender, String receiver, LocalDateTime time){
        ArrayList<Message> conversation = conversations.get(sender).get(receiver);
        int messageIndex = binarySearchMessage(conversation, 0 , conversation.size(), time);
        conversation.remove(messageIndex); //store edit history?
    }

    protected ArrayList<Message> getConversation(String firstUser, String secondUser) {
        return conversations.get(firstUser).get(secondUser);
    }


    protected void messageEvent(String sender, List<String> userList, LocalDateTime time, String content) {
        for (String receiver : userList) {
            sendMessage(sender, receiver, time, content);
        }
    }

    //check if receiver valid in TechConferenceSystem
    protected void addMessage(String firstUser, String secondUser, LocalDateTime time, String content) {
        Message message = new Message(firstUser, secondUser, time, content);
        addReceiverConversation(firstUser, secondUser);
        conversations.get(firstUser).get(secondUser).add(message);
    }

    private void addReceiverConversation(String sender, String receiver){
        if (!conversations.get(sender).containsKey(receiver)) {
            ArrayList<Message> conversation = new ArrayList<>();
            conversations.get(sender).put(receiver, conversation);
        }
    }

    private int binarySearchMessage(ArrayList<Message> conversation, int startIndex, int endIndex, LocalDateTime time){
        int midIndex = (startIndex + endIndex) / 2;
        LocalDateTime midMessageTime = (conversation.get(midIndex)).getTime();
        if(midMessageTime.isEqual(time)){
            return midIndex;
        } else if(midMessageTime.isBefore(time)){
            return binarySearchMessage(conversation, startIndex, midIndex, time);
        } else {
            return binarySearchMessage(conversation, midIndex + 1, endIndex, time);
        }
    }
}
