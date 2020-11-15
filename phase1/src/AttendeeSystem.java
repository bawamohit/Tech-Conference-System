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
                getPresenter().printAttendeeMessageMenu();
                String messageChoice = scanner.nextLine();
                super.helperMessageSystem(username, messageChoice, scanner);
            } else if (attendeeChoice.equals("2")) {
                List<UUID> available = em.getAvailableEvents();
                getPresenter().printAvailableEvents(formatEventsInfo(em.getEventsInfo(available)));
            } else if (attendeeChoice.equals("3")) {//we need to make list of event names.
//                getPresenter().printUCReturns(getUm().getEventsAttending(username));
                getPresenter().printUnderConstruction();
            } else if (attendeeChoice.equals("4")) { //signup event
//                System.out.println(makeOrderedPromptLists(getEm().getEvents()));
                getPresenter().printUnderConstruction();
            } else if (attendeeChoice.equals("5")){ //cancel event
//                getPresenter().printUCReturns(getUm().getEventsAttending(username));
//                System.out.println("Type the name of event you would like to remove");
//                String eventChoice = scanner.nextLine();
                getPresenter().printUnderConstruction();
            } else {
                getPresenter().printInvalidInput();
            }
        }
    }

    private String formatEventsInfo(HashMap<String, HashMap<LocalDateTime, String>> events_info) {
        String info = null;
        for (int i = 0; i < events_info.size(); i++) {
            for (String name: events_info.keySet()){
                HashMap<LocalDateTime, String> map = events_info.get(name);
                for (LocalDateTime time: map.keySet()){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H");
                    String formattedTime = time.format(formatter);
                    info += "\n" + i + ". " + name + "@ " + formattedTime + "in " + map.get(time);
                }
            }
        }
        return info;
    }
}
