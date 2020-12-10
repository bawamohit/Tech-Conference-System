package GUI.OrganizerGUI.CreateRoom;

import GUI.ManagersStorage;
import UseCases.RoomManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CreateRoomController {
    @FXML TextField roomNameField;
    @FXML TextField maxOccupancyField;

    @FXML protected void handleCheckNameButtonAction(ActionEvent event) {
        RoomManager roomManager = ManagersStorage.getInstance().getRoomManager();
        String roomName = roomNameField.getText();
    }

    @FXML protected void handleCreateButtonAction(ActionEvent event) {
    }
}
