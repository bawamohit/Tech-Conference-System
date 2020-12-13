package GUI.AdminGUI;

import GUI.SceneParents.DisplayEventsController;
import GUI.DataHolders.ManagersStorage;
import UseCases.EventManager;

import java.util.List;
import java.util.UUID;

/**
 * Deletable Events subscene for admin
 */
public class DisplayDeletableEventsController extends DisplayEventsController {
    private EventManager eventManager;

    /**
     * Initializes the Deletable Events subscene
     */
    public void initialize(){
        this.eventManager = ManagersStorage.getInstance().getEventManager();
        List<UUID> deletableEventIds = eventManager.getEvents();
        List<List<String>> eventsInfo = eventManager.getAllEventsInfo(deletableEventIds);
        generateEventButtons("/GUI/AdminGUI/EventInfoDelete", eventsInfo);
    }
}
