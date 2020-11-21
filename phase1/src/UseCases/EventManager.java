package UseCases;

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

//    phase 2
//    /**
//     * Implements Getter, getEvents, for event IDs.
//     *
//     * @return event IDs for all scheduled events
//     */
//
//    public List<UUID> getEvents() {
//        Collection<UUID> eventC = events.keySet();
//        return new ArrayList<>(eventC);
//    }

    /**
     * Implements Getter, getEventsStrings, for event strings.
     *
     * @param IDs list of event IDs for which to get string representations for
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
     * @param eventID ID of the event to retrieve attendee list for
     *
     * @return event attendee list, which should not include the speaker
     */
    public List<String> getEventAttendees(UUID eventID) {
        return events.get(eventID).getAttendees();
    }

    /**
     * Implements Getter, getEventStartTime, for an event in events.
     *
     * @param eventID ID of the event to retrieve the start time for
     *
     * @return event start time
     */
    public LocalDateTime getEventStartTime(UUID eventID) {
        return events.get(eventID).getStartTime();
    }

    /**
     * Implements Checker, isFull, for an event's current capacity.
     *
     * @param eventID ID of the event to check availability for; should be a valid event in list of existing events
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
     * @param eventName name of the event to be added
     * @param speaker name of speaker of this new event
     * @param organizer name of organizer of this new event
     * @param startTime this event's start time; it can take on any time between 9-16
     * @param roomName name of the room where this event is located in
     * @param max_capacity the maximum capacity of this event excluding the speaker; this should not exceed the maximum
     *                     capacity of the room
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
     * @param username name of attendee to be added
     * @param eventID ID of event to add attendee to
     *
     * @return a boolean indicating if user was successfully added
     */
    public boolean addAttendee(String username, UUID eventID){
        Event event = events.get(eventID);
        List<String> updated_attendees = getEventAttendees(event.getId());
        if (!event.getAttendees().contains(username)) {
            updated_attendees.add(username);
            event.setAttendees(updated_attendees);
            return true;
        }
        return false;
    }

    /**
     * Implements modifier, removeAttendee, for event in events.
     *
     * @param username attendee username
     * @param eventID list of event ids
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

    /** Determines whether two times overlap.
     *
     * @param existingTime A time that is already occupied.
     * @param newTime A new time that will be compared.
     *
     * @return true if existingTime does not overlap with newTime, and false otherwise.
     */
    public boolean scheduleNotOverlap(LocalDateTime existingTime, LocalDateTime newTime){
        return (!(newTime.isAfter(existingTime.minusHours(1))) || !(newTime.isBefore(existingTime.plusHours(1))));
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
        return scheduleNotOverlap(existingTime, newTime);
    }

}
