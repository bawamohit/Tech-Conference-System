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

    @FXML public void handleDeleteButton(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (!getEventManager().removeEvent(getEventID())){
            alert.setTitle("Deletion Error:");
            alert.setContentText("Sorry this event has already been deleted or modified");
        }
        else {
            getRoomManager().removeEventFromSchedule(getEventID());
            alert.setTitle("Successfully Deleted!");
        }
        alert.showAndWait();
        setChanged();
        notifyObservers();
    }
}
