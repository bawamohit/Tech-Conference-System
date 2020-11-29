package UseCases;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import Entities.Room;

public class RoomManager implements Serializable {
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
    public HashMap<LocalDateTime, UUID> getRoomSchedule(String roomName) {
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
        if (rooms.containsKey(roomName)) {
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
     * @param newTime start time of potential event to be added
     *
     * @return a boolean indicating if an event with room name roomName and start time start can be successfully added
     */
    public boolean canAddEvent(String roomName, LocalDateTime newTime) {
        Room room = rooms.get(roomName);
        for (LocalDateTime time : room.getSchedule().keySet()) {
            if (newTime.isAfter(time.minusHours(1)) && newTime.isBefore(time.plusHours(1))) {
                    return false;
            }
        }
        return true;
    }
    /**
     * Implements modifier, addEventToSchedule, for event in a room.
     *
     * @param eventId id of the event to be added to a room's schedule
     * @param roomName name of room to modify schedule for
     * @param start start time of event to be added
     */
    public void addEventToSchedule(UUID eventId, String roomName, LocalDateTime start) {
        Room room = rooms.get(roomName);
        HashMap<LocalDateTime, UUID> updated_room = room.getSchedule();
        updated_room.put(start, eventId);
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
            HashMap<LocalDateTime, UUID> schedule = r.getSchedule();
            for (LocalDateTime time : schedule.keySet()) {
                if (schedule.get(time).equals(eventID)) {
                    schedule.remove(time);
                    r.setRoomSchedule(schedule);
                    return true;
                }
            }
        }
        return false;
    }

}
