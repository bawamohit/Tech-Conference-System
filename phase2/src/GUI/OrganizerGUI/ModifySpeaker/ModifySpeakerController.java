package GUI.OrganizerGUI.ModifySpeaker;

import Entities.UserType;
import GUI.EventHolder;
import GUI.ManagersStorage;
import UseCases.EventManager;
import UseCases.RoomManager;
import UseCases.UserManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.UUID;

public class ModifySpeakerController {
    @FXML private RadioButton removeRadioButton;
    @FXML private RadioButton addRadioButton;
    @FXML private TextField speakerNameField;
    private EventManager eventManager = ManagersStorage.getInstance().getEventManager();
    private UserManager userManager = ManagersStorage.getInstance().getUserManager();
    private RoomManager roomManager = ManagersStorage.getInstance().getRoomManager();

    /**
     * Handles action when the add button is clicked. Adds speaker to selected event.
     */
    @FXML protected void handleModifyButtonAction() {
        if (addRadioButton.isSelected()) {
            addSpeakerInputChecksSuccess();
            }
        else if (removeRadioButton.isSelected()) {
            removeSpeakerHelper();
        }
    }

    private void addSpeakerInputChecksSuccess(){
        String speakerUsername = speakerNameField.getText();
        if (speakerUsername.isEmpty()){
            createAlertMessage("Missing room name input");
            return;
        }
        if (!userManager.isRegistered(speakerUsername)){
            createAlertMessage("This username does not exist.");
            return;
        }
        if (!userManager.getUserType(speakerUsername).equals(UserType.SPEAKER)){
            createAlertMessage("This user is not a speaker.");
            return;
        }
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../Events/EventInfo.fxml")));
        if (ifEventButtonClicked()) {
            try{
                loader.load();
                UUID eventID = EventHolder.getInstance().getEventID();
                if (!roomAllowsSpeaker(eventID)){
                    createAlertMessage("The room capacity is full and cannot add a speaker to the event");
                    return;
                }
                eventManager.addSpeaker(eventID, speakerUsername);
                userManager.addEventAttending(speakerUsername, eventID);
                createAlertMessage("Speaker Added!");
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        else{
            createAlertMessage("Please click an event you would like to add a speaker to first");
        }
    }

    private void removeSpeakerHelper(){
        String speakerUsername = speakerNameField.getText();
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../Events/EventInfo.fxml")));
        if (ifEventButtonClicked()) {
            try{
                loader.load();
                UUID eventID = EventHolder.getInstance().getEventID();
                if (!eventManager.getEventSpeaker(eventID).contains(speakerUsername)){
                    createAlertMessage("This username is not a speaker at this event. Please input a valid speaker username");
                    return;
                }
                eventManager.removeSpeaker(eventID, speakerUsername);
                userManager.removeEventAttending(speakerUsername, eventID);
                createAlertMessage("Speaker Removed");
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        else{
            createAlertMessage("Please click an event you would like to remove a speaker from to first");
        }
    }

    private void createAlertMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private boolean roomAllowsSpeaker(UUID eventID){
        int roomCapacity = roomManager.getRoomCapacity(eventManager.getEventRoomName(eventID));
        int attendeeSize = eventManager.getEventAttendees(eventID).size();
        int speakerSize = eventManager.getEventSpeaker(eventID).size();
        return roomCapacity > (attendeeSize + speakerSize);
    }

    @FXML private boolean ifEventButtonClicked() {
        return EventHolder.getInstance().getButtonClicked();
    }
}

