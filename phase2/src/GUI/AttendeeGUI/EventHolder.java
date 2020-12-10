package GUI.AttendeeGUI;

import java.util.UUID;

public class EventHolder {
    private UUID eventID;
    private boolean buttonClicked;

    private final static EventHolder INSTANCE = new EventHolder();

    private EventHolder(){}

    public static EventHolder getInstance(){return INSTANCE;}

    public void setEvent(UUID eventID){this.eventID = eventID;}

    public UUID getEventID() {
        return eventID;
    }

    public boolean getButtonClicked(){
        return buttonClicked;
    }

    public void setButtonClicked(boolean trueOrFalse){
        buttonClicked = trueOrFalse;
    }
}
