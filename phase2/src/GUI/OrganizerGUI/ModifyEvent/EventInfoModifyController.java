package GUI.OrganizerGUI.ModifyEvent;

import GUI.DataHolders.EventHolder;
import GUI.SceneParents.EventInfoController;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.util.UUID;

/**
 * The subscene where event infomation is shown
 */
public class EventInfoModifyController extends EventInfoController {

    /**
     * Handles action of when the modify speaker button is clicked. Loads ModifySpeaker subscene
     */
    public void handleModifySpeakerButton(ActionEvent actionEvent) {
        setChanged();
        notifyObservers("ModifySpeaker");
    }

    /**
     * Handles action of when the edit event button is clicked. Loads EditEvent subscene
     */
    public void handleEditEventButton(ActionEvent actionEvent) {
        setChanged();
        notifyObservers("EditEvent");
    }

    /**
     * Handles action of when the remove event button is clicked. Cancels an event.
     */
    public void handleRemoveEventButton(ActionEvent actionEvent) {
        UUID eventID = EventHolder.getInstance().getEventID();
        for (String username: getEventManager().getEventAttendees(eventID)){
            getUserManager().removeEventAttending(username, eventID);
        }
        if (getEventManager().removeEvent(eventID) && getRoomManager().removeEventFromSchedule(eventID)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Event Successfully Removed.");
            alert.showAndWait();
            setChanged();
            notifyObservers("ModifyEvent");
        }
    }
}
