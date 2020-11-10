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
        if(chat.get(messageIndex).getSender().equals(message.getSender())){
            chat.remove(messageIndex);
        }else{
            chat.remove(message);//store edit history?
        }
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

    protected void addMessage(User sender, User receiver, String content) {
        String firstUsername = sender.getUsername();
        String secondUsername = receiver.getUsername();
        Message message = new Message(firstUsername, secondUsername, content);
        addSenderChat(sender);
        addReceiverChat(sender, receiver);
        chats.get(firstUsername).get(secondUsername).add(message);
    }

    private void addSenderChat(User sender) {
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

    private int binarySearchMessage(List<Message> chat, int startIndex, int endIndex, LocalDateTime time){
        int midIndex = (startIndex + endIndex) / 2;
        LocalDateTime midMessageTime = (chat.get(midIndex)).getTime();
        if(midMessageTime.isEqual(time)){
            return midIndex;
        } else if(midMessageTime.isBefore(time)){
            return binarySearchMessage(chat, startIndex, midIndex, time);
        } else {
            return binarySearchMessage(chat, midIndex + 1, endIndex, time);
        }
    }
}
