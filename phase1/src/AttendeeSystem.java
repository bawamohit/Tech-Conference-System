import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AttendeeSystem extends UserSystem{
    public AttendeeSystem (Presenter p, UserManager uMan, EventManager eMan, MessageManager mMan) {
        super(p, uMan, eMan, mMan);
    }

    public void run(String username) {
        Scanner scanner = new Scanner(System.in);
        EventManager em = getEm();
        while (true) {
            getPresenter().printAttendeeMenu();
            String attendeeChoice = scanner.nextLine();

            if (attendeeChoice.equals("0")) {
                getPresenter().printLoggedOut();
                break;
            } else if (attendeeChoice.equals("1")) {
                while (true) {
                    getPresenter().printAttendeeMessageMenu();
                    String messageChoice = scanner.nextLine();
                    super.helperMessageSystem(username, messageChoice, scanner);
                    if(!messageChoice.equals("b")) {
                        getPresenter().printInvalidInput();
                    } else {
                        break;
                    }
                }
            } else if (attendeeChoice.equals("2")) { //view available events
                List<UUID> available = em.getAvailableEvents();
                getPresenter().printAvailableEvents(formatInfo(em.getEventsStrings(available)));
            } else if (attendeeChoice.equals("3")) {//we need to make list of event names.
                getPresenter().printUnderConstruction();
            } else if (attendeeChoice.equals("4")) { //signup event
                signupAttendeeHelper(username, scanner);
            } else if (attendeeChoice.equals("5")) { //cancel event
//                getPresenter().printUCReturns(getUm().getEventsAttending(username));
//                System.out.println("Type the name of event you would like to remove");
//                String eventChoice = scanner.nextLine();
                getPresenter().printUnderConstruction();
            } else {
                getPresenter().printInvalidInput();
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
        getPresenter().printAvailableEvents(formatInfo(em.getEventsStrings(availEvents)));
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
