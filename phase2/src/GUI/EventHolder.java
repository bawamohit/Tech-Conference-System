package GUI;

import java.util.UUID;

public class EventHolder {
    private UUID eventID;
    private boolean buttonClicked;
    private boolean roomAvailabilityChecked;

    private final static EventHolder INSTANCE = new EventHolder();

    private EventHolder(){}

    public static EventHolder getInstance(){return INSTANCE;}

    public void setEvent(UUID eventID){this.eventID = eventID;}

    public UUID getEventID() {
        return eventID;
    }

    public boolean getRoomAvailabilityChecked() {
        return roomAvailabilityChecked;
    }

    public void setRoomAvailabilityChecked(boolean trueOrFalse){
        roomAvailabilityChecked = trueOrFalse;
    }
    public void setButtonClicked(boolean trueOrFalse){
        buttonClicked = trueOrFalse;
    }
}
