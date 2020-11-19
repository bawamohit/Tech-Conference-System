package Use_cases;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import Entities.Event;

/**
 * This class is one of the use cases classes for this program, specifically for manipulating the Event entity class.
 * It stores a map of all event ids to its event object.
 */
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
        Collection<UUID> eventC = events.keySet();
        return new ArrayList<>(eventC);
    }

    /**
     * Implements Getter, getEventsStrings, for event strings.
     *
     * @return event strings for all scheduled events
     */

    public List<String> getEventsStrings(List<UUID> IDs) {
        List<String> eventString = new ArrayList<>();
        for (UUID id: IDs){
            eventString.add(events.get(id).toString());
        }
        return eventString;
    }

    /**
     * Implements Getter, getStringToID, for events' IDs and string representation.
     *
     * @return a Map of Event string representation to its ID.
     */

    public HashMap<String, UUID> getStringToID(List<UUID> IDs){
        HashMap<String, UUID> eventStringToID = new HashMap<>();
        for (UUID id: IDs){
            eventStringToID.put(events.get(id).toString(), id);
        }
        return eventStringToID;
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
            if (!isFull(id)){
                availableEvents.add(id);
            }
        }
        return availableEvents;
    }

    /**
     * Implements Getter, getEventAttendees, for an event in events.
     *
     * @return event attendee list
     */

    public List<String> getEventAttendees(UUID eventID) {
        return events.get(eventID).getAttendees();
    }

    /**
     * Implements Getter, getEventStartTime, for an event in events.
     *
     * @return event start time
     */

    public LocalDateTime getEventStartTime(UUID eventID) {
        return events.get(eventID).getStartTime();
    }

    /**
     * Implements Checker, isFull, for an event's current capacity.
     *
     * @return true if the number of attendees exceeds event's maximum capacity and false otherwise
     */
    public boolean isFull(UUID eventID) {
        Event e = events.get(eventID);
        return (e.getAttendees().size() >= e.getMaxCapacity());
    }

    /**
     * Implements modifier, addEvent, for events.
     *
     * @return a boolean indicating if event was successfully added
     */
    public UUID addEvent(String eventName, String speaker, String organizer, LocalDateTime startTime,
                            String roomName, int max_capacity){
        Event new_event = new Event(eventName, speaker, organizer, startTime, roomName, max_capacity);
        for(UUID id : events.keySet()){
            Event e = events.get(id);
            if (e.getStartTime() == startTime && e.getEventRoomName().equals(roomName)){
                return null; //checked in RM canAdd?
            }
            if (id == new_event.getId()){
                return null; //do we need to check this? since everytime new event, ID distinct
            }
        }
        if (startTime.getHour() < 9 ){
            return null;
        }
        else if (startTime.getHour() == 16){
            if (startTime.getMinute() > 0){
                return null;
            }
        }
        else if (startTime.getHour() > 16) {
            return null;
        }
        events.put(new_event.getId(), new_event);
        return new_event.getId();
    }
//
// for phase 2
//        /**
//         * Implements modifier, removeEvent, for events.
//         *
//         * @return a boolean indicating if event was successfully removed
//         */
//        public boolean removeEvent(Event event){
//            if (events.containsKey(event.getId())){
//                events.remove(event.getId());
//                update canSignUp as necessary
//                return true;
//            }
//            return false;
//        }

    /**
     * Implements modifier, addAttendee, for event in events.
     *
     * @return a boolean indicating if user was successfully added
     */
    public boolean addAttendee(String username, UUID eventID){
        Event event = events.get(eventID);
        List<String> updated_attendees = getEventAttendees(event.getId());
        if (!event.getAttendees().contains(username) && updated_attendees.add(username)){
            event.setAttendees(updated_attendees);
            return true;
        }
        return false;
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
            return true;
        }
        return false;
    }

    /**
     * Implements a checker method, timeNotOverlap, to compare the start times of 2 events, and to ensure that their
     * event times do not overlap one another..
     *
     * @param existingEvent event that is already existing in this program
     * @param newEvent event we are trying to add
     *
     * @return a boolean indicating if the event times does not overlap.
     */
    public boolean timeNotOverlap(UUID existingEvent, UUID newEvent){
        LocalDateTime existingTime = this.events.get(existingEvent).getStartTime();
        LocalDateTime newTime = this.events.get(newEvent).getStartTime();
        return (!(newTime.isAfter(existingTime.minusHours(1))) || !(newTime.isBefore(existingTime.plusHours(1))));
    }

    /**
     * Implements a method, convertIDtoName, to convert a list of event ids to a list of its names
     *
     * @param uuidList list of event ids
     *
     * @return a list of strings, each the event's string representation according to its id.
     */
    public List<String> convertIDtoName(List<UUID> uuidList){
        List<String> nameList = new ArrayList<>();
        for (UUID id :uuidList){
            Event event = events.get(id);
            nameList.add(event.toString());
        }
        return nameList;
    }
}
