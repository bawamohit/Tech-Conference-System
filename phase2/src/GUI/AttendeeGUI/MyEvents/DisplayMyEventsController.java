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
 * The subscene where my events are displayed
 */
public class DisplayMyEventsController extends DisplayEventsController {
    private String username;
    private EventManager eventManager;
    private UserManager userManager;

    /**
     * Initializes the My Events scene.
     */
    public void initialize(){
        super.initialize();
        generateEventButtons("EventInfoCancel", getEventsInfo());
    }
}
