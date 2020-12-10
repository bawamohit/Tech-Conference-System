package GUI.AttendeeGUI;

import GUI.*;
import GUI.AttendeeGUI.MyEvents.EventInfoController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class AttendeeDashboardController extends UserDashboardController {
    public Button cancelButton;

    @FXML private AnchorPane anchorPane;
    @FXML private SplitPane splitPane;
    @FXML private GridPane gridPane;
    @FXML private Label profile;
    @FXML private Button availEventButton;
    @FXML private Button myEventButton;
    @FXML private Button messageButton;

    @FXML
    protected void handleMessageButtonAction(ActionEvent event) {
        loadSubScene("Message");
        cancelButton.setVisible(false);
    }

    @FXML protected void handleAvailEventButtonAction(ActionEvent event) {
        loadSubScene("AvailableEvents");
        cancelButton.setVisible(false);
    }

    @FXML protected void handleMyEventButtonAction(ActionEvent event) {
        loadSubScene("MyEvents");
        cancelButton.setVisible(true);
    }

    @FXML public void handleCancelButtonAction(ActionEvent event){
        if (ifEventButtonClicked()){
            FXMLLoader loader = new FXMLLoader((getClass().getResource("MyEvents/EventInfo.fxml")));
            try{
                loader.load();
                UUID eventID = ((EventInfoController)loader.getController()).eventID;
                getEventManager().removeAttendee(getUsername(), eventID);
                getUserManager().removeEventAttending(getUsername(), eventID);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Successfully Cancelled");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()){
                    loadSubScene("MyEvents");
                }
            }
            catch (IOException e){

                e.printStackTrace();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please click an event you would like to cancel first");
            alert.showAndWait();
        }
    }

    @FXML private boolean ifEventButtonClicked() {
        return EventHolder.getInstance().getButtonClicked();
    }
}
