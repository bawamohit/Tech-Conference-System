package UseCases;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import Entities.Room;

public class RoomManager {
    private HashMap<String, Room> rooms;
    // what we need:
    // attributes: all the room name and their Room object,
    // methods: add room, remove room?, add event to room schedule, remove event from room schedule, create room,
    // check room capacity(or does this go in Room?),

    /**
     * The constructor takes rooms and assigns the variable an appropriate value.
     */
    public RoomManager() {
        this.rooms = new HashMap<>();
    }

    /**
     * Implements Getter, getRooms, for room names.
     *
     * @return List of all room names
     */
    public List<String> getRooms() {
        Collection<String> roomC = rooms.keySet();
        return new ArrayList<>(roomC);
    }

    /**
     * Implements getter, getRoomCapacity, for room in rooms.
     *
     * @param roomName name of room to retrieve capacity for
     *
     * @return the room's capacity
     */
    public int getRoomCapacity(String roomName) {
        return rooms.get(roomName).getCapacity();
    }

    /**
     * Implements getter, getRoomCapacity, for room in rooms.
     *
     * @param roomName name of room to retrieve schedule for
     *
     * @return the room's schedule
     */
    public HashMap<UUID, List<LocalDateTime>> getRoomSchedule(String roomName) {
        return rooms.get(roomName).getSchedule();
    }

    /**
     * Implements modifier, addRoom, for rooms.
     *
     * @param roomName name of room to be added
     * @param capacity maximum capacity of this new room
     *
     * @return a boolean indicating if room was successfully added
     */
    public boolean addRoom(String roomName, int capacity) {
        if (roomExists(roomName)) {
            return false;
        } else {
            Room new_room = new Room(roomName, capacity);
            rooms.put(roomName, new_room);
            return true;
        }
    }

    /**
     * Implements checker, canAddEvent, for a room in rooms.
     *
     * @param roomName name of room to add an event to
     * @param newST start time of potential event to be added
     * @param newET end time of potential event to be added
     *
     * @return a boolean indicating if an event with room name roomName, start time newST, end time newET
     * can be successfully added
     */
    public boolean canAddEvent(String roomName, LocalDateTime newST, LocalDateTime newET) {
        HashMap<UUID, List<LocalDateTime>> schedule = getRoomSchedule(roomName);
        for (UUID event: schedule.keySet()){
            List<LocalDateTime> timeSlot= schedule.get(event);
            if (newET.isAfter(timeSlot.get(0)) && newST.isBefore(timeSlot.get(1))){
                return false;
            }
        }
        return true;
//        for (LocalDateTime existingST: schedule.keySet()) {
//            if (newET.isAfter(existingST) && newST.isBefore(tcs.getEM().getEventEndTime(schedule.get(existingST)))) {
//                presenter.printObjUnavailable("room at this time");
//                return false;
//            }
//        }
//        return true;
    }

    /**
     * Implements checker, canSetCapacity, for an event in a given room
     *
     * @param roomName name of room to compare new capacity with
     * @param newCapacity new capacity for a certain event occurring in room
     * @param numOtherPpl number of people other than attendees in the room
     *
     * @return a boolean indicating if newCapacity can be set
     */
    public boolean canSetCapacity(String roomName, int newCapacity, int numOtherPpl) {
        int capacity = getRoomCapacity(roomName);
        if (newCapacity > (capacity - numOtherPpl)) return false;
        return true;
    }

    /**
     * Implements checker, roomExists, for roomName
     *
     * @param roomName name of room to check if exists
     *
     * @return a boolean indicating if roomName exists already
     */
    public boolean roomExists(String roomName) {
        return rooms.containsKey(roomName);
    }

    /**
     * Implements modifier, addEventToSchedule, for event in a room.
     *
     * @param eventId id of the event to be added to a room's schedule
     * @param roomName name of room to modify schedule for
     * @param start start time of event to be added
     */
    public void addEventToSchedule(UUID eventId, String roomName, LocalDateTime start, LocalDateTime end) {
        Room room = rooms.get(roomName);
        HashMap<UUID, List<LocalDateTime>> updated_room = room.getSchedule();
        List<LocalDateTime> timeSlot = Arrays.asList(start, end);
        updated_room.put(eventId, timeSlot);
        room.setRoomSchedule(updated_room);
    }

    /**
     * Implements modifier, removeEventFromSchedule, for a scheduled event.
     *
     * @return a boolean indicating if event was successfully removed
     */
    public boolean removeEventFromSchedule(UUID eventID) {
        for (String name : rooms.keySet()) {
            Room r = rooms.get(name);
            HashMap<UUID, List<LocalDateTime>> schedule = r.getSchedule();
            if (r.getRoomEventIDs().contains(eventID)){
                schedule.remove(eventID);
                r.setRoomSchedule(schedule);
                return true;
            }
        }
        return false;
    }

    public boolean hasSpace(String roomName, int eventCapacity){
        if (roomExists(roomName)){
            Room room = rooms.get(roomName);
            return room.getCapacity() >= eventCapacity;
        }
        return false;
    }
}
