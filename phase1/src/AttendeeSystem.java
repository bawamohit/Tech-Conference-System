import java.util.*;

public class AttendeeSystem extends UserSystem{
    public AttendeeSystem (Presenter p, UserManager uMan, EventManager eMan, MessageManager mMan, RoomManager rMan) {
        super(p, uMan, eMan, mMan, rMan);
    }

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
                        if (!messageChoice.equals("b")) {
                            presenter.printInvalidInput();
                        } else {
                            break;
                        }
                    }
                    break;
                case "2":  //view available events
                    List<UUID> available = eventM.getAvailableEvents();
                    presenter.printAvailableEvents(formatInfo(eventM.getEventsStrings(available)));
                    break;
                case "3":
                    List<UUID> eventlist = userM.getEventsAttending(username);
                    presenter.printUCReturns(eventM.convertIDtoName(eventlist));
                    break;
                case "4":  //signup event
                    signupAttendeeHelper(username, scanner);
                    break;
                case "5":  //cancel event
                    while(true){
                        if (cancelAttendeeHelper(username, scanner)){
                            presenter.printEventCancelSuccess();
                        } else{
                            presenter.printInvalidInput();
                            break;}
                    }

                default:
                    presenter.printInvalidInput();
                    break;
            }
        }
    }

    private String formatInfo(List<String> eventStrings){
        String info = "";
        int i = 0;
        for (String event: eventStrings){
            info += "\n" + i + ": " + event;
            i += 1;
        }
        if (info.equals("")) {
            presenter.printNoEventsAvailable();
            return "";
        }
        return info;
    }

    private void signupAttendeeHelper(String username, Scanner scanner) {
        List<UUID> availEvents = eventM.getAvailableEvents();
        List<String> eventInfo = eventM.getEventsStrings(availEvents);
        presenter.printAskSignUp();
        presenter.printAvailableEvents(formatInfo(eventInfo));
        String choice = scanner.nextLine();
        if (choice.matches("^[0-9]*$")) {
            int eventChoice = Integer.parseInt(choice);
            if (!(eventChoice < availEvents.size())) {
                presenter.printInvalidInput();
            }
            else{
                UUID id = eventM.getAvailableEvents().get(eventChoice);
                if (isAttendeeFree(username, id)){
                    eventM.addAttendee(username, id);
                    userM.addEventAttending(username, id);
                    presenter.printEventSignUpSuccess();
                }
                else{
                    presenter.printAlreadyBookedTime();
                }
            }
        } else {
            presenter.printInvalidInput();
        }
    }

    private boolean cancelAttendeeHelper(String username, Scanner scanner){
        List<UUID> eventlist = userM.getEventsAttending(username);
        List<String> eventnames = (eventM.convertIDtoName(eventlist));
        presenter.printUCReturns(eventnames);
        presenter.printAskWhichEventCancel();
        String eventChoice = scanner.nextLine();
        if (!eventnames.contains(eventChoice)){
            return false;
        }
        UUID eventRemoving = eventlist.get(eventnames.indexOf(eventChoice));
        userM.removeEventAttending(username, eventRemoving);
        eventM.removeAttendee(username, eventRemoving);

        return true;
    }

    private boolean isAttendeeFree(String username, UUID newEvent){
        List<UUID> userEvents = userM.getEventsAttending(username);
        for (UUID events :userEvents){
            if (eventM.ifTimeOverlap(events, newEvent)){
                return false;
            }
        }
        return true;
    }
}
