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
                List<UUID> available = em.getAvailableEvents();
                getPresenter().printAvailableEvents(formatInfo(em.getEventsStrings(available)));
                getPresenter().printAskSignUp();
                Integer i = Integer.parseInt(scanner.nextLine());
                if (i >= 0 && i <= available.size()){
                    //split based on colon
                    //find in ID to string
                    //get room capacity (RM), get attendee list (EM), compare the two to see if can sign up
                    //add dude to attendee list; add event to user's attending
                }else{
                    getPresenter().printInvalidInput();
                    //prompt again
                }
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

//    private void signupAttendeeHelper(String username, Scanner scanner) {
//        List<UUID> availEvents = getEm().getAvailableEvents();
//        HashMap<String, HashMap<LocalDateTime, String>> eventInfo =
//                getEm().getEventsInfo(availEvents);
//        System.out.println(formatEventsInfo(eventInfo));
//        String choice = scanner.nextLine();
//        if (choice.matches("^[0-9]*$")) {
//            int eventChoice = Integer.valueOf(choice);
//            if (eventChoice < getEm().getAvailableEvents().size()) {
//                UUID id = getEm().getAvailableEvents().get(eventChoice);
//                if (getUm().getEventsAttending(username).)
//                    getEm().addAttendee(username, id);
//
//            }
//        } else {
//        }
//    }
}
