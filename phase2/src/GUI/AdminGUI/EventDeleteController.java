package GUI.AdminGUI;

import GUI.AttendeeGUI.AbstractEventInfoController;
import GUI.GUIController;
import GUI.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class EventDeleteController extends AbstractEventInfoController implements GUIController {
    
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
