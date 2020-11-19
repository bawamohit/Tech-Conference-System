import java.util.*;

/**
 * This class is one of the controllers of this program, specifically for attendees. It is a childclass of UserSystem.
 */
public class AttendeeSystem extends UserSystem{
    /**
     * The constructor stores Presenter, UserManager, EventManger, MessageManager and assigns each variable.
     * It inherits these variables from its parent class, UserSystem.
     *
     * @param p name of event
     * @param uMan speaker of event
     * @param eMan organizer of event
     * @param mMan date and time of event
     */
    public AttendeeSystem (Presenter p, UserManager uMan, EventManager eMan, MessageManager mMan) {
        super(p, uMan, eMan, mMan);
    }

    /**
     * Implements the run method for all attendee users.
     * With this method, an attendee can logout, message (view, send, recieve), view available events,
     * signup and remove themselves from events.
     *
     */
    public void run(String username) {
        Scanner scanner = new Scanner(System.in);
        label:
        while (true) {
            presenter.printAttendeeMenu();
            String attendeeChoice = scanner.nextLine();

            switch (attendeeChoice) {
                case "0":
                    presenter.printLoggedOut();
                    break label;
                case "1":
                    while (true) {
                        presenter.printAttendeeMessageMenu();
                        String messageChoice = scanner.nextLine();
                        super.helperMessageSystem(username, messageChoice, scanner);
                        if (messageChoice.equals("b")) {
                            break;
                        } else if (!messageChoice.matches("^[0123]$")) {
                            presenter.printInvalidInput();
                        }
                    }
                    break;
                case "2":  //view available events
                    List<UUID> available = eventM.getAvailableEvents();
                    String formattedOutput = formatInfo(eventM.getEventsStrings(available));
                    if (formattedOutput.equals("")) {
                        presenter.printNoEventsAvailable();
                    }
                    else{
                        presenter.printAvailableEvents();
                    }
                    break;
                case "3":
                    List<UUID> eventList = userM.getEventsAttending(username);
                    presenter.printUCReturns(eventM.convertIDtoName(eventList));
                    break;
                case "4":  //signup event
                    while(true){
                        List<UUID> availEvents = eventM.getAvailableEvents();
                        List<String> eventInfo = eventM.getEventsStrings(availEvents);
                        presenter.printAskSignUp();
                        presenter.printAvailableEvents(formatInfo(eventInfo));
                        String choice = scanner.nextLine();
                        if (signupAttendeeHelper(username, choice)){
                            presenter.printEventSignUpSuccess();
                            break;
                        }
                        presenter.printInvalidInput();
                    }
                    break;
                case "5":  //cancel event
                    while(true){
                        if (cancelAttendeeHelper(username, scanner)){
                            presenter.printEventCancelSuccess();
                            break;
                        } else{
                            presenter.printInvalidInput();
                        }
                    }

                default:
                    presenter.printInvalidInput();
                    break;
            }
        }
    }


    private boolean signupAttendeeHelper(String username, String choice) {
        List<UUID> availEvents = eventM.getAvailableEvents();
        if (choice.matches("^[0-9]*$")) {
            int eventChoice = Integer.parseInt(choice);
            if (!(eventChoice < availEvents.size())) {
                presenter.printInvalidInput();
                return false;
            } else{
                UUID id = availEvents.get(eventChoice);
                if (isAttendeeFree(username, id)){
                    if (eventM.addAttendee(username, id) && userM.addEventAttending(username, id)) {
                        return true;
                    }
                } else{
                    presenter.printAlreadyBookedTime();
                    return false;
                }
            }
        } else {
            presenter.printInvalidInput();
            return false;
        }
        return false;
    }

    private boolean cancelAttendeeHelper(String username, Scanner scanner){
        List<UUID> eventList = userM.getEventsAttending(username);
        List<String> eventNames = (eventM.convertIDtoName(eventList));
        presenter.printUCReturns(eventNames);
        presenter.printAskWhichEventCancel();
        String eventChoice = scanner.nextLine();
        if (!eventNames.contains(eventChoice)){
            return false;
        }
        UUID eventRemoving = eventList.get(eventNames.indexOf(eventChoice));
        if (userM.removeEventAttending(username, eventRemoving) && eventM.removeAttendee(username, eventRemoving)){
            return true;
        }
        presenter.printNotInEvent();
        return false;
    }

    private boolean isAttendeeFree(String username, UUID newEvent){
        List<UUID> userEvents = userM.getEventsAttending(username);
        for (UUID events :userEvents){
            if (!eventM.timeNotOverlap(events, newEvent)){
                return false;
            }
        }
        return true;
    }
}
