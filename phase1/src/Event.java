import java.time.LocalDateTime;
import java.util.List;

public class Event {
    private String eventName;
    private User speaker;
    private User organizer;
    private List<User> guests;
    private String room;
    private LocalDateTime startTime;

    /**
     * The constructor takes eventName, speaker, organizer, room, startTime of event and assigns each variable.
     *
     * @param eventName name of event
     * @param speaker speaker of event
     * @param organizer organizer of event
     * @param room room for event
     * @param startTime date and time of event
     */

    public Event(String eventName, User speaker, User organizer, String room, LocalDateTime startTime){
        this.eventName = eventName;
        this.speaker = speaker;
        this.organizer = organizer;
        this.room = room;
        this.startTime = startTime;
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
     * @return organizer for event
     */
    public User getOrganizer(){
        return organizer;
    }

    /**
     * Implements Getter, getSender, for sender.
     *
     * @return speaker of event
     */
    public User getSpeaker(){
        return speaker;
    }

    /**
     * Implements Getter, getRoom, for room.
     *
     * @return room for event
     */
    public String getRoom(){
        return room;
    }

    /**
     * Implements Getter, getGuests, for guests.
     *
     * @return guests of this event
     */
    public List<User> getGuests(){
        return guests;
    }

    /**
     * Implements Getter, getStartTime, for StartTime.
     *
     * @return date and time of this event.
     */
    public LocalDateTime getStartTime(){
        return startTime;
    }

}
