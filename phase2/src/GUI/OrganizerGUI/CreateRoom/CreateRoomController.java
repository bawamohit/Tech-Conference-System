package GUI.OrganizerGUI.CreateRoom;

import Entities.Event;
import GUI.EventHolder;
import GUI.ManagersStorage;
import UseCases.RoomManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class CreateRoomController {
    @FXML TextField roomNameField;
    @FXML TextField maxOccupancyField;
    private RoomManager roomManager;

    @FXML public void initialize(){
        roomManager = ManagersStorage.getInstance().getRoomManager();
        EventHolder.getInstance().setRoomAvailabilityChecked(false);
    }

    @FXML protected void handleCheckNameButtonAction(ActionEvent event) {
        String roomName = roomNameField.getText();
        if(roomManager.roomExists(roomName)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("This room already exists. Please choose another name");
            alert.showAndWait();
            return;
        }
        if (roomName.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Missing room name input");
            alert.showAndWait();
            return;
        }
        EventHolder.getInstance().setRoomAvailabilityChecked(true);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Name available!");
        alert.showAndWait();

    }

    @FXML protected void handleCreateButtonAction(ActionEvent event) {
        if (missingInput()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all boxes");
            alert.showAndWait();
            return;
        }
        String roomName = roomNameField.getText();
        if (!validCapacityInput()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Invalid room capacity input. Please enter digits only");
            alert.showAndWait();
            return;
        }
        int roomCapacity = Integer.parseInt(maxOccupancyField.getText());
        if (!EventHolder.getInstance().getRoomAvailabilityChecked()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please check room name availability first.");
            alert.showAndWait();
            return;
        }
        roomManager.addRoom(roomName, roomCapacity);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Room Created!");
        alert.showAndWait();
    }
    private boolean missingInput(){
        boolean roomCapacityEmpty = maxOccupancyField.getText().isEmpty();
        boolean roomNameEmpty = roomNameField.getText().isEmpty();
        return(roomCapacityEmpty || roomNameEmpty);
    }

    private boolean validCapacityInput(){
        return maxOccupancyField.getText().matches("^[0-9]+$");
    }
}
