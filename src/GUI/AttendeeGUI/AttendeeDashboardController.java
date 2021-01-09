package GUI.AttendeeGUI;

import GUI.SceneParents.UserDashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.Observable;

/**
 * Dashboard for Attendee
 */
public class AttendeeDashboardController extends UserDashboardController {
    @FXML private AnchorPane anchorPane;
    @FXML private SplitPane splitPane;
    @FXML private Button availEventButton;
    @FXML private Button myEventButton;
    @FXML private Button messageButton;

    /**
     * Initializes the dashboard for attendees. Displays the available events.
     */
    public void initialize(){
        super.initData("/GUI/AttendeeGUI/AvailableEvents/DisplayAvailableEvents");
    }

    /**
     * Handles the action when the Message button is clicked. Displays the Message scene.
     */
    @FXML
    protected void handleMessageButtonAction(ActionEvent event) {
        loadSubScene("/GUI/Message/Message");
    }

    /**
     * Handles the action when the Available Event button is clicked. Displays the Available Events scene.
     */
    @FXML protected void handleAvailEventButtonAction(ActionEvent event) {
        loadSubScene("/GUI/AttendeeGUI/AvailableEvents/DisplayAvailableEvents");
    }

    /**
     * Handles the action when the My Event button is clicked. Displays the My Events scene.
     */
    @FXML protected void handleMyEventButtonAction(ActionEvent event) {
        loadSubScene("/GUI/AttendeeGUI/MyEvents/DisplayMyEvents");
        observeDisplayEvents();
    }

    /**
     * Updates the scene when an action is done.
     */
    @Override
    public void update(Observable o, Object arg) {
        loadSubScene("/GUI/AttendeeGUI/MyEvents/DisplayMyEvents");
        observeDisplayEvents();
    }
}
