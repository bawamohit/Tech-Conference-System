package Controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import Entities.UserType;
import UI.OrganizerPresenter;

public class OrganizerSystem extends UserSystem {

    private OrganizerPresenter presenter;

    public OrganizerSystem(){
        this.presenter = new OrganizerPresenter();
    }

    public void run(String username, TechConferenceSystem tcs) {
        Scanner scanner = new Scanner(System.in);
        label:
        while (true) {
            presenter.printOrganizerMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "0":
                    presenter.printLoggedOut();
                    break label;
                case "1":
                    presenter.printOrganizerMessageMenu();
                    choice = validInput("^[012345]$", scanner, tcs);
                    if(!choice.equals("0")) {
                        organizerHelperMessageSystem(username, choice, scanner, tcs);
                    }
                    break;
                case "2":
                    if (addEvent(username, scanner, tcs)) {
                        presenter.printEventCreationSuccess();
                        presenter.printSuccess();
                        break;
                    }
                    else {
                        presenter.printEventCreationFail();
                    }
                    break;
                case "3":  //reschedule event
                    presenter.printUnderConstruction();
                    break;
                case "4":  //remove Event
                    presenter.printUnderConstruction();
                    break;
                case "5":  //create speaker
                    tcs.signUp(scanner, UserType.SPEAKER);
                    presenter.printSuccess();
                    break;
                case "6":  //create new room
                    presenter.printAsk("new room's name");
                    presenter.printBackToMainMenu();
                    String roomName = scanner.nextLine();
                    if(roomName.equals("")) break;
                    if (tcs.getRM().addRoom(roomName, 2)) {
                        presenter.printSuccess();
                    } else { presenter.printObjectExists("Room"); }
                    break;
                default:
                    presenter.printInvalidInput();
                    break;
            }
        }
    }

    // Helper method, implements the general message system, and add Organizer-specific choices
    private void organizerHelperMessageSystem(String username, String choice, Scanner sc, TechConferenceSystem tcs) {
        if (choice.matches("[0123]]")) {
            super.helperMessageSystem(username, choice, sc, tcs);
        } else {
            messageAll(username, choice, sc, tcs);
        }
    }

    // Helper method, implements the additional Organizer-specific messaging choices
    private void messageAll(String username, String choice, Scanner scanner, TechConferenceSystem tcs) {
        presenter.printAsk("message");
        String content = scanner.nextLine();
        if (choice.equals("4")) {
            List<String> speakers = tcs.getUM().getSpeakerList();
            tcs.getMM().messageAll(username, speakers, content);
        } else {
            List<String> attendees = tcs.getUM().getAttendeeList();
            tcs.getMM().messageAll(username, attendees, content);
        }
        presenter.printSuccess();
    }

    private boolean addEvent(String username, Scanner sc, TechConferenceSystem tcs) {
        presenter.printAsk("event's name");
        String eventName = sc.nextLine();
        presenter.printAsk("event's start time (enter in the format HH:MM of a time between 9-16)");
        String[] time = sc.nextLine().split(":");
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);
        LocalDateTime startTime = LocalDateTime.of(2020, 6, 9, hour, minute, 0); //catch exception
        if (!isTimeOk(startTime)){
            presenter.printInvalidInput();
            return false;
        }
        presenter.printAsk("event speaker's name");
        String speaker = sc.nextLine();
        if (!isSpeakerOk(speaker, startTime, tcs)){ return false; }
        presenter.printAsk("event's room name (enter room name)");
        String roomName = sc.nextLine();
        if (!isRoomOk(roomName, startTime, tcs)){ return false; }
        int capacity = tcs.getRM().getRoomCapacity(roomName);
        UUID id = tcs.getEM().addEvent(eventName, speaker, username, startTime, roomName, (capacity - 1));
        if (id != null) {
            if (tcs.getRM().addEventToSchedule(id, roomName, startTime) && tcs.getUM().addEventAttending(speaker, id)) {
                return true;
            } else{
                presenter.printUnprocessed();
                return false;
            }
        }
        presenter.printFail();
        return false;
    }

    private boolean isSpeakerOk(String speaker, LocalDateTime newTime, TechConferenceSystem tcs){
        if(!tcs.getUM().getUsernameList().contains(speaker)){
            presenter.printDNE(speaker);
            return false;
        }
        List<UUID> speakers_events = tcs.getUM().getEventsAttending(speaker);
        for (UUID id: speakers_events){
            LocalDateTime existingTime = tcs.getEM().getEventStartTime(id);
            if (!tcs.getEM().scheduleNotOverlap(existingTime, newTime)){
                presenter.printObjUnavailable("speaker");
                return false;
            }
        }
        return true;
    }

    private boolean isRoomOk(String roomName, LocalDateTime startTime, TechConferenceSystem tcs){
        if (!tcs.getRM().getRooms().contains(roomName)) {
            presenter.printDNE("room");
            return false;
        }
        if (!tcs.getRM().canAddEvent(roomName, startTime)){
            presenter.printObjUnavailable("room at this time");
            return false;
        }
        return true;
    }

    private boolean isTimeOk(LocalDateTime startTime){
        int hour = startTime.getHour();
        int minute = startTime.getMinute();
        if (hour < 9 || hour > 16){return false;}
        else if (hour == 16) {
            return minute <= 0;
        }
        return true;
    }
}
