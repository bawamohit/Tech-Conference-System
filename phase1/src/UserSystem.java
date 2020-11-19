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

    /**
     * Implements a method used to format a list of strings to an numbered string.
     * This is used to format a list of information to a string, that shows each element inside the list as a numbered
     * string format.
     */
    protected String formatInfo(List<String> eventStrings){
        StringBuilder info = new StringBuilder();
        int i = 0;
        for (String event: eventStrings){
            info.append("\n").append(i).append(": ").append(event);
            i += 1;
        }
        if (info.toString().equals("")) {
            presenter.printNoEventsAvailable();
            return "";
        }
        return info.toString();
    }

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
        String inboxChoice = "";
        int content = 0;
        while (true) {
            while (true) {
                presenter.printAskWhichInbox();
                presenter.printUCReturns(messageM.getChats(username));
                inboxChoice = scanner.nextLine();
                if (messageM.getChats(username).contains(inboxChoice)) {
                    break;
                } else {
                    presenter.printInvalidInput();
                }
            }
            List<String> inbox = messageM.getInbox(username, inboxChoice);
            int n = 0;
            for (String message : inbox) {
                presenter.printUCReturns(n + ": " + message);
                n += 1;
            }
            presenter.printAskWhichMessage();
            content = scanner.nextInt();
            if (0 <= content && content < n) {
                break;
            } else {
                presenter.printInvalidInput();
            }
        }
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

    /**
     * Implements a method used to save each UserManager, EventManager and MessageManager objects
     * to its designated .ser file.
     *
     */
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

