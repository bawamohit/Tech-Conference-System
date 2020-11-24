package Controllers;

import UI.SpeakerPresenter;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * This class is one of the controllers of this program, specifically for speakers. It is a child class of UserSystem.
 */
public class SpeakerSystem extends UserSystem {
    private SpeakerPresenter presenter;

    public SpeakerSystem(){
        this.presenter = new SpeakerPresenter();
    }

    /**
     * Implements the run method for all speaker users.
     *
     */
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
    private void speakerHelperMessageSystem(String username, String choice, Scanner scan, TechConferenceSystem tcs) {
        if(choice.matches("4")){
            messageAll(username, scan, tcs);
        }else {
            super.helperMessageSystem(username, choice, scan, tcs);
        }
    }

    // Helper method, implements the additional Speaker-specific messaging choices
    private void messageAll(String username, Scanner scanner, TechConferenceSystem tcs) {//TODO message multiple events simultaneously
        List<UUID> listEvents = tcs.getUM().getEventsAttending(username);//TODO get events speaking at?, includes event not speaking at
        if(listEvents.isEmpty()){
            presenter.printDNE("events");//TODO presenter to say no events
            return;
        }
        String eventInfo = formatInfo(tcs.getEM().getEventsStrings(listEvents));
        presenter.printUCReturns(eventInfo);
        presenter.printAskWhichEvents();
        presenter.printBackToMainMenu();
        String index = validInput("^[0-" + (listEvents.size() - 1) + "]$|^.{0}$", scanner, tcs);
        if(index.equals("")) return;
        List<String> attendeeList = tcs.getEM().getEventAttendees(listEvents.get(Integer.parseInt(index)));

        presenter.printAsk("message");
        presenter.printBackToMainMenu();
        String content = validInput(".*", scanner, tcs);
        if(content.equals("")) return;
        tcs.getMM().messageAll(username, attendeeList, content);
    }
}

