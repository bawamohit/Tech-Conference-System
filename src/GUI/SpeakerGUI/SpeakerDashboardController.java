package GUI.SpeakerGUI;

import GUI.SceneParents.UserDashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;

import java.util.Observable;

/**
 * Dashboard for Speakers
 */
public class SpeakerDashboardController extends UserDashboardController {
    @FXML private AnchorPane anchorPane;
    @FXML private SplitPane splitPane;
    @FXML private Button availEventButton;
    @FXML private Button myEventButton;
    @FXML private Button inboxButton;
    @FXML private Button messageTalkButton;

    /**
     * Initializes the dashboard for speakers. Displays the speaker's talks
     */
    public void initialize(){
        super.initData("/GUI/SpeakerGUI/MyEvents/DisplayTalks");
    }

    /**
     * Handles the action when the Inbox button is clicked. Displays the speaker's inbox
     */
    @FXML
    protected void handleInboxButtonAction(ActionEvent event) {
        loadSubScene("/GUI/Message/Message");
    }

    /**
     * Handles the action when the My Events button is clicked. Displays the speaker's talks
     */
    @FXML protected void handleMyEventButtonAction(ActionEvent event) {
        loadSubScene("/GUI/SpeakerGUI/MyEvents/DisplayTalks");
        observeDisplayEvents();
    }

    /**
     * Update the scene after an action is done
     */
    @Override
    public void update(Observable o, Object arg) {
        loadSubScene("/GUI/SpeakerGUI/MyEvents/MyEvents");
        observeDisplayEvents();
    }
}
