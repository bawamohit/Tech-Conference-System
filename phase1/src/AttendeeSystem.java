import java.util.*;

public class AttendeeSystem extends UserSystem{
    public AttendeeSystem (Presenter p, UserManager uMan, EventManager eMan, MessageManager mMan, RoomManager rMan) {
        super(p, uMan, eMan, mMan, rMan);
    }

    public void run(String username) {
        Scanner scanner = new Scanner(System.in);
        label:
        while (true) {
            getPresenter().printAttendeeMenu();
            String attendeeChoice = scanner.nextLine();

            switch (attendeeChoice) {
                case "0":
                    getPresenter().printLoggedOut();
                    break label;
                case "1":
                    while (true) {
                        getPresenter().printAttendeeMessageMenu();
                        String messageChoice = scanner.nextLine();
                        super.helperMessageSystem(username, messageChoice, scanner);
                        if (!messageChoice.equals("b")) {
                            getPresenter().printInvalidInput();
                        } else {
                            break;
                        }
                    }
                    break;
                case "2":  //view available events
                    List<UUID> available = em.getAvailableEvents();
                    getPresenter().printAvailableEvents(formatInfo(em.getEventsStrings(available)));
                    break;
                case "3":
                    List<UUID> eventlist = getUserManager().getEventsAttending(username);
                    getPresenter().printUCReturns(em.convertIDtoName(eventlist));
                    break;
                case "4":  //signup event
                    signupAttendeeHelper(username, scanner);
                    break;
                case "5":  //cancel event
                    while(true){
                        if (cancelAttendeeHelper(username, scanner)){
                            getPresenter().printEventCancelSuccess();
                        } else{
                            getPresenter().printInvalidInput();
                            break;}
                    }

                default:
                    getPresenter().printInvalidInput();
                    break;
            }
        }
    }

    private String formatInfo(List<String> eventStrings){
        String info = null;
        int i = 0;
        for (String event: eventStrings){
            info += "\n" + i + ": " + event;
        }
        return info;
    }

    private void signupAttendeeHelper(String username, Scanner scanner) {
        List<UUID> availEvents = em.getAvailableEvents();
        List<String> eventInfo = em.getEventsStrings(availEvents);
        HashMap<String, UUID> stringToID = em.getStringToID(availEvents);
        getPresenter().printAskSignUp();
        getPresenter().printAvailableEvents(formatInfo(eventInfo));
        String choice = scanner.nextLine();
        if (choice.matches("^[0-9]*$")) {
            int eventChoice = Integer.parseInt(choice);
            if (!(eventChoice < availEvents.size())) {
                getPresenter().printInvalidInput();
            }
            else{
                int i = Integer.parseInt(choice);
                String eventString = eventInfo.get(i);
                UUID id = stringToID.get(eventString);
                String roomName = eventString.split(", ")[2];
                if (isAttendeeFree(username, id) && (em.getEventAttendees(id).size() < getRoomManager().getRoomCapacity(roomName))){
                    em.addAttendee(username, id);
                    getUserManager().addEventAttending(username, id);
                    getPresenter().printEventSignUpSuccess();
                }
                else{
                    if (em.getEventAttendees(id).size() >= getRoomManager().getRoomCapacity(roomName)){
                        getPresenter().printEventFull();
                    } else {
                        getPresenter().printAlreadyBookedTime();
                    }
                }
            }
        } else {
            getPresenter().printInvalidInput();
        }
    }

    private boolean cancelAttendeeHelper(String username, Scanner scanner){
        List<UUID> eventlist = getUserManager().getEventsAttending(username);
        List<String> eventnames = (em.convertIDtoName(eventlist));
        getPresenter().printUCReturns(eventnames);
        getPresenter().printAskWhichEventCancel();
        String eventChoice = scanner.nextLine();
        if (!eventnames.contains(eventChoice)){
            return false;
        }
        UUID eventRemoving = eventlist.get(eventnames.indexOf(eventChoice));
        getUserManager().removeEventAttending(username, eventRemoving);
        em.removeAttendee(username, eventRemoving);

        return true;
    }

    private boolean isAttendeeFree(String username, UUID newEvent){
        List<UUID> userEvents = getUserManager().getEventsAttending(username);
        for (UUID events :userEvents){
            if (em.ifTimeOverlap(events, newEvent)){
                return false;
            }
        }
        return true;
    }
}
