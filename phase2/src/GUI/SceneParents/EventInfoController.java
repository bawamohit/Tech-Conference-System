package GUI.SceneParents;

import GUI.*;
import GUI.DataHolders.EventHolder;
import GUI.DataHolders.ManagersStorage;
import GUI.DataHolders.UserHolder;
import UseCases.EventManager;
import UseCases.MessageManager;
import UseCases.RoomManager;
import UseCases.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.List;
import java.util.Observable;
import java.util.UUID;

public class EventInfoController extends Observable {
    private EventManager eventManager;
    private UserManager userManager;
    private RoomManager roomManager;
    private MessageManager messageManager;
    private String username;
    private UUID eventID;
    private List<String> eventInfo;

    @FXML Label label1;
    @FXML Label label2;
    @FXML Label label3;
    @FXML Button speakers;
    @FXML Label label4;
    @FXML Label label5;
    @FXML Label label6;
    @FXML Label label7;
    @FXML Label label8;

    public void initialize(){
        this.eventManager = ManagersStorage.getInstance().getEventManager();
        this.userManager = ManagersStorage.getInstance().getUserManager();
        this.roomManager = ManagersStorage.getInstance().getRoomManager();
        this.messageManager = ManagersStorage.getInstance().getMessageManager();
        this.username = UserHolder.getInstance().getUsername();
        this.eventID = EventHolder.getInstance().getEventID();
        this.eventInfo = eventManager.getEventsInfo(eventID);

        label1.setText("Event Name: " + eventInfo.get(1));
        label2.setText("Starts: " + eventInfo.get(2));
        label3.setText("Ends: " + eventInfo.get(3));
        speakers.setText("Speakers: ");
        label4.setText(eventInfo.get(4));
        label5.setText("Organizers: " + eventInfo.get(5));
        label6.setText("Room: " + eventInfo.get(6));
        label7.setText("Maximum Capacity: " + eventInfo.get(7));
        label8.setText("Available Spots: " + eventInfo.get(8));
    }

    public String getUsername() {
        return username;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public UUID getEventID() {
        return eventID;
    }

    public RoomManager getRoomManager() {
        return roomManager;
    }

    public MessageManager getMessageManager() { return messageManager; }

    @FXML protected void handleDetailedSpeakersButtonAction(ActionEvent event) {
        String message = "Speakers for this event: " + eventInfo.get(4);
        if(eventInfo.get(4).isEmpty()){message = "This event has no speakers.";}
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
