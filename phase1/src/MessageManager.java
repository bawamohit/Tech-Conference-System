import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageManager {
    private HashMap<String, HashMap<String, List<Message>>> conversations;

    public MessageManager(List<User> userList) {
        this.conversations = new HashMap<>();
        for (User user : userList) {
            String username = user.getUsername();
            HashMap<String, List<Message>> receivers = new HashMap<>();
            conversations.put(username, receivers);
        }
    }

    // GROUP CHAT MOTHERCUCKERS ?????????????
    protected void sendMessage(String sender, String receiver, String content) {
        addMessage(sender, receiver, content);
        addMessage(receiver, sender, content);
    }

    // Precondition: message is in chat
    protected void deleteMessage(Message message){
        String sender = message.getSender();
        String receiver = message.getReceiver();
        List<Message> conversation = conversations.get(sender).get(receiver);
        int messageIndex = binarySearchMessage(conversation, message);
        conversation.remove(messageIndex); //store edit history?
    }

    protected List<Message> getConversation(String firstUser, String secondUser) {
        return conversations.get(firstUser).get(secondUser);
    }

    protected void messageEvent(String sender, List<User> userList, String content) {
        for (User user : userList) {
            String receiver = user.getUsername();
            sendMessage(sender, receiver, content);
        }
    }

    protected void addMessage(String firstUser, String secondUser, String content) {
        Message message = new Message(firstUser, secondUser, content);
        addSenderConversations(firstUser, secondUser);
        addReceiverConversation(firstUser, secondUser);
        conversations.get(firstUser).get(secondUser).add(message);
    }

    private void addSenderConversations(String sender, String receiver) {
        if (!conversations.containsKey(sender)) {
            HashMap<String, List<Message>> receivers = new HashMap<>();
            conversations.put(sender, receivers);
        }
    }

    private void addReceiverConversation(String sender, String receiver){
        if (!conversations.get(sender).containsKey(receiver)) {
            List<Message> conversation = new ArrayList<>();
            conversations.get(sender).put(receiver, conversation);
        }
    }

    private int binarySearchMessage(List<Message> conversation, Message message){
        LocalDateTime time = message.getTime();
        return binarySearchMessage(conversation, 0, conversation.size(), time);
    }

    private int binarySearchMessage(List<Message> conversation, int startIndex, int endIndex, LocalDateTime time){
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
