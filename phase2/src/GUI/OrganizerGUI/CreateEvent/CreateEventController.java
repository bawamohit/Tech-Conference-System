package GUI.OrganizerGUI.CreateEvent;

import GUI.EventHolder;
import GUI.ManagersStorage;
import GUI.UserHolder;
import UseCases.EventManager;
import UseCases.RoomManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.time.LocalDateTime;
import java.util.UUID;

public class CreateEventController {

    @FXML private TextField startTimeField;
    @FXML private TextField endTimeField;
    @FXML private TextField roomField;
    @FXML private TextField eventCapacityField;
    @FXML private TextField eventNameField;
    private RoomManager roomManager;
    private EventManager eventManager;
    private String eventName ;
    private String startTimeString;
    private String endTimeString;
    private String roomName;
    private int eventCapacity;
    private String username;

    @FXML public void initialize(){
        EventHolder.getInstance().setRoomAvailabilityChecked(false);
        roomManager  = ManagersStorage.getInstance().getRoomManager();
        eventManager = ManagersStorage.getInstance().getEventManager();
        username = UserHolder.getInstance().getUsername();
    }

    @FXML protected void handleCreateButtonAction(ActionEvent event) {
        eventName = eventNameField.getText();
        startTimeString = startTimeField.getText();
        endTimeString = endTimeField.getText();
        roomName = roomField.getText();
        if(missingInput()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all boxes");
            alert.showAndWait();
            return;
        }
        LocalDateTime startTime = getTime(startTimeString);
        LocalDateTime endTime = getTime(endTimeString);
        eventCapacity = eventCapacityFieldToInteger(eventCapacityField.getText());
        if(roomAvailabilityChecked()){
            UUID eventID = eventManager.addEvent(eventName, username, startTime, endTime, roomName, eventCapacity);
            roomManager.addEventToSchedule(eventID, roomName, startTime, endTime);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Event Created");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please check availability of the room first.");
            alert.showAndWait();
        }

    }

    private boolean missingInput(){
        boolean eventNameEmpty = eventName.isEmpty();
        boolean startTimeEmpty = startTimeString.isEmpty();
        boolean endTimeEmpty = endTimeString.isEmpty();
        boolean eventCapacityEmpty = eventCapacityField.getText().isEmpty();
        boolean roomNameEmpty = roomField.getText().isEmpty();
        return(eventNameEmpty || startTimeEmpty || endTimeEmpty || eventCapacityEmpty || roomNameEmpty);
    }

    @FXML  protected void handleCheckAvailabilityButtonAction(ActionEvent event) {
        roomName = roomField.getText();
        String eventCapacityString = eventCapacityField.getText();
        if(eventCapacityString.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please enter event capacity");
            alert.showAndWait();
            return;
        }
        eventCapacity = eventCapacityFieldToInteger(eventCapacityString);
        if(!roomManager.roomExists(roomName)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("This room does not exist");
            alert.showAndWait();
            return;
        }
        if (!roomManager.hasSpace(roomName, eventCapacity)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("This event capacity exceeds the room capacity. Please choose another room");
            alert.showAndWait();
            return;
        }
        EventHolder.getInstance().setRoomAvailabilityChecked(true);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("This room can host this event!");
        alert.showAndWait();

    }

    private boolean roomAvailabilityChecked(){
        return EventHolder.getInstance().getRoomAvailabilityChecked();
    }

    private int eventCapacityFieldToInteger(String eventCapacityField){
        return Integer.parseInt(eventCapacityField);
    }

    private LocalDateTime getTime(String time) {
        String pattern = "^([0-9][0-9][0-9][0-9])-(0[1-9]|1[0-2])-([0-2][0-9]|3[0-1]) (09|1[0-6]):([0-5][0-9])$|^.{0}$";

        if (!time.matches(pattern)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Invalid Time Input");
            alert.showAndWait();
        }
        int year2 = Integer.parseInt(time.substring(0, 4));
        int month2 = Integer.parseInt(time.substring(5, 7));
        int day2 = Integer.parseInt(time.substring(8, 10));
        int hour2 = Integer.parseInt(time.substring(11, 13));
        int minute2 = Integer.parseInt(time.substring(14, 16));
        return LocalDateTime.of(year2, month2, day2, hour2, minute2);
    }

}
