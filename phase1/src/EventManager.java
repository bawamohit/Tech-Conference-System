import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private List<Event> events;

    /**
     * The constructor takes events and assigns the variable an appropriate value.
     *
     *
     */

    public EventManager(){ this.events = new ArrayList<>(); }

    /**
     * Implements Getter, getEvents, for events.
     *
     * @return list of all scheduled events
     */
    public List<Event> getEvents(){ return events; }

    /**
     * Implements modifier, addEvent, for events.
     *
     * @return a boolean indicating if event was successfully added
     */
    public boolean addEvent(Event event){
        LocalDateTime start = event.getStartTime();
        for(Event e : events){
            if (e.getStartTime() == start){
                return false;
            }
        }
        if (start.getHour() >= 9 && start.getHour() <= 17){
            events.add(event);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Implements modifier, removeEvent, for events.
     *
     * @return a boolean indicating if event was successfully removed
     */
    // public boolean removeEvent(Event event){

    // }

    /**
     * Implements helper method, findEvent, to find event object when given its name.
     *
     * @return an Event object in list of events associated with the given String eventName.
     */
    public Event findEvent(String eventName){
        for (Event e: events){
            if (e.getEventName().equals(eventName)){
                return e;
            }else{
                return null; // can we make return type optional?
            }
        }
        return null;
    }

    /**
     * Implements modifier, addAttendee, for event in events.
     *
     * @return a boolean indicating if user was successfully added
     */
    public boolean addAttendee(User user, Event event){
        LocalDateTime s_event = event.getStartTime();
        if (event.getAttendees().contains(user.getName())){
            return false;
        }
        for(String name : user.getEventsAttending()){
            Event users_event = findEvent(name);
            LocalDateTime s_user = users_event.getStartTime();
            if (s_user.getHour() == s_event.getHour()){
                return false;
            }
        }
        event.addAttendee(user); // add user to event's guest list
        return true;
    }

    /**
     * Implements modifier, removeAttendee, for event in events.
     *
     * @return a boolean indicating if user was successfully removed
     */
    public boolean removeAttendee(User user, Event event){
        if (!(event.getAttendees().contains(user.getName())) ||
                !(user.getEventsAttending().contains(event.getEventName()))){
            return false;
        }
        event.removeAttendee(user); // remove user from event's guest list
        return true;
    }

}
