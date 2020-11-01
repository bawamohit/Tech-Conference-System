import java.time.LocalDateTime;
import java.util.List;

public class Event {
    private String eventName;
    private String speaker;  //placeholder. replace with User class
    private String organizer; //placeholder. replace with User class
    private List<String> guests; //placeholder. replace with User class
    private String room;
    private LocalDateTime startTime;

    //to avoid long parameter lists, used Builder method to instantiate this class.
    //Will need to call the builder method in the UI class instead of the Event constructor.

    /**
     * Private constructor will be used in EventBuilder to instantiate this class.
     *
     */
    private Event(){}

    public static class EventBuilder {
        private String eventName;
        private String speaker;
        private String organizer;
        private List<String> guests;
        private String room;
        private LocalDateTime startTime;

        public EventBuilder setEventNameRoomTime(String eventName, String room, LocalDateTime startTime) {
            this.eventName = eventName;
            this.room = room;
            this.startTime = startTime;
            return this;
        }

        public EventBuilder setSpeaker(String speaker) {
            this.speaker = speaker;
            return this;
        }

        public EventBuilder setOrganizer(String organizer) {
            this.organizer = organizer;
            return this;
        }

        public EventBuilder setGuests(List<String> guests) {
            this.guests = guests;
            return this;
        }

        public Event build() {
            Event event = new Event();
            event.eventName = this.eventName;
            event.speaker = this.speaker;
            event.organizer = this.organizer;
            event.guests = this.guests;
            event.room = this.room;
            event.startTime = this.startTime;
            return event;
        }
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
