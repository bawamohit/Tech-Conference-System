import java.util.List;
import java.util.Scanner;

public abstract class UserSystem {
    protected Presenter presenter;
    protected UserManager um;
    protected EventManager em;
    protected MessageManager mm;
    protected RoomManager rm;
    //TODO If these variables were set to protected, then don't need to initiate new variables in subclasses, or getters in this class
    // and can we please name the variables to more readable stuff this takes me two minutes to translate every time I see it out of context
    public UserSystem (Presenter p, UserManager uMan, EventManager eMan, MessageManager mMan, RoomManager rMan) {
        presenter = p;
        um = uMan;
        em = eMan;
        mm = mMan;
        rm = rMan;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public UserManager getUserManager() {
        return um;
    }

    public EventManager getEventManager() {
        return em;
    }

    public MessageManager getMessageManager() {
        return mm;
    }

    public RoomManager getRoomManager() {
        return rm;
    }

    abstract void run(String username);

    protected void helperMessageSystem(String username, String choice, Scanner scanner){
        switch (choice) {
            case "0":
                sendMessage(username, scanner);
                break;
            case "1":
                //edit Message
                getPresenter().printUnderConstruction();
                break;
            case "2":
                // delete Message
                deleteMessage(username, scanner);
                break;
            case "3":
                // view inbox
                viewInbox(username, scanner);
                break;
            default:
                break;
        }
    }

    private void sendMessage(String username, Scanner scanner){
        getPresenter().printAskMsgReceiver();
        String receiver = scanner.nextLine();
        getPresenter().printAsk("message");
        String message = scanner.nextLine();
        getMessageManager().sendMessage(username, receiver, message);
        getPresenter().printMessageSent();
    }

    private void editMessage(String username, Scanner scanner){
        getPresenter().printUnderConstruction();
    }

    private void deleteMessage(String username, Scanner scanner){
        getPresenter().printAskWhichInbox();
        getPresenter().printUCReturns(getMessageManager().getChats(username));
        String inboxChoice = scanner.nextLine();
        List<String> inbox = getMessageManager().getInbox(username, inboxChoice);
        int n = 0;
        for (String message : inbox) {
            System.out.println(n + ": " + message);
            n += 1;
        }
        getPresenter().printAskWhichMessage();
        int content = scanner.nextInt();
        getMessageManager().deleteMessage(getMessageManager().getChat(username, inboxChoice).get(content));
    }

    private void viewInbox(String username, Scanner scanner){
        getPresenter().printAskWhichInbox();
        getPresenter().printUCReturns(getMessageManager().getChats(username));
        String contact = scanner.nextLine();
        for (String message : getMessageManager().getInbox(username, contact)){
            System.out.println(message);
        }
    }
}

