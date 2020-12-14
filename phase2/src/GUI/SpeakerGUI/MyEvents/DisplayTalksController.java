package GUI.SpeakerGUI.MyEvents;

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
 * My Talks subscene for Speakers
 */
public class DisplayTalksController extends DisplayEventsController {
    private WelcomeController welcomeController;
    private String username;
    private EventManager eventManager;
    private UserManager userManager;

    /**
     * Initializes the My Talks subscene for Speakers. Displays the speaker's talks
     */
    public void initialize(){
        super.initialize();
        generateEventButtons("EventInfoMessage", getEventsInfo());
    }
}
