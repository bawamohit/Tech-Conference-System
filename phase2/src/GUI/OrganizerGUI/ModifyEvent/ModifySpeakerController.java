package GUI.OrganizerGUI.ModifyEvent;

import Entities.UserType;
import GUI.EventHolder;
import GUI.EventInfoController;
import GUI.ManagersStorage;
import GUI.UserHolder;
import UseCases.EventManager;
import UseCases.RoomManager;
import UseCases.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Observable;
import java.util.UUID;

public class ModifySpeakerController extends Observable {
    @FXML private RadioButton removeRadioButton;
    @FXML private RadioButton addRadioButton;
    @FXML private TextField speakerNameField;
    @FXML Label label1;
    @FXML Label label2;

    private EventManager eventManager;
    private UserManager userManager;
    private RoomManager roomManager;
    private UUID eventID;

    public void initialize() {
        this.eventManager = ManagersStorage.getInstance().getEventManager();
        this.userManager = ManagersStorage.getInstance().getUserManager();
        this.roomManager = ManagersStorage.getInstance().getRoomManager();
        this.eventID = EventHolder.getInstance().getEventID();
        List<String> eventInfo = eventManager.getEventsInfo(eventID);
        label1.setText("Event Name: " + eventInfo.get(1));
        label2.setText("Speakers: " + eventInfo.get(2));
    }

    /**
     * Handles action when the add button is clicked. Adds speaker to selected event.
     */
    @FXML protected void handleModifyButtonAction() {
        if (addRadioButton.isSelected()) {
            addSpeakerInputChecksSuccess();
            setChanged();
            notifyObservers("ModifySpeaker");
        }
        else if (removeRadioButton.isSelected()) {
            removeSpeakerHelper();
            setChanged();
            notifyObservers("ModifySpeaker");
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

        if (!roomAllowsSpeaker(eventID)){
            createAlertMessage("The room capacity is full and cannot add a speaker to the event");
            return;
        }

        if(!speakerAvailable(speakerUsername, eventManager.getEventStartTime(eventID), eventManager.getEventEndTime(eventID))){
            createAlertMessage("This speaker is not available at the time of this event.");
            return;
        }
        eventManager.addSpeaker(eventID, speakerUsername);
        userManager.addEventAttending(speakerUsername, eventID);
        createAlertMessage("Speaker Added!");
    }

    private void removeSpeakerHelper(){
        String speakerUsername = speakerNameField.getText();

        if (!eventManager.getEventSpeaker(eventID).contains(speakerUsername)){
            createAlertMessage("This username is not a speaker at this event. Please input a valid speaker username");
            return;
        }
        eventManager.removeSpeaker(eventID, speakerUsername);
        userManager.removeEventAttending(speakerUsername, eventID);
        createAlertMessage("Speaker Removed");
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

    private boolean speakerAvailable(String speaker, LocalDateTime newST, LocalDateTime newET){
        List<UUID> speakers_events = userManager.getEventsAttending(speaker);
        for (UUID id: speakers_events){
            LocalDateTime existingST = eventManager.getEventStartTime(id);
            LocalDateTime existingET = eventManager.getEventEndTime(id);
            if (eventManager.scheduleOverlap(existingST, existingET, newST, newET)){
                return false;
            }
        }
        return true;
    }
    @FXML private boolean ifEventButtonClicked() {
        return EventHolder.getInstance().getButtonClicked();
    }

    public void handleBackButtonAction(ActionEvent actionEvent) {
        setChanged();
        notifyObservers("ModifyEvent");
    }
}

