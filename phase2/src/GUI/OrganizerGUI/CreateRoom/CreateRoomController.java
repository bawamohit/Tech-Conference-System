package GUI.OrganizerGUI.CreateRoom;

import GUI.DataHolders.EventHolder;
import GUI.DataHolders.ManagersStorage;
import UseCases.RoomManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * The subscene where organizers can create rooms
 */
public class CreateRoomController {
    private RoomManager roomManager;

    @FXML TextField roomNameField;
    @FXML TextField maxOccupancyField;

    /**
     * Initializes the Create Room scene.
     */
    @FXML public void initialize(){
        this.roomManager = ManagersStorage.getInstance().getRoomManager();
    }

    /**
     * Handles action when the check name availability button is clicked. Checks whether room name is available.
     */
    @FXML protected void handleCheckNameButtonAction() {
        String roomName = roomNameField.getText();
        if (roomName.isEmpty()){
            createInfoAlert("Missing room name input.");
        } else if(roomManager.roomExists(roomName)){
            createInfoAlert("This room already exists. Please choose another name.");
        } else{
            createInfoAlert("Name available!");
        }
    }

    /**
     * Handles action when the create button is clicked. Creates the room.
     */
    @FXML protected void handleCreateButtonAction() {
        String roomName = roomNameField.getText();
        String roomCapacity = maxOccupancyField.getText();

        if (roomName.isEmpty() || roomCapacity.isEmpty()){
            createInfoAlert("Incomplete fields.");
        }else if (!roomCapacity.matches("\\d+")){
            createInfoAlert("Invalid room capacity input. Please enter numbers only.");
        }else if(roomManager.roomExists(roomName)){
            createInfoAlert("This room already exists. Please choose another name.");
        }else {
            roomManager.addRoom(roomName, Integer.parseInt(roomCapacity));
            createInfoAlert("Room Created!");
            roomNameField.clear();
            maxOccupancyField.clear();
        }
    }

    /**
     * Displays a pop-up message
     */
    private void createInfoAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
