import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MessageManager {
    private HashMap<String, HashMap<UUID, Chat>> chats;

    public MessageManager(List<User> userList) {
        this.chats = new HashMap<>();
        for (User user : userList) {
            String username = user.getUsername();
            HashMap<UUID, Chat> chatIDs = new HashMap<>();
            chats.put(username, chatIDs);
        }
    }

    // GROUP CHAT MOTHERCUCKERS ?????????????
    protected void sendMessage(String sender, String receiver, String content) {
        addMessage(sender, receiver, content);
        addMessage(receiver, sender, content);
    }
    //groupchat
    protected void sendMessage(String sender, UUID groupChat, String content) {
        for(String member: members){
            addMessage(member, groupChat, content);
        }
    }
    //HashMap<String, object<conversation>>;

    // Precondition: message is in chat
    protected void deleteMessage(Message message){
        String sender = message.getSender();
        String receiver = message.getReceiver();
        List<Message> conversation = conversations.get(sender).get(receiver);
        int messageIndex = binarySearchMessage(conversation, message);
        conversation.remove(messageIndex); //store edit history?
    }

    protected List<Message> getConversation(User firstUser, User secondUser) {
        String firstUsername = firstUser.getUsername();
        String secondUsername = secondUser.getUsername();
        return chats.get(firstUsername).get(secondUsername);
    }

    protected void messageEvent(String sender, List<User> userList, String content) {
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

    private void checkUserExists(User sender) {
        String senderName = sender.getUsername();
        if (!chats.containsKey(senderName)) {
            HashMap<UUID, Chat> chatIDs = new HashMap<>();
            chats.put(senderName, chatIDs);
        }
    }

    private void checkChatExists(List<User> members){
        if (!conversations.get(sender).containsKey(chatID)) {
            List<Message> conversation = new ArrayList<>();
            conversations.get(sender).put(receiver, conversation);
        }

        for (member: members) {
            if()
        }
    }

    private int binarySearchMessage(List<Message> conversation, Message message){
        LocalDateTime time = message.getTime();
        return binarySearchMessage(conversation, 0, conversation.size(), time);
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
