package GUI.AttendeeGUI.AvailableEvents;

import GUI.GUIController;
import GUI.MainController;
import GUI.ManagersStorage;
import UseCases.EventManager;
import UseCases.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;
import java.util.UUID;

public class EventInfoController implements GUIController {
    private EventManager eventManager;
    private UserManager userManager;
    private UUID event;
    private List<String> eventInfo;

    @FXML Label label1;
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
        this.event = EventHolder.getInstance().getEventID();
        this.eventInfo = eventManager.getEventsInfo(event);

        label1.setText("Event Name: " + eventInfo.get(1));
        label2.setText("Start Time: " + eventInfo.get(3));
        label3.setText("End Time: " + eventInfo.get(4));
        label4.setText("Speakers: " + eventInfo.get(2));
        label5.setText("Organizers: " + eventInfo.get(6));
        label6.setText("Room: " + eventInfo.get(5));
        label7.setText("Maximum Capacity: " + eventInfo.get(7));
        label8.setText("Available Spots: " + eventInfo.get(8));
    }
    @Override
    public void initData(MainController mainController) {

    }
}
