package Controllers;

import java.util.List;
import java.util.Scanner;

public abstract class UserSystem {

    abstract void run(String username, TechConferenceSystem tcs);

    // Implements the general message system for all user types
    protected void helperMessageSystem(String username, String choice, Scanner scanner, TechConferenceSystem tcs){
        switch (choice) {
            case "1":
                sendMessage(username, scanner, tcs);
                break;
            case "2":
                //edit Message
                tcs.getPresenter().printUnderConstruction();
                break;
            case "3":
                // delete Message
                deleteMessage(username, scanner, tcs);
                break;
            case "4":
                // view inbox
                viewInbox(username, scanner, tcs);
                break;
            default:
                break;
        }
    }

    // Helper method, sends a message from the User to a receiver (user input) with content (user input)
    private void sendMessage(String username, Scanner scanner, TechConferenceSystem tcs){
        String receiver;
        while(true) {
            tcs.getPresenter().printAskMsgReceiver();
            tcs.getPresenter().printBackToMainMenu();
            receiver = scanner.nextLine();
            if(receiver.equals("")) return;
            if(tcs.getUM().isRegistered(receiver)){
                break;
            } else{
                tcs.getPresenter().printInvalidInput();
            }
        }
        tcs.getPresenter().printAsk("message");
        tcs.getPresenter().printBackToMainMenu();
        String message = scanner.nextLine();
        if(message.equals("")) return;
        tcs.getMM().sendMessage(username, receiver, message);
        tcs.getPresenter().printMessageSent();
    }

    // Helper method, deletes a message in the User's inboxes (user chooses the message)
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
        tcs.getMM().deleteMessage(tcs.getMM().getInbox(username, inboxChoice).get(index));
    }

    // Helper method, views an inbox between the User and another chosen (by input) user
    private String viewInbox(String username, Scanner scanner, TechConferenceSystem tcs){
        String inboxChoice;
        while (true) {
            tcs.getPresenter().printAskWhichInbox();
            tcs.getPresenter().printUCReturns(tcs.getMM().getInboxes(username));
            inboxChoice = scanner.nextLine();
            if (tcs.getMM().getInboxes(username).contains(inboxChoice)) {
                break;
            } else {
                tcs.getPresenter().printInvalidInput();
            }
        }
        List<String> inbox = tcs.getMM().getInboxString(username, inboxChoice);
        String inboxToString = formatInfo(inbox);
        tcs.getPresenter().printUCReturns(inboxToString);
        return inboxChoice;
    }

    /**
     * Implements a method used to format a list of strings to an numbered string.
     * This is used to format a list of information to a string, that shows each element inside the list as a numbered
     * string format.
     */
    protected String formatInfo(List<String> strings){
        StringBuilder info = new StringBuilder();
        for (int i = 0; i < strings.size(); i++){
            info.append("\n").append(i).append(". ").append(strings.get(i));
        }

        return info.toString();
    }

    // Helper method, checks whether the user's input is valid in respect to the menu options
    protected String validInput(String pattern, Scanner scanner, TechConferenceSystem tcs){
        String input = scanner.nextLine();
        while(!input.matches(pattern)){
            tcs.getPresenter().printInvalidInput();
            input = scanner.nextLine();
        }
        return input;
    }

    // TODO: Use this method or delete it
    private void editMessage(String username, Scanner scanner, TechConferenceSystem tcs){
        tcs.getPresenter().printUnderConstruction();
    }
}

