import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Chat {
    private UUID chatID;
    private List<String> memberList;
    private List<UUID> messageList;

    public Chat(List<String> memberList){
        this.memberList = memberList;
        chatID = UUID.randomUUID();
        messageList = new ArrayList<>();
    }

    public void addMessage(Message message){
        messageList.add(message.getID());
    }

    public UUID getChatID() {
        return chatID;
    }

    public List<String> getMemberList() {
        return memberList;
    }

    public void addMember(String member){
        memberList.add(member);
    }

    public void removeMember(String member){ memberList.remove(member); }
}
