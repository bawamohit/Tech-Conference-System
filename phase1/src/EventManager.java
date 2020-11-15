import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class EventManager implements Serializable {
    private HashMap<UUID, Event> events;

    /**
     * The constructor takes events and assigns the variable an appropriate value.
     *
     *
     */
    public EventManager(){
        this.events = new HashMap<>();
    }

    /**
     * Implements Getter, getEvents, for event IDs.
     *
     * @return event IDs for all scheduled events
     */

    public List<UUID> getEvents() {
        Collection<UUID> eventc = events.keySet();
        ArrayList<UUID> eventList = new ArrayList<>(eventc);
        return eventList;
    }

    /**
     * Implements Getter, getEventsInfo, for a list of events.
     *
     * @return a Map of Event Name to its time and location
     */

    public LinkedHashMap<String, HashMap<LocalDateTime, String>> getEventsInfo(List<UUID> eventIDs){
        LinkedHashMap<String, HashMap<LocalDateTime, String>> eventsInfo = new LinkedHashMap<>();
        for (UUID id: eventIDs){
            Event e = events.get(id);
            HashMap<LocalDateTime, String> timeToPlace = new HashMap<>();
            timeToPlace.put(e.getStartTime(), e.getEventRoomName());
            eventsInfo.put(e.getEventName(), timeToPlace);
        }
        return eventsInfo;
    }

    /**
     * Implements Getter, getAvailableEvents, for IDs of available events.
     *
     * @return event IDs for all events still open for signup
     */

    public List<UUID> getAvailableEvents() {
        ArrayList<UUID> availableEvents = new ArrayList<>();
        for (UUID id: events.keySet()){
            Event e = events.get(id);
            if (e.getCanSignUp()){
                availableEvents.add(id);
            }
        }
        return availableEvents;
    }

    /**
     * Implements helper method, findEvent, to find event object when given its name.
     *
     * @return an Event object in hashmap of events associated with the given String eventName.
     */
    private boolean canAddAttendee(UUID eventID){
        if (events.get(eventID).isFull()){
            return false;
        }
        return true;
    }

    /**
     * Implements modifier, addEvent, for events.
     *
     * @return a boolean indicating if event was successfully added
     */
    public boolean addEvent(String eventName, String speaker, String organizer, LocalDateTime startTime,
                            String roomName, int max_capacity){
        Event new_event = new Event(eventName, speaker, organizer, startTime, roomName, max_capacity);
        for(UUID id : events.keySet()){
            Event e = events.get(id);
            if (e.getStartTime() == startTime && e.getEventRoomName().equals(roomName)){
                return false;
            }
        }
        if (startTime.getHour() >= 9 && startTime.getHour() <= 16){
            events.put(new_event.getId(), new_event);
            return true;
        }else{
            return false;
        }
    }

    ///**
     //* Implements modifier, removeEvent, for events.
    // *
   //  * @return a boolean indicating if event was successfully removed
  //   */
//    public boolean removeEvent(Event event){
//        if (events.containsKey(event.getId())){
//            events.remove(event.getId());
//            update canSignUp as necessary
//            return true;
//        }
//        return false;
//    } // for phase 2

//    /**
//     * Implements helper method, findEvent, to find event object when given its name.
//     *
//     * @return an Event object in hashmap of events associated with the given String eventName.
//     */
//    private Event findEvent(String eventName){
//        for (Event e: events.values()){
//            if (e.getEventName().equals(eventName)){
//                return e;
//            }
//        }
//        return null;
//    }

    /**
     * Implements modifier, addAttendee, for event in events.
     *
     * @return a boolean indicating if user was successfully added
     */
    public boolean addAttendee(String username, UUID eventID){
        Event event = events.get(eventID);
//        LocalDateTime s_event = event.getStartTime();
        if (event.getAttendees().contains(username)){
            return false;
        }
        //check availability of event somehow
//        RoomManager rm = new RoomManager();
//        if (!rm.hasSpace(event.getEventRoomName(), (event.getAttendees().size()))){
//            event.setCanSignUp(false); //not supposed to call rm in em?
//            return false;
//        }else{
//            event.setCanSignUp(true);
//            List<String> updated_event = event.getAttendees();
//            updated_event.add(username);
//            event.setAttendees(updated_event);
//            return true;
//        }
    return true;
    }

    /**
     * Implements modifier, removeAttendee, for event in events.
     *
     * @return a boolean indicating if user was successfully removed
     */
    public boolean removeAttendee(String username, UUID eventID){
        Event event = events.get(eventID);
        List<String> updated_event = event.getAttendees();
        if (updated_event.remove(username)){
            event.setAttendees(updated_event);
            event.setCanSignUp(true);
            return true;
        }
        return false;
    }


}
