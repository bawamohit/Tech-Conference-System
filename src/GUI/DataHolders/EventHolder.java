package GUI.DataHolders;

import java.util.UUID;

/**
 * This class that stores the instance of the event
 */
public final class EventHolder {
    private UUID eventID;

    private final static EventHolder INSTANCE = new EventHolder();

    private EventHolder(){}

    /**
     * Gets the EventHolder instance that is initialized when program is run
     *
     * @return EventHolder
     */
    public static EventHolder getInstance(){return INSTANCE;}

    /** Setter for id of event
     *
     * @param eventID id of event
     */
    public void setEvent(UUID eventID){this.eventID = eventID;}

    /** Getter for id of event
     *
     * @return id of event
     */
    public UUID getEventID() {
        return eventID;
    }
}
