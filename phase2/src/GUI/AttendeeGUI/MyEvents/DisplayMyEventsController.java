package GUI.AttendeeGUI.MyEvents;

import GUI.*;
import GUI.DataHolders.EventHolder;
import GUI.DataHolders.ManagersStorage;
import GUI.DataHolders.UserHolder;
import GUI.SceneParents.DisplayEventsController;
import UseCases.EventManager;
import UseCases.UserManager;

import java.util.List;
import java.util.UUID;

/**
 * My Events subscene for Attendee
 */
public class DisplayMyEventsController extends DisplayEventsController {
    private String username;
    private EventManager eventManager;
    private UserManager userManager;

    /**
     * Initializes the My Events subscene. Displays the events the user signed up for.
     */
    public void initialize(){
        super.initialize();
        generateEventButtons("EventInfoCancel", getEventsInfo());
    }
}
