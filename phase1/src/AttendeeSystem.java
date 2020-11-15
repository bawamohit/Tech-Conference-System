import java.util.*;

public class AttendeeSystem extends UserSystem{
    public AttendeeSystem (Presenter p, UserManager uMan, EventManager eMan, MessageManager mMan) {
        super(p, uMan, eMan, mMan);
    }

    public void run(String username) {
        Scanner scanner = new Scanner(System.in);
        EventManager em = getEm();
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
                    List<UUID> eventlist = getUm().getEventsAttending(username);
                    getPresenter().printUCReturns(getEm().convertIDtoName(eventlist));
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
        List<UUID> availEvents = getEm().getAvailableEvents();
        List<String> eventInfo = getEm().getEventsStrings(availEvents);
        getPresenter().printAskSignUp();
        getPresenter().printAvailableEvents(formatInfo(eventInfo));
        String choice = scanner.nextLine();
        if (choice.matches("^[0-9]*$")) {
            int eventChoice = Integer.parseInt(choice);
            if (!(eventChoice < availEvents.size())) {
                getPresenter().printInvalidInput();
            }
            else{
                UUID id = getEm().getAvailableEvents().get(eventChoice);
                if (isAttendeeFree(username, id)){
                    getEm().addAttendee(username, id);
                    getUm().addEventAttending(username, id);
                    getPresenter().printEventSignUpSuccess();
                }
                else{
                    getPresenter().printAlreadyBookedTime();
                }
            }
        } else {
            getPresenter().printInvalidInput();
        }
    }

    private boolean cancelAttendeeHelper(String username, Scanner scanner){
        List<UUID> eventlist = getUm().getEventsAttending(username);
        List<String> eventnames = (getEm().convertIDtoName(eventlist));
        getPresenter().printUCReturns(eventnames);
        getPresenter().printAskWhichEventCancel();
        String eventChoice = scanner.nextLine();
        if (!eventnames.contains(eventChoice)){
            return false;
        }
        UUID eventRemoving = eventlist.get(eventnames.indexOf(eventChoice));
        getUm().removeEventAttending(username, eventRemoving);
        getEm().removeAttendee(username, eventRemoving);

        return true;
    }

    private boolean isAttendeeFree(String username, UUID newEvent){
        List<UUID> userEvents = getUm().getEventsAttending(username);
        for (UUID events :userEvents){
            if (getEm().ifTimeOverlap(events, newEvent)){
                return false;
            }
        }
        return true;
    }
}
