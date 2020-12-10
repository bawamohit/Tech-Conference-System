package GUI.OrganizerGUI.CreateEvent;

import GUI.ManagersStorage;
import UseCases.RoomManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CreateEventController {

    @FXML private TextField startTimeField;
    @FXML private TextField endTimeField;
    @FXML private TextField roomField;
    @FXML private TextField eventCapacityField;
    @FXML private TextField eventNameField;

    @FXML protected void handleCreateButtonAction(ActionEvent event) {
    }

    @FXML  protected void handleCheckCapacityButtonAction(ActionEvent event) {
        RoomManager roomManager = ManagersStorage.getInstance().getRoomManager();
        String roomName = roomField.getText();
    }
}
