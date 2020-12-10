package Controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import Entities.UserType;
import UI.OrganizerPresenter;

/**
 * This class is one of the controllers of this program, specifically for organizers. It is a child class of UserSystem.
 */
public class OrganizerSystem extends UserSystem {

    private final OrganizerPresenter presenter;

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
                case "3":  //change event capacity
                    changeMaxCapacity(scanner, tcs);
                    break;
                case "4":  //remove Event
                    removeEvent(scanner, tcs);
                    break;
                case "5":  //create speaker
                    tcs.signUp(scanner, UserType.SPEAKER);
                    presenter.printSuccess();
                    break;
                case "6":  //create new room
                    createRoom(scanner, tcs);
                    break;
                case "7": //add speaker
                    addSpeakerToEvent(tcs, scanner);
                    break;
                case "8": //create attendee
                    tcs.signUp(scanner, UserType.ATTENDEE);
                    presenter.printSuccess();
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

    private void createRoom (Scanner scanner, TechConferenceSystem tcs){
        presenter.printAsk("new room's name");
        presenter.printBackToMainMenu();
        String roomName = scanner.nextLine();
        if(roomName.equals("")) {
            return;
        }
        presenter.printAsk("new room's maximum capacity");
        presenter.printBackToMainMenu();
        String roomCap = validInput("^[1-9][0-9]*$|^.{0}$", scanner, tcs);
        if(roomCap.equals("")) {
            return;
        }
        if (tcs.getRM().addRoom(roomName, Integer.parseInt(roomCap))) {
            presenter.printSuccess();
        } else {
            presenter.printObjectExists("Room");
        }
    }

    private void changeMaxCapacity(Scanner scanner, TechConferenceSystem tcs){
        List<UUID> openEvents = tcs.getEM().getAvailableEvents(LocalDateTime.now());
        String formattedOutput = formatInfo(tcs.getEM().getEventsStrings(openEvents));
        if (formattedOutput.equals("")) {
            presenter.printNoEventsAvailable("changing capacity");
            return;
        }
        presenter.printAvailableEvents(formattedOutput);
        presenter.printAsk("number of the event to change capacity for");
        presenter.printBackToMainMenu();
        String choice = validInput("^[0-" + (openEvents.size() - 1) + "]$|^.{0}$", scanner ,tcs);
        if(choice.equals("")) {
            return;
        }
        UUID id = openEvents.get(Integer.parseInt(choice));
        presenter.printAsk("new maximum capacity");
        presenter.printBackToMainMenu();
        String maxCap = validInput("^[1-9][0-9]*$|^.{0}$", scanner, tcs);
        if(maxCap.equals("")) {
            return;
        }
        if (!tcs.getRM().canSetCapacity(tcs.getEM().getEventRoomName(id), Integer.parseInt(maxCap),
                tcs.getEM().getEventSpeaker(id).size())){
            presenter.printInvalidInput();
            return;
        }
        tcs.getEM().setMaxCapacity(id, Integer.parseInt(maxCap));
        if (Integer.parseInt(maxCap) < tcs.getEM().getEventAttendees(id).size()){
            for (String username: tcs.getEM().getEventAttendees(id)){
                tcs.getEM().removeAttendee(username, id);
                tcs.getUM().removeEventAttending(username, id);
            }
        }
        presenter.printSuccess();
    }

    private void addEvent(String username, Scanner scanner, TechConferenceSystem tcs) {//TODO update other methods for adding year moth date
        presenter.printAsk("event's name");
        presenter.printBackToMainMenu();
        String eventName = scanner.nextLine();
        if(eventName.equals("")){
            return;
        }
        LocalDateTime startTime = getTime(scanner, tcs, "event's start time (enter in the format YYYY:MM:DD:HH:MM of " +
                "a time between 9-16)");
        if (startTime == null) return;
        LocalDateTime endTime = getTime(scanner, tcs, "event's end time (enter in the format YYYY:MM:DD:HH:MM of " +
                "a time between 9-16)");
        if (endTime == null) return;
        presenter.printAsk("event's room name (enter room name)");
        presenter.printBackToMainMenu();
        String roomName = scanner.nextLine();
        if (roomName.equals("") || !isRoomOk(roomName, startTime, endTime, tcs)) return;
        presenter.printAsk("event's maximum capacity");
        presenter.printBackToMainMenu();
        String maxCap = validInput("^[1-9][0-9]*$|^.{0}$", scanner, tcs);
        if(maxCap.equals("")) {
            return;
        }
        if (!tcs.getRM().canSetCapacity(roomName, Integer.parseInt(maxCap), 0)) {
            presenter.printInvalidInput();
            return;
        }
        UUID id = tcs.getEM().addEvent(eventName, username, startTime, endTime, roomName, Integer.parseInt(maxCap));
        tcs.getUM().addEventAttending(username, id); //TODO organizer
        tcs.getRM().addEventToSchedule(id, roomName, startTime, endTime);
        presenter.printEventActionSuccess("created");
    }

