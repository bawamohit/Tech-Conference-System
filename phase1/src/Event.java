import java.time.LocalDateTime;
import java.util.List;

public class Event {
    private String speaker;  //placeholder. replace with User class
    private String organizer; //placeholder. replace with User class
    private List<String> guests; //placeholder. replace with User class
    private String room;
    private String eventName;
    private LocalDateTime startTime;

    //to avoid long parameter lists, used List (containing speaker, organizer, guests, room, eventName)
    // as parameter for this constructor. will need to create this list in the UI class.

    /**
     * The constructor takes in a List of Objects containing the information regarding this Event. It assigns
     * variable speaker, organizer, guests, room, eventName, startTime.
     *
     * @param eventInfo list of Objects containing the information regarding this Message
     */
    public Event(List<Object> eventInfo){
        eventName = (String) eventInfo.get(0);
        organizer = (String) eventInfo.get(1); //replace String with User class
        speaker = (String) eventInfo.get(2); //replace String with User class
        room = (String) eventInfo.get(3);
        guests = (List<String>) eventInfo.get(4); // still figuring out how to get this cast right.
        startTime = (LocalDateTime) eventInfo.get(5);
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
    public String getOrganizer(){ //replace String with User class
        return organizer;
    }

    /**
     * Implements Getter, getSender, for sender.
     *
     * @return speaker of event
     */
    public String getSpeaker(){ //replace String with User class
        return speaker;
    }

    /**
     * Implements Getter, getRoom, for room.
     *
     * @return room for event
     */
    public String getRoom(){ //replace String with User class
        return room;
    }

    /**
     * Implements Getter, getGuests, for guests.
     *
     * @return guests of this event
     */
    public List<String> getGuests(){ //replace String with User class
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
