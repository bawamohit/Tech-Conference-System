import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.Set;

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
     * Implements creator, createEvent, to instantiate an Event object.
     *
     * @return an Event object with assigned attributes as specified by the parameters.
     */
    public Event createEvent(String eventName, String speaker, String organizer, LocalDateTime startTime){
        return new Event(eventName, speaker, organizer, startTime);
    }
    /**
     * Implements modifier, addEvent, for events.
     *
     * @return a boolean indicating if event was successfully added
     */
    public boolean addEvent(String eventName, String speaker, String organizer, LocalDateTime startTime){
        Event new_event = createEvent(eventName, speaker, organizer, startTime);
        LocalDateTime start = new_event.getStartTime();
        for(UUID id : events.keySet()){
            Event e = events.get(id);
            if (e.getStartTime() == start){
                return false;
            }
        }
        if (start.getHour() >= 9 && start.getHour() <= 17){
            events.put(new_event.getId(), new_event);
            return true;
        }else{
            return false;
        }
        // assign to room specified -> need room manager for that :') not my responsibility
    }

    /**
     * Implements modifier, removeEvent, for events.
     *
     * @return a boolean indicating if event was successfully removed
     */
//    public boolean removeEvent(Event event){
//        if (events.containsKey(event.getId())){
//            events.remove(event.getId());
//            return true;
//        }
//        return false;
//    } // for phase 2

    /**
     * Implements helper method, findEvent, to find event object when given its name.
     *
     * @return an Event object in list of events associated with the given String eventName.
     */
    public Event findEvent(UUID eventID){
        for (UUID id: events.keySet()){
            Event e = events.get(id);
            if (e.getId().equals(eventID)){
                return e;
            }
        }
        return null;
    }

    /**
     * Implements modifier, addAttendee, for event in events.
     *
     * @return a boolean indicating if user was successfully added
     */
    public boolean addAttendee(String username, UUID eventID){
        Event event = events.get(eventID);
        LocalDateTime s_event = event.getStartTime();
        if (event.getAttendees().contains(username)){
            return false;
        }
        // if room is full -> return false -> do in room manager?
//        for(UUID id : user.getEventsAttending()){
//            Event users_event = findEvent(id);
//            LocalDateTime s_user = users_event.getStartTime();
//            if (s_user.getHour() == s_event.getHour()){
//                return false;
//            }
//        } //assume conditions related to user are satisfied
        List<String> updated_event = event.getAttendees();
        updated_event.add(username);
        event.setAttendees(updated_event);
        return true;
    }

    /**
     * Implements modifier, removeAttendee, for event in events.
     *
     * @return a boolean indicating if user was successfully removed
     */
    public boolean removeAttendee(String username, UUID eventID){
        Event event = events.get(eventID);
        if (!(event.getAttendees().contains(username))){
            return false;
        }
        List<String> updated_event = event.getAttendees();
        if (updated_event.remove(username)){
            event.setAttendees(updated_event);
            // update room capacity?
            return true;
        }
        return false;

    }

}
