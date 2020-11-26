package Controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import Entities.UserType;
import UI.OrganizerPresenter;

/**
 * This class is one of the controllers of this program, specifically for organizers. It is a child class of UserSystem.
 */
public class OrganizerSystem extends UserSystem {

    private OrganizerPresenter presenter;

    public OrganizerSystem(){
        this.presenter = new OrganizerPresenter();
    }

    /**
     * Implements the run method for all organizer users.
     *
     */
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
                    addEvent(username, scanner, tcs);
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
        if (choice.matches("^[0123]$")) {
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

    private void addEvent(String username, Scanner scanner, TechConferenceSystem tcs) {//TODO update other methods for adding year moth date
        presenter.printAsk("event's name");
        presenter.printBackToMainMenu();
        String eventName = scanner.nextLine();
        if(eventName.equals("")) return;
        presenter.printAsk("event's start time (enter in the format YY:MM:DD:HH:MM of a time between 9-16)");
        presenter.printBackToMainMenu();
        String time = validInput("^([0-9][0-9]):(0[1-9]|1[0-2]):([0-2][0-9]|3[0-1]):(09|1[0-6]):([0-5][0-9])$", scanner, tcs);
        if(time.equals("")) return;
        int year = Integer.parseInt(time.substring(0, 2));
        int month = Integer.parseInt(time.substring(3, 5));
        int day = Integer.parseInt(time.substring(6, 8));
        int hour = Integer.parseInt(time.substring(9, 11));
        int minute = Integer.parseInt(time.substring(12,14));
        LocalDateTime startTime = LocalDateTime.of(year, month, day, hour, minute);
        presenter.printAsk("event speaker's name");
        String speaker = scanner.nextLine();
        presenter.printBackToMainMenu();
        if (speaker.equals("") || !isSpeakerOk(speaker, startTime, tcs)) return;//TODO speaker method isSpeaker()
        presenter.printAsk("event's room name (enter room name)");
        presenter.printBackToMainMenu();
        String roomName = scanner.nextLine();
        if (roomName.equals("") || !isRoomOk(roomName, startTime, tcs)) return;
        int capacity = tcs.getRM().getRoomCapacity(roomName);
        UUID id = tcs.getEM().addEvent(eventName, speaker, username, startTime, roomName, (capacity - 1));
        tcs.getUM().addEventAttending(speaker, id);
        tcs.getRM().addEventToSchedule(id, roomName, startTime);
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
}
