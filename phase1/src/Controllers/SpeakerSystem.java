package Controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class SpeakerSystem extends UserSystem{

    public void run(String username, TechConferenceSystem tcs){
        Scanner scan = new Scanner(System.in);

        while(true){
            tcs.getPresenter().printSpeakerMenu();
            String speakerChoice = scan.nextLine();

            switch (speakerChoice){
                case "0":
                    tcs.getPresenter().printLoggedOut();
                    break;
                case "1":
                    while (true) {
                        tcs.getPresenter().printSpeakerMessageMenu();
                        String messageChoice = scan.nextLine();
                        speakerHelperMessageSystem(username, messageChoice, scan, tcs);
                        if(messageChoice.equals("b")) {
                            break;
                        } else if (!messageChoice.matches("^[01234]$")) {
                            tcs.getPresenter().printInvalidInput();
                        }
                    }
                case "2":

                default:

            }
        }
    }

    //TODO: Complete this method
    public void speakerHelperMessageSystem(String username, String choice, Scanner scan, TechConferenceSystem tcs) {
        super.helperMessageSystem(username, choice, scan, tcs);
        messageAll(username, choice, scan, tcs);
    }

    public void messageAll(String username, String choice, Scanner scan, TechConferenceSystem tcs) {
        if (choice.equals("4")) {
            List<UUID> listEvents = tcs.getUM().getEventsAttending(username);
            String listChoice;
            while (true) {
                tcs.getPresenter().printAskWhichEvents();
                int n = 0;
                for (UUID eventID : listEvents) {
                    tcs.getPresenter().printUCReturns(n + ": " + eventID);
                    n += 1;
                }
                listChoice = scan.nextLine();
                char[] listChoiceSorted = listChoice.toCharArray();
                Arrays.sort(listChoiceSorted);
                listChoice = new String(listChoiceSorted);
                String possibleChoices = "";
                for (int i = 0; i < n; i ++) {
                    possibleChoices = possibleChoices.concat(String.valueOf(i));
                }
                if (possibleChoices.contains(listChoice)) {
                    break;
                } else {
                    tcs.getPresenter().printInvalidInput();
                }
            }
            tcs.getPresenter().printAsk("message");
            String content = scan.nextLine();
            char[] listChoiceSorted = listChoice.toCharArray();
            for (Character eventID : listChoiceSorted) {
                List<String> attendeeList = tcs.getEM().getEventAttendees(listEvents.get((int)eventID));
                for (String user : attendeeList) {
                    tcs.getMM().sendMessage(username, user, content);
                }
            }
        }
    }
}
