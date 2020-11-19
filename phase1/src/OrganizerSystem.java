import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class OrganizerSystem extends UserSystem {
    private RoomManager roomM;

    public OrganizerSystem(Presenter p, UserManager uMan, EventManager eMan, MessageManager mMan, RoomManager rMan) {
        super(p, uMan, eMan, mMan);
        this.roomM = rMan;
    }

    public void run(String username) {
        Scanner sc = new Scanner(System.in);
        label:
        while (true) {
            presenter.printOrganizerMenu();
            String option = sc.nextLine();
            switch (option) {
                case "0":
                    presenter.printLoggedOut();
                    break label;
                case "1":
                    while (true) {
                        presenter.printOrganizerMessageMenu();
                        String messageChoice = sc.nextLine();
                        organizerHelperMessageSystem(username, messageChoice, sc);
                        if (messageChoice.equals("b")) {
                            break;
                        } else if (!messageChoice.matches("^[012345]$")){
                            presenter.printInvalidInput();
                        }
                    }
                    break;
                case "2":
                    if (addEvent(username, sc)) {
                        presenter.printEventCreationSuccess();
                    }
                    break;
                case "3":  //reschedule event
                    presenter.printUnderConstruction();
                    break;
                case "4":  //remove Event
                    presenter.printUnderConstruction();
                    // ask for event id
                    // remove event
                    // print action successful or unsuccessful
                    break;
                case "5":  //create speaker
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
                    break;
                case "6":  //create new room
                    while (true) {
                        presenter.printAsk("new room's name");
                        String roomName = sc.nextLine();
//                presenter.printAsk("new room's maximum capacity");
//                String capacity = sc.nextLine();
                        if (roomM.addRoom(roomName, 2)) {
                            presenter.printSuccess();
                            break;
                        } else {
                            presenter.printFail();
                        }
                    }
                    break;
                default:
                    presenter.printInvalidInput();
                    break;
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
                } else if (choice.equals("5") && userM.getUserType(user) == UserType.ORGANIZER) {
                    messageM.sendMessage(username, user, content);
                }
            }
        }
    }

    private boolean addEvent(String username, Scanner sc) {
        presenter.printAsk("event's name");
        String eventName = sc.nextLine();
        presenter.printAsk("event speaker's name");
        String speaker = sc.nextLine();
        // check if speaker exists
        if(!userM.getUsernameList().contains(speaker)){
            presenter.printDNE(speaker);
            return false;
        }
        presenter.printAsk("event's start time (enter a number from 9-16)");
        int time = Integer.parseInt(sc.nextLine());
        presenter.printAsk("event's room name (enter room name)");
        String roomName = sc.nextLine();
        // check if room exists
        if (!roomM.getRooms().contains(roomName)) {
            presenter.printDNE("room");
            return false;
        }
        LocalDateTime startTime = LocalDateTime.of(2020, 6, 9, time, 0, 0);
        List<UUID> speakers_events = userM.getEventsAttending(speaker);
        // check if speaker is occupied
        for (UUID id: speakers_events){
            LocalDateTime existingTime = eventM.getEventStartTime(id);
            if (!userM.scheduleNotOverlap(existingTime, startTime)){
                presenter.printObjUnavailable("speaker");
                return false;
            }
        }
        int capacity = roomM.getRoomCapacity(roomName);
        //room occupied
        //time is wrong
        // check if event added successfully
        UUID id = eventM.addEvent(eventName, speaker, username, startTime, roomName, capacity);
        if (id != null) {
            if (roomM.addEventToSchedule(id, roomName, startTime)) {
                presenter.printSuccess();
                return true;
            }
        }
        presenter.printFail();
        return false;
    }
}
