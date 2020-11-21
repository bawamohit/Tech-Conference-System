package Controllers;

import java.util.*;

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
            String choice = scanner.nextLine();

            switch (choice) {
                case "0":
                    tcs.getPresenter().printLoggedOut();
                    break label;
                case "1":
                    tcs.getPresenter().printAttendeeMessageMenu();
                    choice = validInput("^[01234]$", scanner, tcs);
                    if(!choice.equals(Character.toString('0'))) {
                        super.helperMessageSystem(username, choice, scanner, tcs);
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
                    tcs.getPresenter().printMyEvents(formatInfo(tcs.getEM().getEventsStrings(eventList)), "attend");
                    break;
                case "4":  //signup event
                    while(true){
                        List<UUID> availEvents = tcs.getEM().getAvailableEvents();
                        List<String> eventInfo = tcs.getEM().getEventsStrings(availEvents);
                        tcs.getPresenter().printAskSignUp();
                        tcs.getPresenter().printAvailableEvents(formatInfo(eventInfo));
                        choice = scanner.nextLine();
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
                    break;
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
                    return tcs.getEM().addAttendee(username, id) && tcs.getUM().addEventAttending(username, id);
                } else{
                    tcs.getPresenter().printAlreadyBookedTime();
                    return false;
                }
            }
        } else {
            tcs.getPresenter().printInvalidInput();
            return false;
        }
    }

    private boolean cancelAttendeeHelper(String username, Scanner scanner, TechConferenceSystem tcs){
        List<UUID> eventList = tcs.getUM().getEventsAttending(username);
        tcs.getPresenter().printMyEvents(formatInfo(tcs.getEM().getEventsStrings(eventList)), "attend");
        tcs.getPresenter().printAskWhichEventCancel();
        int choice = Integer.parseInt(scanner.nextLine());
        if (!(choice <= eventList.size() - 1)){
            return false;
        }
        UUID id = eventList.get(choice);
        if (tcs.getUM().removeEventAttending(username, id) && tcs.getEM().removeAttendee(username, id)){
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
