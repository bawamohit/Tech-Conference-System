package GUI.AttendeeGUI.AvailableEvents;

import GUI.*;
import UseCases.EventManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class AvailableEventsController extends DisplayEventsController {
    private EventManager eventManager;

    public void initialize(){
        this.eventManager = ManagersStorage.getInstance().getEventManager();
        LocalDateTime currTime = LocalDateTime.now();
        List<UUID> availableEventIDs = eventManager.getAvailableEvents(currTime);
        List<List<String>> eventsInfo = eventManager.getAllEventsInfo(availableEventIDs);
        generateEventButtons("EventSignUp", eventsInfo);
    }
}
