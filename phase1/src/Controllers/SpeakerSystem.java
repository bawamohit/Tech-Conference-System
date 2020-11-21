package Controllers;

import UI.SpeakerPresenter;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class SpeakerSystem extends UserSystem {
    private SpeakerPresenter presenter;

    public SpeakerSystem(){
        this.presenter = new SpeakerPresenter();
    }

    public void run(String username, TechConferenceSystem tcs){
        Scanner scanner = new Scanner(System.in);
        label:
        while(true){
            presenter.printSpeakerMenu();
            String choice = scanner.nextLine();

            switch (choice){
                case "0":
                    presenter.printLoggedOut();
                    break label;
                case "1":
                        presenter.printSpeakerMessageMenu();
                        choice = validInput("^[01234]$", scanner, tcs);
                        if(!choice.equals("0")) {
                            speakerHelperMessageSystem(username, choice, scanner, tcs);
                        }
                        break;
                case "2":
                    List<UUID> eventList = tcs.getUM().getEventsAttending(username);
                    presenter.printMyEvents(formatInfo(tcs.getEM().getEventsStrings(eventList)),
                            "speak at");
                default:

            }
        }
    }

    // Helper method, implements the general message system, and add Speaker-specific choices
    public void speakerHelperMessageSystem(String username, String choice, Scanner scan, TechConferenceSystem tcs) {
        super.helperMessageSystem(username, choice, scan, tcs);
        messageAll(username, choice, scan, tcs);
    }

    // Helper method, implements the additional Speaker-specific messaging choices
    public void messageAll(String username, String choice, Scanner scan, TechConferenceSystem tcs) {
        if (choice.equals("4")) {
            List<UUID> listEvents = tcs.getUM().getEventsAttending(username);
            String listChoice;
            while (true) {
                presenter.printAskWhichEvents();
                int n = 0;
                for (UUID eventID : listEvents) {
                    presenter.printUCReturns(n + ": " + eventID);
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
                    presenter.printInvalidInput();
                }
            }
            presenter.printAsk("message");
            String content = scan.nextLine();
            char[] listChoiceSorted = listChoice.toCharArray();
            for (Character eventID : listChoiceSorted) {
                List<String> attendeeList = tcs.getEM().getEventAttendees(listEvents.get((int)eventID));
                tcs.getMM().messageAll(username, attendeeList, content);
            }
        }
    }
}

