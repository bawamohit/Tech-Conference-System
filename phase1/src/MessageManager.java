import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageManager {
    private HashMap<String, HashMap<String, List<Message>>> chats;

    // controller does everything ??????
    public MessageManager(List<User> userList) {
        this.chats = new HashMap<>();
        for (User user : userList) {
            String username = user.getUsername();
            HashMap<String, List<Message>> receivers = new HashMap<>();
            chats.put(username, receivers);
        }
    }

    protected void sendMessage(User sender, User receiver, String content) {
        addMessage(sender, receiver, content);
        addMessage(receiver, sender, content);
    }

    protected void deleteMessage(Message message) {
        String sender = message.getSender();
        String receiver = message.getReceiver();
        List<Message> chat = chats.get(sender).get(receiver);
        int messageIndex = binarySearchMessage(chat, message);
        chat.remove(messageIndex); //store edit history?
    }

    protected List<Message> getConversation(User firstUser, User secondUser) {
        String firstUsername = firstUser.getUsername();
        String secondUsername = secondUser.getUsername();
        return chats.get(firstUsername).get(secondUsername);
    }

    protected void messageEvent(User sender, List<User> userList, String content) {
        for (User user : userList) {
            sendMessage(sender, user, content);
        }
    }

    protected void addMessage(User firstUser, User secondUser, String content) {
        String firstUsername = firstUser.getUsername();
        String secondUsername = secondUser.getUsername();
        Message message = new Message(firstUsername, secondUsername, content);
        addSenderChat(firstUser, secondUser);
        addReceiverChat(firstUser, secondUser);
        chats.get(firstUsername).get(secondUsername).add(message);
    }

    private void addSenderChat(User sender, User receiver) {
        String senderUsername = sender.getUsername();
        if (!chats.containsKey(senderUsername)) {
            HashMap<String, List<Message>> receivers = new HashMap<>();
            chats.put(senderUsername, receivers);
        }
    }

    private void addReceiverChat(User sender, User receiver){
        String senderUsername = sender.getUsername();
        String receiverUsername = receiver.getUsername();
        if (!chats.get(senderUsername).containsKey(receiverUsername)) {
            ArrayList<Message> chat = new ArrayList<>();
            chats.get(senderUsername).put(receiverUsername, chat);
        }
    }

    private int binarySearchMessage(List<Message> chat, Message message) {
        LocalDateTime time = message.getTime();
        return binarySearchMessage(chat, 0, chat.size(), time);
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
