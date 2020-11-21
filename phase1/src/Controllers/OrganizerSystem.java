package Controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import Entities.UserType;

public class OrganizerSystem extends UserSystem {

    public void run(String username, TechConferenceSystem tcs) {
        Scanner scanner = new Scanner(System.in);
        label:
        while (true) {
            tcs.getPresenter().printOrganizerMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "0":
                    tcs.getPresenter().printLoggedOut();
                    break label;
                case "1":
                    tcs.getPresenter().printOrganizerMessageMenu();
                    choice = validInput("^[012345]$", scanner, tcs);
                    if(!choice.equals("0")) {
                        super.helperMessageSystem(username, choice, scanner, tcs);
                        organizerHelperMessageSystem(username, choice, scanner, tcs);
                    }
                    break;
                case "2":
                    if (addEvent(username, scanner, tcs)) {
                        tcs.getPresenter().printEventCreationSuccess();
                        tcs.getPresenter().printSuccess();
                        break;
                    }
                    else {
                        tcs.getPresenter().printEventCreationFail();
                    }
                    break;
                case "3":  //reschedule event
                    tcs.getPresenter().printUnderConstruction();
                    break;
                case "4":  //remove Event
                    tcs.getPresenter().printUnderConstruction();
                    break;
                case "5":  //create speaker
                    while (true) {
                        if (addSpeaker(scanner, tcs)) {
                            tcs.getPresenter().printSuccess();
                            break;
                        }
                    } break;
                case "6":  //create new room
                    while (true) {
                        tcs.getPresenter().printAsk("new room's name");
                        String roomName = scanner.nextLine();
        //                presenter.printAsk("new room's maximum capacity");
        //                String capacity = scanner.nextLine();
                        if (tcs.getRM().addRoom(roomName, 2)) {
                            tcs.getPresenter().printSuccess();
                            break;
                        } else { tcs.getPresenter().printObjectExists("Room"); }
                    }
                    break;
                default:
                    tcs.getPresenter().printInvalidInput();
                    break;
            }
        }
    }

    // Helper method, implements the general message system, and add Organizer-specific choices
    private void organizerHelperMessageSystem(String username, String choice, Scanner sc, TechConferenceSystem tcs) {
        super.helperMessageSystem(username, choice, sc, tcs);
        messageAll(username, choice, sc, tcs);
    }

    // Helper method, implements the additional Organizer-specific messaging choices
    private void messageAll(String username, String choice, Scanner scanner, TechConferenceSystem tcs) {
        if (choice.equals("4") || choice.equals("5")) {
            tcs.getPresenter().printAsk("message");
            String content = scanner.nextLine();
            List<String> userList = tcs.getUM().getUsernameList();
            for (String user : userList) {
                if (choice.equals("4") && tcs.getUM().getUserType(user) == UserType.SPEAKER) {
                    tcs.getMM().sendMessage(username, user, content);
                } else if (choice.equals("5") && tcs.getUM().getUserType(user) == UserType.ORGANIZER) {
                    tcs.getMM().sendMessage(username, user, content);
                }
            }
        }
    }

    private boolean addSpeaker(Scanner sc, TechConferenceSystem tcs){
        tcs.getPresenter().printAsk("speaker's name");
        String speakerName = sc.nextLine();
        tcs.getPresenter().printAsk("speaker's username");
        String speakerUsername = sc.nextLine();
        tcs.getPresenter().printAsk("speaker's account password");
        String speakerPW = sc.nextLine();
        if (tcs.getUM().registerUser(UserType.SPEAKER, speakerName, speakerUsername, speakerPW)) {
            tcs.getPresenter().printUserInfo(UserType.SPEAKER, speakerUsername, speakerPW);
            return true;
        } else {
            tcs.getPresenter().printObjectExists("Username"); //or invalid input?
            tcs.getPresenter().printFail();
            return false;
        }
    }

    private boolean addEvent(String username, Scanner sc, TechConferenceSystem tcs) {
        tcs.getPresenter().printAsk("event's name");
        String eventName = sc.nextLine();
        tcs.getPresenter().printAsk("event's start time (enter in the format H:MM of a time between 9-16)");
        String[] time = sc.nextLine().split(":");
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);
        LocalDateTime startTime = LocalDateTime.of(2020, 6, 9, hour, minute, 0); //catch exception
        if (!isTimeOk(startTime, tcs)){
            tcs.getPresenter().printInvalidInput();
            return false;
        }
        tcs.getPresenter().printAsk("event speaker's name");
        String speaker = sc.nextLine();
        if (!isSpeakerOk(speaker, startTime, tcs)){ return false; }
        tcs.getPresenter().printAsk("event's room name (enter room name)");
        String roomName = sc.nextLine();
        if (!isRoomOk(roomName, startTime, tcs)){ return false; }
        int capacity = tcs.getRM().getRoomCapacity(roomName);
        UUID id = tcs.getEM().addEvent(eventName, speaker, username, startTime, roomName, (capacity - 1));
        if (id != null) {
            if (tcs.getRM().addEventToSchedule(id, roomName, startTime) && tcs.getUM().addEventAttending(speaker, id)) {
                return true;
            } else{
                tcs.getPresenter().printUnprocessed();
                return false;
            }
        }
        tcs.getPresenter().printFail();
        return false;
    }

    private boolean isSpeakerOk(String speaker, LocalDateTime newTime, TechConferenceSystem tcs){
        if(!tcs.getUM().getUsernameList().contains(speaker)){
            tcs.getPresenter().printDNE(speaker);
            return false;
        }
        List<UUID> speakers_events = tcs.getUM().getEventsAttending(speaker);
        for (UUID id: speakers_events){
            LocalDateTime existingTime = tcs.getEM().getEventStartTime(id);
            if (!tcs.getUM().scheduleNotOverlap(existingTime, newTime)){
                tcs.getPresenter().printObjUnavailable("speaker");
                return false;
            }
        }
        return true;
    }

    private boolean isRoomOk(String roomName, LocalDateTime startTime, TechConferenceSystem tcs){
        if (!tcs.getRM().getRooms().contains(roomName)) {
            tcs.getPresenter().printDNE("room");
            return false;
        }
        if (!tcs.getRM().canAddEvent(roomName, startTime)){
            tcs.getPresenter().printObjUnavailable("room at this time");
            return false;
        }
        return true;
    }

    private boolean isTimeOk(LocalDateTime startTime, TechConferenceSystem tcs){
        int hour = startTime.getHour();
        int minute = startTime.getMinute();
        if (hour < 9 || hour > 16){ return false; }
        if (hour == 16 && minute > 0){ return false; }
        return true;
    }
}
