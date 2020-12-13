package GUI.DataHolders;

import java.util.UUID;

public final class EventHolder {
    private UUID eventID;

    private final static EventHolder INSTANCE = new EventHolder();

    private EventHolder(){}

    public static EventHolder getInstance(){return INSTANCE;}

    public void setEvent(UUID eventID){this.eventID = eventID;}

    public UUID getEventID() {
        return eventID;
    }
}
