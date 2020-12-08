package Entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * This class is one of the entity classes for this program, specifically for event.
 *
 */
public class Event implements Comparable<Event> {
    private String eventName;
    private List<String> speakers;
    private String organizer;
    private List<String> attendees;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private UUID id;
    private String roomName;
    private int maxCapacity;

    /**
     * This constructor is for events with no speaker
     * The constructor takes eventName, organizer, startTime of event and assigns each variable.
     * It also instantiates the list of attendees, attendees, and sets an unique id for the event.
     *
     * @param eventName name of event
     * @param organizer organizer of event
     * @param startTime starting date and time of event
     * @param endTime ending date and time of event
     * @param roomName name of the room
     */
    public Event(String eventName, String organizer, LocalDateTime startTime, LocalDateTime endTime, String roomName,
                 int maxCapacity){
        this.eventName = eventName;
        this.speakers = new ArrayList<>();
        this.organizer = organizer;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomName = roomName;
        this.maxCapacity = maxCapacity;
        attendees = new ArrayList<>();
        id = UUID.randomUUID();
    }

    /**
     * Implements the default toString method and return a String with the following format:
     * "The event <eventName> will occur at <startTime> in <roomName>.
     *
     * @return a string representation of the event
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm");
        String formattedST = startTime.format(formatter);
        String formattedET = endTime.format(formatter);
        return eventName + ", " + speakers + ", " + formattedST + "~" + formattedET + ", " + roomName;
    }

    // for phase 2
    /**
     * Implements Getter, getEventName, for eventName.
     *
     * @return name of event
     */
    public String getEventName(){
        return eventName;
    }
    // for phase 2
    /**
     * Implements Getter, getOrganizer, for organizer.
     *
     * @return username of event's organizer
     */
    public String getOrganizer(){
        return organizer;
    }

    /**
     * Converts speaker list to a string format
     *
     * @return all speakers in a string
     */
    public String convertSpeakerString(){
        return String.join(", ", this.speakers);
    }
    /**
     * Implements Getter, getSender, for sender.
     *
     * @return username of speaker of event
     */
    public List<String> getSpeakers(){ //phase 2
        return speakers;
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
     * @return date and time of this event
     */
    public LocalDateTime getStartTime(){
        return startTime;
    }

    /**
     * Implements Getter, getEndTime, for StartTime.
     *
     * @return date and time of this event
     */
    public LocalDateTime getEndTime(){
        return endTime;
    }

    /**
     * Implements Getter, getRoomName, for roomName.
     *
     * @return name of event's room
     */
    public String getRoomName(){
        return roomName;
    }

    /**
     * Implements Getter, getMaxCapacity, for max_capacity.
     *
     * @return the event's maximum capacity
     */
    public int getMaxCapacity(){
        return maxCapacity;
    }

    /**
     * Implements Getter, getId, for id.
     *
     * @return id of event
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

    /**
     * Implements Setter, setId, for event id. (Use for file reading purposes only)
     *
     * @param id ID to be set
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Implements Setter, setMaxCapacity, for maxCapacity.
     *
     * @param newCap new maximum capacity of event
     */
    public void setMaxCapacity(int newCap){ this.maxCapacity = newCap; }

    @Override
    public int compareTo(Event event) {
        if (this.startTime.isBefore(event.startTime)){
            return -1;
        }
        else if (this.startTime.equals(event.startTime)){
            return 0;
        }
        return 1;
    }
}
