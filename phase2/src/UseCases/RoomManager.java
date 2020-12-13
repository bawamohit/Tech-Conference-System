package UseCases;

import java.time.LocalDateTime;
import java.util.*;
import Entities.Room;

public class RoomManager {
    private HashMap<String, Room> rooms;

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
     */
    public void addRoom(String roomName, int capacity) {
        Room newRoom = new Room(roomName, capacity);
        rooms.put(roomName, newRoom);
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
    public boolean cannotAddEvent(String roomName, LocalDateTime newST, LocalDateTime newET) {
        HashMap<UUID, List<LocalDateTime>> schedule = getRoomSchedule(roomName);
        for (UUID event: schedule.keySet()){
            List<LocalDateTime> timeSlot= schedule.get(event);
            if (newET.isAfter(timeSlot.get(0)) && newST.isBefore(timeSlot.get(1))){
                return true;
            }
        }
        return false;
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
    public boolean cannotSetCapacity(String roomName, int newCapacity, int numOtherPpl) {
        int capacity = getRoomCapacity(roomName);
        return newCapacity > capacity - numOtherPpl;
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
    //TODO replace this method with methods below if it does what i think it does
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

    public void removeEventFromRoom(String roomName, UUID eventID){
        Room room = rooms.get(roomName);
        HashMap<UUID, List<LocalDateTime>> schedule = room.getSchedule();
        schedule.remove(eventID);
        room.setRoomSchedule(schedule);
    }

    public boolean doesNotContainEvent(String room, UUID eventID){
        return !rooms.get(room).getRoomEventIDs().contains(eventID);
    }

    /**
     * Returns if a room has enough space to hold the given event's capacity
     *
     * @return a boolean indicating if the room has space for the event's capacity
     */
    public boolean hasInsufficientSpace(String roomName, int capacity){
        if (roomExists(roomName)){
            Room room = rooms.get(roomName);
            return room.getCapacity() < capacity;
        }
        return true;
    }
}
