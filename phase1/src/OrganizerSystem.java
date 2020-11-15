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
        Presenter p = getPresenter();
        EventManager em = getEm();
        getPresenter().printOrganizerMenu();
        String option = sc.nextLine();
        if (option.equals("2")) { //create event
            while (true) {
                p.printAsk("event's name");
                String eventName = sc.nextLine();
                p.printAsk("event speaker's name");
                String speaker = sc.nextLine();
                p.printAsk("event's start time (enter a number from 9-17)");
                String time = sc.nextLine();
                Integer hour = Integer.parseInt(time);
                p.printAsk("event's room name");
                String roomName = sc.nextLine();
                p.printAsk("event's maximum capacity");
                int max = Integer.parseInt(sc.nextLine());
                LocalDateTime startTime = LocalDateTime.of(2020, 6, 9, hour, 00);
                if (em.addEvent(eventName, speaker, username, startTime, roomName, max)) {
                    p.printSuccess();
                    p.printBack();
                    if (sc.nextLine().equals('b')){
                        run(username);
                    } else {
                        getPresenter().printInvalidInput();
                    }//is this how you return
                } else {
                    p.printFail();
                    //prompt again
                }
            }
        } else if (option.equals("1")) { // message
            p.printOrganizerMessageMenu();
            String messageChoice = sc.nextLine();
            organizerHelperMessageSystem(username, option, sc);
        } else if (option.equals("4")) { //remove event
            p.printUnderConstruction();
            // ask for event id
            // remove event
            // print action successful or unsuccessful
        } else if (option.equals("5")) { //create speaker
            p.printAsk("speaker's name");
            String speakerName = sc.nextLine();
            p.printAsk("speaker's username");
            String speakerUsername = sc.nextLine();
            p.printAsk("speaker's account password");
            String speakerPW = sc.nextLine();
            if (um.registerUser(UserType.SPEAKER, speakerName, speakerUsername, speakerPW)) {
                p.printSuccess();
                p.printUserInfo(UserType.SPEAKER, speakerUsername, speakerPW);
            } else {
                p.printUsernameExists();
                p.printFail();
                }
        } else if (option.equals("6")) { //create room
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

    private void organizerHelperMessageSystem(String username, String option, Scanner sc) {
        super.helperMessageSystem(username, option, sc);
        if (option.equals("4") || option.equals("5")) {
            getPresenter().printAsk("message");
            String content = sc.nextLine();
            List<String> userList = getUm().getUsernameList();
            for (String user : userList) {
                if (option.equals("4") && getUm().getUserType(user) == UserType.SPEAKER) {
                    getMm().sendMessage(username, user, content);
                } else if (getUm().getUserType(user) == UserType.ORGANIZER) {
                    getMm().sendMessage(username, user, content);
                }
            }
        }
    }
}
