package Entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

public class Room implements Serializable {
    private String roomName;
    private int capacity; //do we need curr_capacity and max_capacity
    private HashMap<LocalDateTime, UUID> schedule;

    /**
     * The constructor takes name and maximum capacity of attendees allowed of a room and assigns each variable.
     * It also instantiates the schedule, a map of event scheduled at a corresponding time.
     *
     * @param roomName name of room
     * @param capacity capacity of attendees allowed in the room
     */

    public Room(String roomName, int capacity){
        this.roomName = roomName;
        this.capacity = capacity;
        schedule = new HashMap<>();
    }

    /**
     * Implements Getter, getRoomName for roomName.
     *
     * @return name of room
     */
    public String getRoomName(){
        return roomName;
    }

    /**
     * Implements Getter, getCapacity, for capacity.
     *
     * @return maximum capacity of attendees allowed in the room
     */
    public int getCapacity(){
        return capacity;
    }

    /**
     * Implements Getter, getSchedule, for schedule.
     *
     * @return schedule for room
     */
    public HashMap<LocalDateTime, UUID> getSchedule(){
        return schedule;
    }

//    /**
//     * Implements Getter, getRoomEvents, for events in this room's schedule.
//     *
//     * @return list of all the events occurring in this room
//     */
//    public List<UUID> getRoomEvents(){ return new ArrayList(schedule.values()); } //constructor with param collection

    /**
     * Implements Setter, setRoomSchedule, for this room.
     *
     * @param schedule updated schedule of room
     */
    public void setRoomSchedule(HashMap<LocalDateTime, UUID> schedule){
        this.schedule = schedule;
    }

}
