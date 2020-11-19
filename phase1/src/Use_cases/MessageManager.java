package Use_cases;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Entities.Message;

public class MessageManager implements Serializable {
    private HashMap<String, HashMap<String, List<Message>>> chats;

    public MessageManager() {
        this.chats = new HashMap<>();
    }

    public void sendMessage(String sender, String receiver, String content) {
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

    protected List<String> getChats(String user) {
        addSenderChat(user);
        return new ArrayList<>(chats.get(user).keySet());
    }

    protected List<String> getInbox(String firstUser, String secondUser){
        List<Message> messages = chats.get(firstUser).get(secondUser);
        List<String> inbox = new ArrayList<>();
        for(Message message : messages){
            inbox.add(message.getContent());
        }

        return inbox;
    }

    //might not need
    protected List<Message> getChat(String firstUser, String secondUser) {
        return chats.get(firstUser).get(secondUser);
    }

    protected void messageEvent(String sender, List<String> userList, String content) {
        for (String user : userList) {
            sendMessage(sender, user, content);
        }
    }

    private void addMessage(String sender, String receiver, String content) {
        Message message = new Message(sender, receiver, content);
        addSenderChat(sender);
        addReceiverChat(sender, receiver);
        chats.get(sender).get(receiver).add(message);
    }

    private void addSenderChat(String sender) {
        if (!chats.containsKey(sender)) {
            HashMap<String, List<Message>> receivers = new HashMap<>();
            chats.put(sender, receivers);
        }
    }

    private void addReceiverChat(String sender, String receiver){
        if (!chats.get(sender).containsKey(receiver)) {
            ArrayList<Message> chat = new ArrayList<>();
            chats.get(sender).put(receiver, chat);
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
