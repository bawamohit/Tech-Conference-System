package GUI.AttendeeGUI.AvailableEvents;

import GUI.DataHolders.ManagersStorage;
import GUI.SceneParents.DisplayEventsController;
import UseCases.EventManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Available Events subscene for Attendee
 */
public class DisplayAvailableEventsController extends DisplayEventsController {
    private EventManager eventManager;

    /**
     * Initializes the Available Events subscene. Display available events.
     */
    public void initialize(){
        this.eventManager = ManagersStorage.getInstance().getEventManager();
        LocalDateTime currTime = LocalDateTime.now();
        List<UUID> availableEventIDs = eventManager.getAvailableEvents(currTime);
        List<List<String>> eventsInfo = eventManager.getAllEventsInfo(availableEventIDs);
        generateEventButtons("EventInfoSignUp", eventsInfo);
    }
}
