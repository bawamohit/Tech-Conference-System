package GUI.OrganizerGUI.EditEvent;

import GUI.EventHolder;
import GUI.ManagersStorage;
import UseCases.EventManager;
import UseCases.RoomManager;
import UseCases.UserManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

public class EditEventController {

    @FXML private TextField textField;
    @FXML private Label text;
    @FXML private RadioButton capacityRadioButton;
    @FXML private RadioButton roomRadioButton;
    private EventManager eventManager = ManagersStorage.getInstance().getEventManager();
    private UserManager userManager = ManagersStorage.getInstance().getUserManager();
    private RoomManager roomManager = ManagersStorage.getInstance().getRoomManager();

    @FXML public void handleChangeButtonAction(){
        if(capacityRadioButton.isSelected()){
            changeCapacity();
        }
        else if(roomRadioButton.isSelected()){
            changeRoom();
        }
        else{
            createAlertMessage("Please select either to change event capacity or room");
        }
    }

    private void changeCapacity(){
        String capacityString = textField.getText();
        int newCapacity = Integer.parseInt(capacityString);
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../Events/EventInfo.fxml")));
        if (ifEventButtonClicked()) {
            try{
                loader.load();
                UUID eventID = EventHolder.getInstance().getEventID();
                String roomName = eventManager.getEventRoomName(eventID);
                int speakers = eventManager.getEventSpeaker(eventID).size();
                if (!roomManager.canSetCapacity(roomName, newCapacity, speakers)){
                    createAlertMessage("The new capacity exceeds the room capacity. Please choose a lower capacity.");
                    return;
                }
                if(!eventManager.canChangeCapacity(eventID, newCapacity)){
                    createAlertMessage("The current number of attendees exceeds the new capacity. Please enter a higher capacity.");
                    return;
                }
                eventManager.setMaxCapacity(eventID, newCapacity);
                createAlertMessage("Capacity Changed!");
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        createAlertMessage("Please click an event you would like to change first");
    }

    private void changeRoom(){
        String roomName = textField.getText();
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../Events/EventInfo.fxml")));
        if (ifEventButtonClicked()) {
            try{
                loader.load();
                UUID eventID = EventHolder.getInstance().getEventID();
                if (!roomManager.roomExists(roomName)){
                    createAlertMessage("This room does not exist.");
                    return;
                }
                if(!roomAllowsEvent(eventID)){
                    createAlertMessage("The number of registered people of this event exceeds the new room's capacity, thus cannot change room.");
                    return;
                }
                LocalDateTime start = eventManager.getEventStartTime(eventID);
                LocalDateTime end = eventManager.getEventEndTime(eventID);
                if(!roomManager.canAddEvent(roomName, start,end)){
                    createAlertMessage("This room is not available at this time.");
                    return;
                }
                eventManager.setEventRoomName(eventID, roomName);
                roomManager.removeEventFromSchedule(eventID);
                roomManager.addEventToSchedule(eventID, roomName, start, end);
                createAlertMessage("Room Changed!");
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        createAlertMessage("Please click an event you would like to change first");
    }

    @FXML private void handleChangeCapacityButtonAction(){
        text.setText("New Capacity:  ");
    }

    @FXML private void createAlertMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML private boolean ifEventButtonClicked() {
        return EventHolder.getInstance().getButtonClicked();
    }

    @FXML private void handleChangeRoomButtonAction(){
        text.setText("New Room:  ");
    }
    private boolean roomAllowsEvent(UUID eventID){
        int roomCapacity = roomManager.getRoomCapacity(eventManager.getEventRoomName(eventID));
        int attendeeSize = eventManager.getEventAttendees(eventID).size();
        int speakerSize = eventManager.getEventSpeaker(eventID).size();
        return roomCapacity > (attendeeSize + speakerSize);
    }
}
