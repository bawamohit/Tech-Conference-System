package GUI.AttendeeGUI.MyEvents;

import GUI.EventInfoController;
import GUI.GUIController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class EventInfoCancelController extends EventInfoController implements GUIController {

    @Override
    public void initialize() {
        super.initialize();
    }

    @FXML public void handleCancelButton(ActionEvent event){
        getEventManager().removeAttendee(getUsername(), getEventID());
        getUserManager().removeEventAttending(getUsername(), getEventID());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Registration Successfully Canceled");
        alert.showAndWait();

        setChanged();
        notifyObservers();
    }
}
