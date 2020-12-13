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

/**
 * The subscene that displays event information
 */
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

    /**
     * Initializes the event information scene.
     */
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
        label5.setText("Organizer: " + eventInfo.get(5));
        label6.setText("Room: " + eventInfo.get(6));
        label7.setText("Maximum Capacity: " + eventInfo.get(7));
        label8.setText("Available Spots: " + eventInfo.get(8));
    }

    /**
     * Implements Getter, getUsername, for username.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Implements Getter, getUserManager, for userManager.
     *
     * @return userManager
     */
    public UserManager getUserManager() {
        return userManager;
    }

    /**
     * Implements Getter, getEventManager, for eventManager.
     *
     * @return userManager
     */
    public EventManager getEventManager() {
        return eventManager;
    }

    /**
     * Implements Getter, getEventID, for eventID.
     *
     * @return id of the event
     */
    public UUID getEventID() {
        return eventID;
    }

    /**
     * Implements Getter, getRoomManager, for roomManager.
     *
     * @return roomManager
     */
    public RoomManager getRoomManager() {
        return roomManager;
    }

    /**
     * Implements Getter, getMessageManager, for messageManager.
     *
     * @return messageManager
     */
    public MessageManager getMessageManager() { return messageManager; }

    /**
     * Handles action of when the detailed speakers button is clicked. Shows enlarged detailed speakers list
     */
    @FXML protected void handleDetailedSpeakersButtonAction() {
        String message = "Speakers for this event: " + eventInfo.get(4);
        if(eventInfo.get(4).isEmpty()){message = "This event has no speakers.";}
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
