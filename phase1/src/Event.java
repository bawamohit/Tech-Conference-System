import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private String eventName;
    private User speaker;
    private User organizer;
    private List<User> attendees;
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
        attendees = new ArrayList<>();
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
     * Implements Getter, getAttendees, for attendees.
     *
     * @return attendees of this event
     */
    public List<User> getAttendees(){
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
     * Adds new user to attendees.
     *
     * @param attendee new attendee of event
     */
    public void addAttendee(User attendee){
        attendees.add(attendee);
    }

    /**
     * Removes user from attendees.
     *
     * @param attendee attendee that is getting removed from event
     */
    public void removeAttendee(User attendee){
        attendees.remove(attendee);
    }
}
