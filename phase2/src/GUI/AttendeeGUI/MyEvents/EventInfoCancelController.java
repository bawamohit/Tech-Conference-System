package GUI.AttendeeGUI.MyEvents;

import GUI.SceneParents.EventInfoController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

/**
 * Event Info subscene for Attendee
 */
public class EventInfoCancelController extends EventInfoController {

    /**
     * Initializes the Event Info subscene. Displays the event info
     */
    @Override
    public void initialize() {
        super.initialize();
    }

    /**
     * Handles the action when the Cancel button is clicked. Cancel the registration of the user in that event.
     */
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
