package GUI.SpeakerGUI;

import GUI.SceneParents.UserDashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;

import java.util.Observable;

public class SpeakerDashboardController extends UserDashboardController {
    @FXML private AnchorPane anchorPane;
    @FXML private SplitPane splitPane;
    @FXML private Button availEventButton;
    @FXML private Button myEventButton;
    @FXML private Button inboxButton;
    @FXML private Button messageTalkButton;

    public void initialize(){
        super.initData("/GUI/SpeakerGUI/MyEvents/MyEvents");
    }

    @FXML
    protected void handleInboxButtonAction(ActionEvent event) {
        loadSubScene("/GUI/SpeakerGUI/Message/SpeakerMessage");
    }

    @FXML
    protected void handleMessageTalkButtonAction(ActionEvent event) {
        loadSubScene("/GUI/SpeakerGUI/MyEvents/MyEvents");
        observeDisplayEvents();
    }

    @FXML protected void handleMyEventButtonAction(ActionEvent event) {
        loadSubScene("/GUI/SpeakerGUI/MyEvents/MyEvents");
        observeDisplayEvents();
    }

    @Override
    public void update(Observable o, Object arg) {
        loadSubScene("/GUI/SpeakerGUI/MyEvents/MyEvents");
        observeDisplayEvents();
    }
}
