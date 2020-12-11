package GUI.OrganizerGUI.ModifyEvent;

import GUI.DisplayEventsController;
import GUI.ManagersStorage;
import UseCases.EventManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class DisplayModifiableEventsController extends DisplayEventsController {
    /**
     * Initializes the Modify Event scene.
     */
    public void initialize(){
        EventManager eventManager = ManagersStorage.getInstance().getEventManager();
        LocalDateTime currTime = LocalDateTime.now();
        List<UUID> availableEventIDs = eventManager.getAvailableEvents(currTime);
        List<List<String>> eventsInfo = eventManager.getAllEventsInfo(availableEventIDs);
        generateEventButtons("EventInfoModify", eventsInfo);
    }
}
