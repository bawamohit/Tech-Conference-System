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
        while (true) {
            presenter.printOrganizerMenu();
            String option = sc.nextLine();
            if (option.equals("0")) {
                presenter.printLoggedOut();
                break;
            } else if (option.equals("1")) {
                while (true) {
                    presenter.printOrganizerMessageMenu();
                    String messageChoice = sc.nextLine();
                    organizerHelperMessageSystem(username, messageChoice, sc);
                    if(!messageChoice.equals("b")) {
                        presenter.printInvalidInput();
                    } else {
                        break;
                    }
                }
            } else if (option.equals("2")) {
                while (true) {
                    presenter.printAsk("event's name");
                    String eventName = sc.nextLine();
                    presenter.printAsk("event speaker's name");
                    String speaker = sc.nextLine();
                    presenter.printAsk("event's start time (enter a number from 9-17)");
                    Integer time = sc.nextInt();
                    presenter.printAsk("event's room name (enter room name)");
                    String roomName = sc.nextLine();
                    if (!roomM.getRooms().contains(roomName)){
                        presenter.printDNE(roomName);
                    }else {
                        LocalDateTime startTime = LocalDateTime.of(2020, 6, 9, time, 0, 0);
                        int capacity = roomM.getRoomCapacity(roomName);
                        if (eventM.addEvent(eventName, speaker, username, startTime, roomName, capacity)) {
                            presenter.printSuccess();
                            presenter.printBack();
                            if (!sc.nextLine().equals('b')) {
                                presenter.printInvalidInput();
                            } else {
                                break;
                            }
                        } else {
                            presenter.printFail(); //does this automatically prompt again?
                        }
                    }
                }
            } else if (option.equals("3")){
                presenter.printUnderConstruction();
            } else if (option.equals("4")) { //remove Event
                // ask for event id
                // remove event
                // print action successful or unsuccessful
            } else if (option.equals("5")) { //create speaker
                while (true) {
                    presenter.printAsk("speaker's name");
                    String speakerName = sc.nextLine();
                    presenter.printAsk("speaker's username");
                    String speakerUsername = sc.nextLine();
                    presenter.printAsk("speaker's account password");
                    String speakerPW = sc.nextLine();
                    if (userM.registerUser(UserType.SPEAKER, speakerName, speakerUsername, speakerPW)) {
                        presenter.printSuccess();
                        presenter.printUserInfo(UserType.SPEAKER, speakerUsername, speakerPW);
                        break;
                    } else {
                        presenter.printUsernameExists(); //or invalid input?
                        presenter.printFail();
                    }
                }
            } else if (option.equals("6")) { //create new room
                while (true) {
                    presenter.printAsk("new room's name");
                    String roomName = sc.nextLine();
//                presenter.printAsk("new room's maximum capacity");
//                String capacity = sc.nextLine();
                    if (roomM.addRoom(roomName, 2)) {
                        presenter.printSuccess();
                        //return to menu
                        break;
                    } else {
                        presenter.printFail();
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
            presenter.printAsk("message");
            String content = sc.nextLine();
            List<String> userList = userM.getUsernameList();
            for (String user : userList) {
                if (choice.equals("4") && userM.getUserType(user) == UserType.SPEAKER) {
                    messageM.sendMessage(username, user, content);
                } else if (userM.getUserType(user) == UserType.ORGANIZER) {
                    messageM.sendMessage(username, user, content);
                }
            }
        }
    }
}
