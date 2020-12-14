package GUI.AdminGUI;

import GUI.SceneParents.EventInfoController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

/**
 * Event Info subscene for admin
 */
public class EventInfoDeleteController extends EventInfoController {

    /**
     * Handles the action when the Delete button is clicked. Deletes the event that was clicked.
     */
    @FXML public void handleDeleteButton(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        if(!getSpeakersList().isEmpty()){
            for(String speaker: getSpeakersList().split(",")){
                getUserManager().removeEventAttending(speaker, getEventID());
            }
        }

        getEventManager().removeEvent(getEventID());
        getRoomManager().removeEventFromRoom(getEventID(), getRoom());
        alert.setContentText("Successfully Deleted!");
        alert.showAndWait();
        setChanged();
        notifyObservers();
    }
}
