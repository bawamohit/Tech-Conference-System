package Controllers;

import java.util.*;
import Use_cases.*;
import UI.Presenter;

/**
 * This class is one of the controllers of this program, specifically for attendees. It is a child class of UserSystem.
 */
public class AttendeeSystem extends UserSystem{

    /**
     * Implements the run method for all attendee users.
     * With this method, an attendee can logout, message (view, send, receive), view available events,
     * signup and remove themselves from events.
     *
     */
    public void run(String username, TechConferenceSystem tcs) {
        Scanner scanner = new Scanner(System.in);
        label:
        while (true) {
            tcs.getPresenter().printAttendeeMenu();
            String attendeeChoice = scanner.nextLine();

            switch (attendeeChoice) {
                case "0":
                    tcs.getPresenter().printLoggedOut();
                    break label;
                case "1":
                    while (true) {
                        tcs.getPresenter().printAttendeeMessageMenu();
                        String messageChoice = scanner.nextLine();
                        super.helperMessageSystem(username, messageChoice, scanner, tcs);
                        if (messageChoice.equals("b")) {
                            break;
                        } else if (!messageChoice.matches("^[0123]$")) {
                            tcs.getPresenter().printInvalidInput();
                        }
                    }
                    break;
                case "2":  //view available events
                    List<UUID> available = tcs.getEM().getAvailableEvents();
                    String formattedOutput = formatInfo(tcs.getEM().getEventsStrings(available));
                    if (formattedOutput.equals("")) {
                        tcs.getPresenter().printNoEventsAvailable();
                    }
                    else{
                        tcs.getPresenter().printAvailableEvents(formattedOutput);
                    }
                    break;
                case "3":
                    List<UUID> eventList = tcs.getUM().getEventsAttending(username);
                    tcs.getPresenter().printUCReturns(tcs.getEM().getEventsStrings(eventList));
                    break;
                case "4":  //signup event
                    while(true){
                        List<UUID> availEvents = tcs.getEM().getAvailableEvents();
                        List<String> eventInfo = tcs.getEM().getEventsStrings(availEvents);
                        tcs.getPresenter().printAskSignUp();
                        tcs.getPresenter().printAvailableEvents(formatInfo(eventInfo));
                        String choice = scanner.nextLine();
                        if (signupAttendeeHelper(username, choice, tcs)){
                            tcs.getPresenter().printEventSignUpSuccess();
                            break;
                        }
                        tcs.getPresenter().printInvalidInput();
                    }
                    break;
                case "5":  //cancel event
                    while(true){
                        if (cancelAttendeeHelper(username, scanner, tcs)){
                            tcs.getPresenter().printEventCancelSuccess();
                            break;
                        } else{
                            tcs.getPresenter().printInvalidInput();
                        }
                    }

                default:
                    tcs.getPresenter().printInvalidInput();
                    break;
            }
        }
    }


    private boolean signupAttendeeHelper(String username, String choice, TechConferenceSystem tcs) {
        List<UUID> availEvents = tcs.getEM().getAvailableEvents();
        if (choice.matches("^[0-9]*$")) {
            int eventChoice = Integer.parseInt(choice);
            if (!(eventChoice < availEvents.size())) {
                tcs.getPresenter().printInvalidInput();
                return false;
            } else{
                UUID id = availEvents.get(eventChoice);
                if (isAttendeeFree(username, id, tcs)){
                    if (tcs.getEM().addAttendee(username, id) && tcs.getUM().addEventAttending(username, id)) {
                        return true;
                    }
                } else{
                    tcs.getPresenter().printAlreadyBookedTime();
                    return false;
                }
            }
        } else {
            tcs.getPresenter().printInvalidInput();
            return false;
        }
        return false;
    }

    private boolean cancelAttendeeHelper(String username, Scanner scanner, TechConferenceSystem tcs){
        List<UUID> eventList = tcs.getUM().getEventsAttending(username);
        List<String> eventNames = (tcs.getEM().convertIDtoName(eventList));
        tcs.getPresenter().printUCReturns(eventNames);
        tcs.getPresenter().printAskWhichEventCancel();
        String eventChoice = scanner.nextLine();
        if (!eventNames.contains(eventChoice)){
            return false;
        }
        UUID eventRemoving = eventList.get(eventNames.indexOf(eventChoice));
        if (tcs.getUM().removeEventAttending(username, eventRemoving) && tcs.getEM().removeAttendee(username, eventRemoving)){
            return true;
        }
        tcs.getPresenter().printNotInEvent();
        return false;
    }

    private boolean isAttendeeFree(String username, UUID newEvent, TechConferenceSystem tcs){
        List<UUID> userEvents = tcs.getUM().getEventsAttending(username);
        for (UUID events :userEvents){
            if (!tcs.getEM().timeNotOverlap(events, newEvent)){
                return false;
            }
        }
        return true;
    }
}
