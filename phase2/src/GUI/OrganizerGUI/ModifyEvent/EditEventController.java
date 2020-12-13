package GUI.OrganizerGUI.ModifyEvent;

import GUI.DataHolders.EventHolder;
import GUI.DataHolders.ManagersStorage;
import UseCases.EventManager;
import UseCases.RoomManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Observable;
import java.util.UUID;

/**
 * The subscene where organizers can edit events
 */
public class EditEventController extends Observable {

    @FXML private TextField textField;
    @FXML private Label text;
    @FXML private RadioButton capacityRadioButton;
    @FXML private RadioButton roomRadioButton;
    @FXML Label label1;
    @FXML Label label2;
    @FXML Label label3;

    private EventManager eventManager;
    private RoomManager roomManager;
    private UUID eventID;

    /**
     * Initializes the edit event scene.
     */
    public void initialize() {
        this.eventManager = ManagersStorage.getInstance().getEventManager();
        this.roomManager = ManagersStorage.getInstance().getRoomManager();
        this.eventID = EventHolder.getInstance().getEventID();
    }

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
        String roomName = eventManager.getEventRoomName(eventID);
        int speakers = eventManager.getEventSpeaker(eventID).size();
        if (roomManager.cannotSetCapacity(roomName, newCapacity, speakers)){
            createAlertMessage("The new capacity exceeds the room capacity. Please choose a lower capacity.");
            return;
        }
        if(!eventManager.canChangeCapacity(eventID, newCapacity)){
            createAlertMessage("The current number of attendees exceeds the new capacity. Please enter a higher capacity.");
            return;
        }
        eventManager.setMaxCapacity(eventID, newCapacity);
        createAlertMessage("Capacity Changed!");
        setChanged();
        notifyObservers("EditEvent");
    }

    private void changeRoom(){
        String roomName = textField.getText();

        if (!roomManager.roomExists(roomName)){
            createAlertMessage("This room does not exist.");
            return;
        }
        if(!roomAllowsEvent(eventID)){
            createAlertMessage("The capacity of this event exceeds the new room's capacity, thus cannot change room.");
            return;
        }
        LocalDateTime start = eventManager.getEventStartTime(eventID);
        LocalDateTime end = eventManager.getEventEndTime(eventID);
        if(roomManager.cannotAddEvent(roomName, start, end)){
            createAlertMessage("This room is not available at this time.");
            return;
        }
        eventManager.setEventRoomName(eventID, roomName);
        roomManager.removeEventFromSchedule(eventID);
        roomManager.addEventToSchedule(eventID, roomName, start, end);
        createAlertMessage("Room Changed!");
        setChanged();
        notifyObservers("EditEvent");
    }

    @FXML private void handleChangeCapacityButtonAction(){
        text.setText("New Capacity:  ");
        textField.setVisible(true);
    }

    @FXML private void createAlertMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML private void handleChangeRoomButtonAction(){
        text.setText("New Room:  ");
        textField.setVisible(true);
    }
    private boolean roomAllowsEvent(UUID eventID){
        int roomCapacity = roomManager.getRoomCapacity(eventManager.getEventRoomName(eventID));
        int eventCapacity = eventManager.getEventMaxCapacity(eventID);
        int speakerSize = eventManager.getEventSpeaker(eventID).size();
        return roomCapacity > (eventCapacity + speakerSize);
    }

    public void handleBackButtonAction(ActionEvent actionEvent) {
        setChanged();
        notifyObservers("ModifyEvent");
    }
}
