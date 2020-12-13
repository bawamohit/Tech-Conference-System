package GUI.OrganizerGUI.ModifyEvent;

import GUI.DataHolders.EventHolder;
import GUI.SceneParents.EventInfoController;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.util.UUID;

public class EventInfoModifyController extends EventInfoController {

    public void handleModifySpeakerButton(ActionEvent actionEvent) {
        setChanged();
        notifyObservers("ModifySpeaker");
    }

    public void handleEditEventButton(ActionEvent actionEvent) {
        setChanged();
        notifyObservers("EditEvent");
    }

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
