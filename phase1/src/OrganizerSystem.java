import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class OrganizerSystem extends UserSystem {

    public OrganizerSystem(Presenter p, UserManager uMan, EventManager eMan, MessageManager mMan, RoomManager rMan) {
        super(p, uMan, eMan, mMan, rMan);
    }

    public void run(String username) {
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Presenter p = getPresenter();
        EventManager em = getEventManager();
        while (true) {
            getPresenter().printOrganizerMenu();
            String option = sc.nextLine();
            if (option.equals("0")) {
                getPresenter().printLoggedOut();
                break;
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
                    Integer time = sc.nextInt();
                    p.printAsk("event's room name (enter room name)");
                    String roomName = sc.nextLine();
                    if (!rm.getRooms().contains(roomName)){
                        p.printDNE(roomName);
                    }else {
                        LocalDateTime startTime = LocalDateTime.of(2020, 6, 9, time, 0, 0);
                        int capacity = rm.getRoomCapacity(roomName);
                        if (em.addEvent(eventName, speaker, username, startTime, roomName, capacity)) {
                            p.printSuccess();
                            p.printBack();
                            if (!sc.nextLine().equals('b')) {
                                p.printInvalidInput();
                            } else {
                                break;
                            }
                        } else {
                            p.printFail(); //does this automatically prompt again?
                        }
                    }
                }
            } else if (option.equals("3")){
                p.printUnderConstruction();
            } else if (option.equals("4")) { //remove Event
                // ask for event id
                // remove event
                // print action successful or unsuccessful
            } else if (option.equals("5")) { //create speaker
                while (true) {
                    p.printAsk("speaker's name");
                    String speakerName = sc.nextLine();
                    p.printAsk("speaker's username");
                    String speakerUsername = sc.nextLine();
                    p.printAsk("speaker's account password");
                    String speakerPW = sc.nextLine();
                    if (um.registerUser(UserType.SPEAKER, speakerName, speakerUsername, speakerPW)) {
                        p.printSuccess();
                        p.printUserInfo(UserType.SPEAKER, speakerUsername, speakerPW);
                        break;
                    } else {
                        p.printUsernameExists(); //or invalid input?
                        p.printFail();
                    }
                }
            } else if (option.equals("6")) { //create new room
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
            List<String> userList = getUserManager().getUsernameList();
            for (String user : userList) {
                if (choice.equals("4") && getUserManager().getUserType(user) == UserType.SPEAKER) {
                    getMessageManager().sendMessage(username, user, content);
                } else if (getUserManager().getUserType(user) == UserType.ORGANIZER) {
                    getMessageManager().sendMessage(username, user, content);
                }
            }
        }
    }
}
