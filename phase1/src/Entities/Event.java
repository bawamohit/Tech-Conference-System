package Entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Event implements Serializable {
    private String eventName;
    private String speaker;
    private String organizer;
    private List<String> attendees;
    private LocalDateTime startTime;
    private UUID id;
    private String roomName;
    private int max_capacity;

    /**
     * The constructor takes eventName, speaker, organizer, startTime of event and assigns each variable.
     * It also instantiates the list of attendees, attendees, and sets an unique id for the event.
     *
     * @param eventName name of event
     * @param speaker speaker of event
     * @param organizer organizer of event
     * @param startTime date and time of event
     * @param roomName name of the room
     */
    public Event(String eventName, String speaker, String organizer, LocalDateTime startTime, String roomName,
                 int max_capacity){
        this.eventName = eventName;
        this.speaker = speaker;
        this.organizer = organizer;
        this.startTime = startTime;
        this.roomName = roomName;
        this.max_capacity = max_capacity;
        attendees = new ArrayList<>();
        id = UUID.randomUUID();
    }

    /**
     * Implements the default toString method and return a String with the following format:
     * "The event <eventName> will occur at <startTime> in <roomName>.
     *
     * @return A string representation of the event.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = startTime.format(formatter);
        return eventName + ", " + formattedTime + ", " + roomName;
    }

    /**
     * Implements Getter, getEventName, for eventName.
     *
     * @return name of event
     */
    public String getEventName(){
        return eventName;
    }

    /**
     * Implements Getter, getOrganizer, for organizer.
     *
     * @return username of event's organizer
     */
    public String getOrganizer(){ //phase 2
        return organizer;
    }

    /**
     * Implements Getter, getSender, for sender.
     *
     * @return username of speaker of event
     */
    public String getSpeaker(){ //phase 2
        return speaker;
    }

    /**
     * Implements Getter, getAttendees, for attendees.
     *
     * @return attendees of event
     */
    public List<String> getAttendees(){
        return attendees;
    }

    /**
     * Implements Getter, getStartTime, for StartTime.
     *
     * @return date and time of this event.
     */
    public LocalDateTime getStartTime(){
        return startTime;
    }

    /**
     * Implements Getter, getEventRoomName, for roomName.
     *
     * @return name of event's room
     */
    public String getEventRoomName(){
        return roomName;
    }

    /**
     * Implements Getter, getMaxCapacity, for max_capacity.
     *
     * @return the event's maximum capacity
     */
    public int getMaxCapacity(){
        return max_capacity;
    }

    /**
     * Implements Getter, getId, for id.
     *
     * @return id of event.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Implements Setter, setAttendees, for attendees.
     *
     * @param attendees new attendees of event
     */
    public void setAttendees(List<String> attendees){
        this.attendees = attendees;
    }



}
