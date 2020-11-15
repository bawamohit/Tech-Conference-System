import java.util.List;
import java.util.Scanner;

public abstract class UserSystem {
    private Presenter presenter;
    private UserManager um;
    private EventManager em;
    private MessageManager mm;

    public UserSystem (Presenter p, UserManager uMan, EventManager eMan, MessageManager mMan) {
        presenter = p;
        um = uMan;
        em = eMan;
        mm = mMan;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public UserManager getUm() {
        return um;
    }

    public EventManager getEm() {
        return em;
    }

    public MessageManager getMm() {
        return mm;
    }

    abstract void run(String username);

    protected void helperMessageSystem(String username, String choice, Scanner scanner){
        if (choice.equals("0")){
            getPresenter().printAskMsgReceiver();
            String receiver = scanner.nextLine();
            getPresenter().printAsk("message");
            String message = scanner.nextLine();
            getMm().sendMessage(username, receiver, message);
            getPresenter().printMessageSent();
        } else if (choice.equals("1")){
            //edit Message
            getPresenter().printUnderConstruction();
        } else if (choice.equals("2")){
            // delete Message
            getPresenter().printAskWhichInbox();
            getPresenter().printUCReturns(getMm().getChats(username));
            String inboxChoice = scanner.nextLine();
            List<String> inbox = getMm().getInbox(username, inboxChoice);
            int n = 0;
            for (String message : inbox) {
                System.out.println(n + ": " + message);
                n += 1;
            }
            getPresenter().printAskWhichMessage();
            int content = scanner.nextInt();
            getMm().deleteMessage(getMm().getChat(username, inboxChoice).get(content));
        } else if (choice.equals("3")){
            // view inbox
            getPresenter().printAskWhichInbox();
            getPresenter().printUCReturns(getMm().getChats(username));
            String contact = scanner.nextLine();
            for (String message :getMm().getInbox(username, contact)){
                System.out.println(message);
            }
        } else if (choice.equals("b")){
            run(username);
        } else {
            getPresenter().printInvalidInput();
            getPresenter().printAttendeeMessageMenu();
        }
    }
}
