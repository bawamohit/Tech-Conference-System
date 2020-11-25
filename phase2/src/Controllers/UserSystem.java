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

    // Helper method, sends a message from the User to a receiver (user input) with content (user input)
    private void sendMessage(String username, Scanner scanner, TechConferenceSystem tcs){
        String receiver;
        while(true) {
            tcs.presenter().printAskMsgReceiver();
            tcs.presenter().printBackToMainMenu();
            receiver = scanner.nextLine();
            if(receiver.equals("")) return;
            if(tcs.getUM().isRegistered(receiver)){
                break;
            } else{
                tcs.presenter().printInvalidInput();
            }
        }
        tcs.presenter().printAsk("message");
        tcs.presenter().printBackToMainMenu();
        String message = scanner.nextLine();
        if(message.equals("")) return;
        tcs.getMM().sendMessage(username, receiver, message);
        tcs.presenter().printMessageSent();
    }

    // Helper method, deletes a message in the User's inboxes (user chooses the message)
    private void deleteMessage(String username, Scanner scanner, TechConferenceSystem tcs){
        String receiver = viewInbox(username, scanner, tcs);
        if(receiver.equals("")) return;
        int inboxSize = tcs.getMM().getInboxString(username, receiver).size();
        String index;
        tcs.presenter().printAskWhichMessage();
        tcs.presenter().printBackToMainMenu();
        index = validInput("^[0-" + (inboxSize - 1) + "]$|^.{0}$", scanner, tcs);
        if(index.equals("")) return;
        tcs.getMM().deleteMessage(username, receiver, (Integer.parseInt(index)));
        tcs.presenter().printMessageDeleted();
    }

    // Helper method, views an inbox between the User and another chosen (by input) user
    private String viewInbox(String username, Scanner scanner, TechConferenceSystem tcs){
        String receiver;
        while (true) {
            tcs.presenter().printAskWhichInbox();
            tcs.presenter().printUCReturns(tcs.getMM().getInboxes(username));
            tcs.presenter().printBackToMainMenu();
            receiver = scanner.nextLine();
            if (receiver.equals("")){
                return receiver;
            } else if (tcs.getMM().getInboxes(username).contains(receiver)) {
                break;
            } else {
                tcs.presenter().printInvalidInput();
            }
        }
        List<String> inbox = tcs.getMM().getInboxString(username, receiver);
        for(String s: inbox) {
            tcs.presenter().printUCReturns(s);
        }

        return receiver;
    }

    /**
     * Implements a method used to format a list of strings to an numbered string.
     * This is used to format a list of information to a string, that shows each element inside the list as a numbered
     * string format.
     */
    protected String formatInfo(List<String> strings){//TODO feels like a presenter method
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
            tcs.presenter().printInvalidInput();
            input = scanner.nextLine();
        }
        return input;
    }
}

