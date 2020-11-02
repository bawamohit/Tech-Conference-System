import java.time.LocalDateTime;
import java.util.List;

public class Event {
    private String eventName;
    private User speaker;
    private User organizer;
    private List<User> guests;
    private String room;
    private LocalDateTime startTime;

    //to avoid long parameter lists, used Builder method to instantiate this class.
    //Will need to call the Builder methods in the UI class instead of the Event constructor.

    private Event(){}

    public static class Builder {
        private String eventName;
        private User speaker;
        private User organizer;
        private List<User> guests;
        private String room;
        private LocalDateTime startTime;

        public Builder(){}

        public Builder setEventNameRoomTime(String eventName, String room, LocalDateTime startTime) {
            this.eventName = eventName;
            this.room = room;
            this.startTime = startTime;
            return this;
        }

        public Builder setSpeaker(User speaker) {
            this.speaker = speaker;
            return this;
        }

        public Builder setOrganizer(User organizer) {
            this.organizer = organizer;
            return this;
        }

        public Builder setGuests(List<User> guests) {
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
