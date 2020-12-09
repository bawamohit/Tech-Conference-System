package GUI.AttendeeGUI.AvailableEvents;

import GUI.AttendeeGUI.Message.CollocutorHolder;

import java.util.UUID;

public class EventHolder {
    private UUID eventID;
    private final static EventHolder INSTANCE = new EventHolder();

    private EventHolder(){}

    public static EventHolder getInstance(){return INSTANCE;}

    public void setEvent(UUID eventID){this.eventID = eventID;}

    public UUID getEventID() {
        return eventID;
    }
}
