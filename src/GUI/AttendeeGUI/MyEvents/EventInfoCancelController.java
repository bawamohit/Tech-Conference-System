package GUI.AttendeeGUI.MyEvents;

import GUI.SceneParents.EventInfoController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

/**
 * The subscene where event infomation is shown
 */
public class EventInfoCancelController extends EventInfoController {

    /**
     * Initializes the EventInfo scene.
     */
    @Override
    public void initialize() {
        super.initialize();
    }

    /**
     * Handles action of when the cancel button is clicked. Cancels the attendee from the event
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
