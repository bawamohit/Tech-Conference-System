package GUI.SceneParents;

import GUI.*;
import GUI.DataHolders.EventHolder;
import GUI.DataHolders.ManagersStorage;
import GUI.DataHolders.UserHolder;
import UseCases.EventManager;
import UseCases.RoomManager;
import UseCases.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;
import java.util.Observable;
import java.util.UUID;

public class EventInfoController extends Observable implements GUIController {
    private EventManager eventManager;
    private UserManager userManager;
    private RoomManager roomManager;
    private String username;
    private UUID eventID;
    private List<String> eventInfo;

    @FXML Label label1;
    @FXML Label label2;
    @FXML Label label3;
    @FXML Label label4;
    @FXML Label label5;
    @FXML Label label6;
    @FXML Label label7;
    @FXML Label label8;

    @Override
    public void initData(MainController mainController) { }

    public void initialize(){
        this.eventManager = ManagersStorage.getInstance().getEventManager();
        this.userManager = ManagersStorage.getInstance().getUserManager();
        this.roomManager = ManagersStorage.getInstance().getRoomManager();
        this.username = UserHolder.getInstance().getUsername();
        this.eventID = EventHolder.getInstance().getEventID();
        this.eventInfo = eventManager.getEventsInfo(eventID);

        label1.setText("Event Name: " + eventInfo.get(1));
        label2.setText("Starts: " + eventInfo.get(3));
        label3.setText("Ends: " + eventInfo.get(4));
        label4.setText("Speakers: " + eventInfo.get(2));
        label5.setText("Organizers: " + eventInfo.get(6));
        label6.setText("Room: " + eventInfo.get(5));
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

}
