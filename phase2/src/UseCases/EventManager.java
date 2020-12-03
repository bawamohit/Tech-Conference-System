package UseCases;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import Entities.Event;

/**
 * This class is one of the use cases classes for this program, specifically for manipulating the Event entity class.
 * It stores a map of all event ids to its event object.
 */
public class EventManager {
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
     * Implements getter for event name of a particular event.
     *
     * @param id The id of the particular event.
     *
     * @return The name of the particular event
     */
    public String getEventName(UUID id) {
        return events.get(id).getEventName();
    }

    /**
     * Implements getter for event speaker name of a particular event.
     *
     * @param id The id of the particular event.
     *
     * @return The list of speakers of the particular event
     */
    public List<String> getEventSpeaker(UUID id) {
        return events.get(id).getSpeakers();
    }

    /**
     * Implements getter for event organizer name of a particular event.
     *
     * @param id The id of the particular event.
     *
     * @return The organizer's name of the particular event
     */
    public String getEventOrganizer(UUID id) {
        return events.get(id).getOrganizer();
    }

    /**
     * Implements getter for event room name of a particular event.
     *
     * @param id The id of the particular event.
     *
     * @return The room's name of the particular event
     */
    public String getEventRoomName(UUID id) {
        return events.get(id).getRoomName();
    }

    /**
     * Implements getter for event maximum capacity of a particular event.
     *
     * @param id The id of the particular event.
     *
     * @return The maximum capacity of the particular event
     */
    public int getEventMaxCapacity(UUID id) {
        return events.get(id).getMaxCapacity();
    }

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
     * @return event IDs for all events after currTime still open for signup
     */
    public List<UUID> getAvailableEvents(LocalDateTime currTime) {
        ArrayList<UUID> availableEvents = new ArrayList<>();
        for (UUID id: events.keySet()){
            if (currTime.isBefore(events.get(id).getStartTime())){
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
     * Implements Getter, getEventEndTime, for an event in events.
     *
     * @param eventID ID of the event to retrieve the end time for
     *
     * @return event end time
     */
    public LocalDateTime getEventEndTime(UUID eventID) {
        return events.get(eventID).getEndTime();
    }

    /**
     * Implements Checker, isFull, for an event's current capacity.
     *
     * @param eventID ID of the event to check availability for; should be a valid event in list of existing events
     *
     * @return a boolean indicating if the event is full
     */
    public boolean isFull(UUID eventID) {
        Event e = events.get(eventID);
        return (e.getAttendees().size() >= e.getMaxCapacity());
    }

    /**
     * Implements Checker, canChangeCapacity, for an event's capacity.
     *
     * @param eventID ID of the event to check capacity for
     * @param newCapacity new capacity to change to
     *
     * @return a boolean indicating if event's maximum capacity can be changed to newCapacity
     */
    public boolean canChangeCapacity(UUID eventID, int newCapacity) {
        Event e = events.get(eventID);
        return (e.getAttendees().size() <= newCapacity);
    }

    /**
     * Implements Setter, setMaxCapacity, for an event.
     *
     * @param eventID ID of event to change capacity for
     * @param newCap new maximum capacity of event
     */
    public void setMaxCapacity(UUID eventID, int newCap){ events.get(eventID).setMaxCapacity(newCap); }


    /**
     * Implements modifier, addEvent, for events.
     *
     * @param eventName name of the event to be added
     * @param organizer name of organizer of this new event
     * @param startTime this event's start time; it can take on any time between 9-16
     * @param roomName name of the room where this event is located in
     * @param maxCapacity the maximum capacity of this event excluding the speaker; this should not exceed the maximum
     *                     capacity of the room
     *
     * @return The ID of the new event created
     */
    public UUID addEvent(String eventName, String organizer, LocalDateTime startTime, LocalDateTime endTime,
                         String roomName, int maxCapacity){
        Event newEvent = new Event(eventName, organizer, startTime, endTime, roomName, maxCapacity);
        events.put(newEvent.getId(), newEvent);
        return newEvent.getId();
    }
    /**
     * Implements modifier, addEvent, for events. (Only to be used for reading from files)
     *
     * @param eventName name of the event to be added
     * @param organizer name of organizer of this new event
     * @param startTime this event's start time; it can take on any time between 9-16
     * @param roomName name of the room where this event is located in
     * @param maxCapacity the maximum capacity of this event excluding the speaker; this should not exceed the maximum
     *                     capacity of the room
     * @param id id of the new event
     * @return The ID of the new event created
     */
    public UUID addEvent(String eventName, String organizer, LocalDateTime startTime, LocalDateTime endTime,
                         String roomName, int maxCapacity, UUID id){
        Event newEvent = new Event(eventName, organizer, startTime, endTime, roomName, maxCapacity);
        newEvent.setId(id);
        events.put(newEvent.getId(), newEvent);
        return newEvent.getId();
    }

    public void addSpeaker(UUID eventID, String newSpeaker){
        Event oldEvent = events.get(eventID);
        List<String> speakers = oldEvent.getSpeakers();
        speakers.add(newSpeaker);
    }

    /**
     * Implements modifier, removeEvent, for events.
     *
     * @return a boolean indicating if event was successfully removed
     */
    public boolean removeEvent(UUID eventID){
        if (events.containsKey(eventID)){
            events.remove(eventID);
            return true;
        }
        return false;
    }

    /**
     * Implements modifier, addAttendee, for event in events.
     *
     * @param eventID ID of event to add attendee to
     * @param username name of attendee to be added
     * @return a boolean indicating if user was successfully added
     */
    public boolean addAttendee(UUID eventID, String username){
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

    /** Determines whether two time blocks (start time ~ end time) overlap.
     *
     * @param existingST A start time that is already occupied.
     * @param existingST An end time that is already occupied.
     * @param newST A new start time that will be compared.
     * @param newET A new end time that will be compared.
     *
     * @return a boolean indicating if the new and existing time blocks overlap
     */
    public boolean scheduleNotOverlap(LocalDateTime existingST, LocalDateTime existingET,
                                      LocalDateTime newST, LocalDateTime newET){
        return (!(newET.isAfter(existingST)) || !(newST.isBefore(existingET)));
    }


}
