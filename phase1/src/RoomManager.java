import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class RoomManager {
    private HashMap<String, Room> rooms;
    // what we need:
    // attributes: all the room name and their Room object,
    // methods: add room, remove room?, add event to room schedule, remove event from room schedule, create room,
    // check room capacity(or does this go in Room?),
    /**
     * The constructor takes rooms and assigns the variable an appropriate value.
     *
     *
     */

    public RoomManager(){ this.rooms = new HashMap<>(); }

    /**
     * Implements Getter, getRooms, for rooms.
     *
     * @return hashmap of all created rooms
     */
    public HashMap<String, Room> getRooms(){ return rooms; }

    /**
     * Implements creator, createRoom, to instantiate a Room object.
     *
     * @return a Room object with assigned attributes as specified by the parameters.
     */
    public Room createRoom(String roomName, int capacity){
        return new Room(roomName, capacity);
    }

    /**
     * Implements modifier, addRoom, for rooms.
     *
     * @return a boolean indicating if room was successfully added.
     */
    public boolean addRoom(String roomName, int capacity) {
        if (rooms.containsKey(roomName)){
            return false;
        }else{
            Room new_room = createRoom(roomName, capacity);
            rooms.put(roomName, new_room);
            return true;
        }
    }

    /**
     * Implements helper method, findRoom, to find Room object when given its name.
     *
     * @return a Room object in hashmap of rooms associated with the given String roomName.
     */
    public Room findRoom(String roomName){
        for (String name: rooms.keySet()){
            Room r = rooms.get(name);
            if (r.getRoomName().equals(roomName)){
                return r;
            }
        }
        return null;
    }

    /**
     * Implements modifier, removeEventFromRoom, for event in a room.
     *
     * @return a boolean indicating if event was successfully removed
     */
    public boolean removeEventFromRoom(UUID eventID, String roomName){
        // should we specify room or let caller remove event directly without knowing which room?
        Room room = findRoom(roomName);
        if (!(room.getSchedule().containsValue(eventID))){
            return false;
        }
        LocalDateTime = time; // can we assume there's a one-to-one relationship between event and time? (i.e.,
        // can events occur at multiple time of the day in the same room or na)
        HashMap<LocalDateTime, UUID> updated_room = room.getSchedule();
        if (updated_room.remove(time, eventID)){
            room.setRoomSchedule(updated_room);
            // update room capacity?
            return true;
        }
        return false;
    }

    /**
     * Implements modifier, addEventToRoom, for event in a room.
     *
     * @return a boolean indicating if event was successfully added
     */
    public boolean addEventToRoom(HashMap<UUID, Event> events, UUID eventId, String roomName){
        Room room = findRoom(roomName);
        Event event = events.get(eventId);
        LocalDateTime start = event.getStartTime();
        if (room.getSchedule().containsValue(eventId)){
            return false;
        }
        for (LocalDateTime time: room.getSchedule().keySet()){
            if (time.isAfter(start.minusHours(1)) && time.isBefore(start.plusHours(1))){
                return false;
            }
        }
        HashMap<LocalDateTime, UUID> updated_room = room.getSchedule();
        updated_room.put(start, eventId);
        room.setRoomSchedule(updated_room);
        return true;
    }
}
