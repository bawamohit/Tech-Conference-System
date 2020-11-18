import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

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
        Collection<String> roomc = rooms.keySet();
        return new ArrayList<>(roomc);
    }

    /**
     * Implements creator, createRoom, to instantiate a Room object.
     *
     * @return a Room object with assigned attributes as specified by the parameters
     */
    public Room createRoom(String roomName, int capacity) {
        return new Room(roomName, capacity);
    }

    /**
     * Implements modifier, addRoom, for rooms.
     *
     * @return a boolean indicating if room was successfully added
     */
    public boolean addRoom(String roomName, int capacity) {
        if (rooms.containsKey(roomName)) {
            return false;
        } else {
            Room new_room = createRoom(roomName, capacity);
            rooms.put(roomName, new_room);
            return true;
        }
    }

    /**
     * Implements helper method, findRoom, to find Room object when given its name.
     *
     * @return a Room object in hashmap of rooms associated with the given String roomName
     */
    private Room findRoom(String roomName) {
        if (rooms.containsKey(roomName)) {
            return rooms.get(roomName);
        }
        return null;
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
                    return true; // since same event with diff time will have different ids, we don't need to worry about it
                }
            }
        }
        return false;
    }

    /**
     * Implements modifier, addEventToSchedule, for event in a room.
     *
     * @return a boolean indicating if event was successfully added
     */
    public boolean addEventToSchedule(UUID eventId, String roomName, LocalDateTime start) {
        Room room = findRoom(roomName);
        if (room.getSchedule().containsValue(eventId)) {
            return false;
        }
        for (LocalDateTime time : room.getSchedule().keySet()) {
            if (start.isAfter(time.minusHours(1))) {
                if (start.isBefore(time.plusHours(1))) {
                return false;
                }
            }
        }
        HashMap<LocalDateTime, UUID> updated_room = room.getSchedule();
        updated_room.put(start, eventId);
        room.setRoomSchedule(updated_room);
        return true;
    }
    public int getRoomCapacity(String roomName) {
        return rooms.get(roomName).getCapacity();
    }
}
