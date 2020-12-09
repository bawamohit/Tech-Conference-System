package GUI.AttendeeGUI;

import GUI.AttendeeGUI.AvailableEvents.EventHolder;
import GUI.ManagersStorage;
import GUI.UserHolder;
import UseCases.EventManager;
import UseCases.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;
import java.util.UUID;

public class AbstractEventInfoController {
    protected EventManager eventManager;
    protected UserManager userManager;
    protected String username;
    protected UUID eventID;
    protected List<String> eventInfo;

    @FXML
    Label label1;
    @FXML Label label2;
    @FXML Label label3;
    @FXML Label label4;
    @FXML Label label5;
    @FXML Label label6;
    @FXML Label label7;
    @FXML Label label8;

    public void initialize(){
        this.eventManager = ManagersStorage.getInstance().getEventManager();
        this.userManager = ManagersStorage.getInstance().getUserManager();
        this.username = UserHolder.getInstance().getUsername();
        this.eventID = EventHolder.getInstance().getEventID();
        this.eventInfo = eventManager.getEventsInfo(eventID);

        label1.setText("Event Name: " + eventInfo.get(1));
        label2.setText("Start Time: " + eventInfo.get(3));
        label3.setText("End Time: " + eventInfo.get(4));
        label4.setText("Speakers: " + eventInfo.get(2));
        label5.setText("Organizers: " + eventInfo.get(6));
        label6.setText("Room: " + eventInfo.get(5));
        label7.setText("Maximum Capacity: " + eventInfo.get(7));
        label8.setText("Available Spots: " + eventInfo.get(8));
    }
}
