import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public abstract class UserSystem {
    protected Presenter presenter;
    protected UserManager userM;
    protected EventManager eventM;
    protected MessageManager messageM;
    File eventManagerInfo = new File("./phase1/src/eventManager.ser");
    File messageManagerInfo = new File("./phase1/src/messageManager.ser");
    File userManagerInfo = new File("./phase1/src/userManager.ser");

    public UserSystem (Presenter p, UserManager uMan, EventManager eMan, MessageManager mMan) {
        presenter = p;
        userM = uMan;
        eventM = eMan;
        messageM = mMan;
    }

    abstract void run(String username);

    protected void helperMessageSystem(String username, String choice, Scanner scanner){
        switch (choice) {
            case "0":
                sendMessage(username, scanner);
                break;
            case "1":
                //edit Message
                presenter.printUnderConstruction();
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
        presenter.printAskMsgReceiver();
        String receiver = scanner.nextLine();
        presenter.printAsk("message");
        String message = scanner.nextLine();
        messageM.sendMessage(username, receiver, message);
        presenter.printMessageSent();
    }

    // TODO: Use this method or delete it
    private void editMessage(String username, Scanner scanner){
        presenter.printUnderConstruction();
    }

    private void deleteMessage(String username, Scanner scanner){
        presenter.printAskWhichInbox();
        presenter.printUCReturns(messageM.getChats(username));
        String inboxChoice = scanner.nextLine();
        List<String> inbox = messageM.getInbox(username, inboxChoice);
        int n = 0;
        for (String message : inbox) {
            System.out.println(n + ": " + message);
            n += 1;
        }
        presenter.printAskWhichMessage();
        int content = scanner.nextInt();
        messageM.deleteMessage(messageM.getChat(username, inboxChoice).get(content));
    }

    private void viewInbox(String username, Scanner scanner){
        presenter.printAskWhichInbox();
        presenter.printUCReturns(messageM.getChats(username));
        String contact = scanner.nextLine();
        for (String message : messageM.getInbox(username, contact)){
            System.out.println(message);
        }
    }

    protected void save(){
        try {
            UserGateway userGateway = new UserGateway();
            userGateway.saveToFile(userManagerInfo.getPath(), userM);
            EventGateway eventGateway = new EventGateway();
            eventGateway.saveToFile(eventManagerInfo.getPath(), eventM);
            MessageGateway messageGateway = new MessageGateway();
            messageGateway.saveToFile(messageManagerInfo.getPath(), messageM);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

