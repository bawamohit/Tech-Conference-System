import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.Set; // do we need this (used for keyset())

public class EventManager {
    private HashMap<UUID, Event> events;

    /**
     * The constructor takes events and assigns the variable an appropriate value.
     *
     *
     */

    public EventManager(){ this.events = new HashMap<>(); }

    /**
     * Implements Getter, getEvents, for events.
     *
     * @return list of all scheduled events
     */
    public HashMap<UUID, Event> getEvents(){ return events; }

    /**
     * Implements modifier, addEvent, for events.
     *
     * @return a boolean indicating if event was successfully added
     */
    public boolean addEvent(Event event){
        LocalDateTime start = event.getStartTime();
        for(UUID id : events.keySet()){
            Event e = events.get(id);
            if (e.getStartTime() == start){
                return false;
            }
        }
        if (start.getHour() >= 9 && start.getHour() <= 17){
            events.put(event.getId(), event);
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
        for (UUID id: events.keySet()){
            Event e = events.get(id);
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
        List<String> updated_event = event.getAttendees();
        updated_event.add(user.getName());
        event.setAttendees(updated_event);
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
        List<String> updated_event = event.getAttendees();
        if (updated_event.remove(user.getName())){
            event.setAttendees(updated_event);
            return true;
        }
        return false;
    }

}
