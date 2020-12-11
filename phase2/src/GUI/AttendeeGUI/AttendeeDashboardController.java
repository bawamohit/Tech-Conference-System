package GUI.AttendeeGUI;

import GUI.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Observable;
import java.util.Optional;
import java.util.UUID;

public class AttendeeDashboardController extends UserDashboardController {
    @FXML private AnchorPane anchorPane;
    @FXML private SplitPane splitPane;
    @FXML private Button availEventButton;
    @FXML private Button myEventButton;
    @FXML private Button messageButton;

    public void initialize(){
        super.initData("/GUI/AttendeeGUI/AvailableEvents/DisplayAvailableEvents");
    }

    @FXML
    protected void handleMessageButtonAction(ActionEvent event) {
        loadSubScene("/GUI/Message/Message");
    }

    @FXML protected void handleAvailEventButtonAction(ActionEvent event) {
        loadSubScene("/GUI/AttendeeGUI/AvailableEvents/DisplayAvailableEvents");
    }

    @FXML protected void handleMyEventButtonAction(ActionEvent event) {
        loadSubScene("/GUI/AttendeeGUI/MyEvents/DisplayMyEvents");
        observeDisplayEvents();
    }

    @Override
    public void update(Observable o, Object arg) {
        loadSubScene("/GUI/AttendeeGUI/MyEvents/DisplayMyEvents");
        observeDisplayEvents();
    }
}
