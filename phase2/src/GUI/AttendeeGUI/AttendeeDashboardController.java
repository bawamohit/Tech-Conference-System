package GUI.AttendeeGUI;

import GUI.SceneParents.UserDashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.Observable;

/**
 * The dashboard for attendees
 */
public class AttendeeDashboardController extends UserDashboardController {

    /**
     * Initializes the Dashboard scene.
     */
    public void initialize(){
        super.initData("/GUI/AttendeeGUI/AvailableEvents/DisplayAvailableEvents");
    }

    /**
     * Handles action when the message button is clicked. Loads Message subscene
     */
    @FXML
    protected void handleMessageButtonAction() {
        loadSubScene("/GUI/Message/Message");
    }

    /**
     * Handles action of when the available events button is clicked. Loads AvailableEvents subscene
     */
    @FXML protected void handleAvailEventButtonAction() {
        loadSubScene("/GUI/AttendeeGUI/AvailableEvents/DisplayAvailableEvents");
    }

    /**
     * Handles action of when the My Events button is clicked. Loads MyEvents subscene
     */
    @FXML protected void handleMyEventButtonAction() {
        loadSubScene("/GUI/AttendeeGUI/MyEvents/DisplayMyEvents");
        observeDisplayEvents();
    }

    @Override
    public void update(Observable o, Object arg) {
        loadSubScene("/GUI/AttendeeGUI/MyEvents/DisplayMyEvents");
        observeDisplayEvents();
    }
}
