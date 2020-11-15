import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class OrganizerSystem extends UserSystem {
    private RoomManager rm;

    public OrganizerSystem(Presenter p, UserManager uMan, EventManager eMan, MessageManager mMan, RoomManager rMan) {
        super(p, uMan, eMan, mMan);
        RoomManager rm = rMan;
    }

    public void run(String username) {
        System.out.println("testing");
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H"); //determines pattern we want to string to be
        Presenter p = getPresenter();
        EventManager em = getEm();
        while (true) {
            getPresenter().printOrganizerMenu();
            String option = sc.nextLine();
            
            if (option.equals("0")) {
                getPresenter().printLoggedOut();
            } else if (option.equals("1")) {
                while (true) {
                    getPresenter().printOrganizerMessageMenu();
                    String messageChoice = sc.nextLine();
                    organizerHelperMessageSystem(username, messageChoice, sc);
                    if(!messageChoice.equals("b")) {
                        getPresenter().printInvalidInput();
                    } else {
                        break;
                    }
                }
            } else if (option.equals("2")) {
                while (true) {
                    p.printAsk("event's name");
                    String eventName = sc.nextLine();
                    p.printAsk("event speaker's name");
                    String speaker = sc.nextLine();
                    p.printAsk("event's start time (enter a number from 9-17)");
                    String time = sc.nextLine();
                    p.printAsk("event's room name (enter room name)");
                    String roomName = sc.nextLine();
                    LocalDateTime startTime = LocalDateTime.parse(time, formatter);
                    if (em.addEvent(eventName, speaker, username, startTime, roomName)) {
                        p.printSuccess();
                        //return to menu
                        break;
                    } else {
                        p.printFail();
                        //prompt again
                    }
                }
            } else if (option.equals("8")) {
                p.printUnderConstruction();
                // ask for event id
                // remove event
                // print action successful or unsuccessful
            } else if (option.equals("9")) {
                while (true) {
                    // create speaker account stuff
                }
            } else if (option.equals("10")) {
                while (true) {
                    p.printAsk("new room's name");
                    String roomName = sc.nextLine();
//                p.printAsk("new room's maximum capacity");
//                String capacity = sc.nextLine();
                    if (rm.addRoom(roomName, 2)) {
                        p.printSuccess();
                        //return to menu
                        break;
                    } else {
                        p.printFail();
                    }
                }
            }
        }
    }

    private void organizerHelperMessageSystem(String username, String choice, Scanner sc) {
        super.helperMessageSystem(username, choice, sc);
        messageAll(username, choice, sc);
    }

    private void messageAll(String username, String choice, Scanner sc) {
        if (choice.equals("4") || choice.equals("5")) {
            getPresenter().printAsk("message");
            String content = sc.nextLine();
            List<String> userList = getUm().getUsernameList();
            for (String user : userList) {
                if (choice.equals("4") && getUm().getUserType(user) == UserType.SPEAKER) {
                    getMm().sendMessage(username, user, content);
                } else if (getUm().getUserType(user) == UserType.ORGANIZER) {
                    getMm().sendMessage(username, user, content);
                }
            }
        }
    }
}