    private LocalDateTime getTime(Scanner scanner, TechConferenceSystem tcs, String s) {
        presenter.printAsk(s);
        presenter.printBackToMainMenu();
        String timeStr = validInput("^([0-9][0-9][0-9][0-9]):(0[1-9]|1[0-2]):([0-2][0-9]|3[0-1]):(09|1[0-6]):" +
                "([0-5][0-9])$|^.{0}$", scanner, tcs);
        if (timeStr.equals("")){
            return null;
        }
        int year2 = Integer.parseInt(timeStr.substring(0, 4));
        int month2 = Integer.parseInt(timeStr.substring(5, 7));
        int day2 = Integer.parseInt(timeStr.substring(8, 10));
        int hour2 = Integer.parseInt(timeStr.substring(11, 13));
        int minute2 = Integer.parseInt(timeStr.substring(14, 16));
        return LocalDateTime.of(year2, month2, day2, hour2, minute2);
    }

    private void removeEvent(Scanner scanner, TechConferenceSystem tcs){
        String choice;
        List<UUID> openEvents = tcs.getEM().getAvailableEvents(LocalDateTime.now());
        List<String> eventInfo = tcs.getEM().getEventsStrings(openEvents);
        presenter.printAvailableEvents(formatInfo(eventInfo));
        presenter.printAskRemove();
        presenter.printBackToMainMenu();
        choice = validInput("^[0-" + (openEvents.size() - 1) + "]$|^.{0}$", scanner ,tcs);
        if(choice.equals("")) {
            return;
        }
        UUID id = openEvents.get(Integer.parseInt(choice));
        if (!tcs.getEM().removeEvent(id)) {
            presenter.printDNE(("the event " + tcs.getEM().getEventName(id)));
            presenter.printEventActionFail("removed");
            return;
        }
        tcs.getRM().removeEventFromSchedule(id);
        for (String username: tcs.getUM().getUsernameList()){
            tcs.getUM().removeEventAttending(username, id);
        }
        presenter.printEventActionSuccess("removed");
    }

    private void addSpeakerToEvent(TechConferenceSystem tcs, Scanner scanner){
        List<UUID> availEvents = tcs.getEM().getAvailableEvents(LocalDateTime.now());
        List<String> eventInfo = tcs.getEM().getEventsStrings(availEvents);
        presenter.printAskSignUp();
        presenter.printAvailableEvents(formatInfo(eventInfo));
        presenter.printBackToMainMenu();
        String choice = validInput("^[0-" + (availEvents.size() - 1) + "]$|^.{0}$", scanner ,tcs);
        if(choice.equals("")) {
            return;
        }
        UUID eventID = availEvents.get(Integer.parseInt(choice));
        LocalDateTime startTime = tcs.getEM().getEventStartTime(eventID);
        LocalDateTime endTime = tcs.getEM().getEventEndTime(eventID);
        presenter.printAsk("speaker's username");
        presenter.printBackToMainMenu();
        String speakerName = validInput(".+", scanner, tcs);
        if(speakerName.equals("")) {
            return;
        }
        if(!(tcs.getUM().getUserType(speakerName) == UserType.SPEAKER)){
            presenter.printNotASpeaker();
        }
        if (!isSpeakerOk(speakerName, startTime, endTime, tcs)){
            presenter.printInvalidInput();
        }
        if (tcs.getEM().getEventMaxCapacity(eventID) ==
                (tcs.getRM().getRoomCapacity(tcs.getEM().getEventRoomName(eventID)) -
                        tcs.getEM().getEventSpeaker(eventID).size())){
            presenter.printEventFull();
            return;
        }
        tcs.getEM().addSpeaker(eventID, speakerName);
        tcs.getUM().addEventAttending(speakerName, eventID);
        presenter.printSuccess();
    }

    private boolean isSpeakerOk(String speaker, LocalDateTime newST, LocalDateTime newET, TechConferenceSystem tcs){
        if(!tcs.getUM().getUsernameList().contains(speaker)){
            presenter.printDNE(speaker);
            return false;
        }
        List<UUID> speakers_events = tcs.getUM().getEventsAttending(speaker);
        for (UUID id: speakers_events){
            LocalDateTime existingST = tcs.getEM().getEventStartTime(id);
            LocalDateTime existingET = tcs.getEM().getEventEndTime(id);
            if (tcs.getEM().scheduleOverlap(existingST, existingET, newST, newET)){
                presenter.printObjUnavailable("speaker");
                return false;
            }
        }
        return true;
    }

    private boolean isRoomOk(String roomName, LocalDateTime newST, LocalDateTime newET, TechConferenceSystem tcs){
        if (!tcs.getRM().roomExists(roomName)) {
            presenter.printDNE("room");
            return false;
        }
        if (!tcs.getRM().canAddEvent(roomName, newST, newET)){
            presenter.printObjUnavailable("room at this time");
            return false;
        }
//        HashMap<LocalDateTime, UUID> schedule = tcs.getRM().getRoomSchedule(roomName);
//        for (LocalDateTime existingST: schedule.keySet()) {
//            if (newET.isAfter(existingST) && newST.isBefore(tcs.getEM().getEventEndTime(schedule.get(existingST)))) {
//                presenter.printObjUnavailable("room at this time");
//                return false;
//            }
//        }
        return true;
    }

}
