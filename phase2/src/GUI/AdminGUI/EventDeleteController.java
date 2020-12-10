package GUI.AdminGUI;

import GUI.EventInfoController;
import GUI.GUIController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class EventDeleteController extends EventInfoController implements GUIController {

    @Override
    public void initialize() {
        super.initialize();
    }

    @FXML public void handleDeleteButton(ActionEvent event){
        if (!getEventManager().removeEvent(getEventID())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deletion Error:");
            alert.setContentText("Sorry this event has already been deleted or modified");
            alert.showAndWait();
        }
        else {
            getRoomManager().removeEventFromSchedule(getEventID());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successfully Deleted!");
        }


    }
}
