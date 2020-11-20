package Controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import Use_cases.*;
import UI.Presenter;
import Gateways.*;

public abstract class UserSystem {
    File eventManagerInfo = new File("./phase1/src/eventManager.ser");
    File messageManagerInfo = new File("./phase1/src/messageManager.ser");
    File userManagerInfo = new File("./phase1/src/userManager.ser");

    abstract void run(String username, TechConferenceSystem tcs);

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

        return info.toString();
    }


    protected void helperMessageSystem(String username, String choice, Scanner scanner, TechConferenceSystem tcs){
        switch (choice) {
            case "0":
                sendMessage(username, scanner, tcs);
                break;
            case "1":
                //edit Message
                tcs.getPresenter().printUnderConstruction();
                break;
            case "2":
                // delete Message
                deleteMessage(username, scanner, tcs);
                break;
            case "3":
                // view inbox
                viewInbox(username, scanner, tcs);
                break;
            default:
                break;
        }
    }

    private void sendMessage(String username, Scanner scanner, TechConferenceSystem tcs){//TODO user doesn't exist
        tcs.getPresenter().printAskMsgReceiver();
        String receiver = scanner.nextLine();
        tcs.getPresenter().printAsk("message");
        String message = scanner.nextLine();
        tcs.getMM().sendMessage(username, receiver, message);
        tcs.getPresenter().printMessageSent();
    }

    // TODO: Use this method or delete it
    private void editMessage(String username, Scanner scanner, TechConferenceSystem tcs){
        tcs.getPresenter().printUnderConstruction();
    }

    private void deleteMessage(String username, Scanner scanner, TechConferenceSystem tcs){
        String inboxChoice = viewInbox(username, scanner, tcs);
        int inboxSize = tcs.getMM().getInbox(username, inboxChoice).size();
        int index;
        while(true) {
            tcs.getPresenter().printAskWhichMessage();
            index = scanner.nextInt();
            if (0 <= index && index < inboxSize) {
                break;
            } else {
                tcs.getPresenter().printInvalidInput();
            }
        }
        tcs.getMM().deleteMessage(tcs.getMM().getChat(username, inboxChoice).get(index));
    }

    private String viewInbox(String username, Scanner scanner, TechConferenceSystem tcs){
        String inboxChoice;
        while (true) {
            tcs.getPresenter().printAskWhichInbox();
            tcs.getPresenter().printUCReturns(tcs.getMM().getChats(username));
            inboxChoice = scanner.nextLine();
            if (tcs.getMM().getChats(username).contains(inboxChoice)) {
                break;
            } else {
                tcs.getPresenter().printInvalidInput();
            }
        }
        List<String> inbox = tcs.getMM().getInbox(username, inboxChoice);

        int inboxSize = tcs.getMM().getInbox(username, inboxChoice).size();
        for (int i = 0; i < inboxSize; i++) {
            tcs.getPresenter().printUCReturns(i + ". " + inbox.get(i));
        }

        return inboxChoice;
    }

    /**
     * Implements a method used to save each UserManager, EventManager and MessageManager objects
     * to its designated .ser file.
     *
     */
    /*protected void save(){
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
    }*/
}

