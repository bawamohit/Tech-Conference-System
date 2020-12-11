package GUI.AttendeeGUI.MyEvents;

import GUI.*;
import UseCases.EventManager;
import UseCases.UserManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class DisplayMyEventsController extends DisplayEventsController implements GUIController {
    private MainController mainController;
    private String username;
    private EventManager eventManager;
    private UserManager userManager;

    public void initialize(){
        EventHolder.getInstance().setButtonClicked(false);
        this.eventManager = ManagersStorage.getInstance().getEventManager();
        this.userManager = ManagersStorage.getInstance().getUserManager();
        this.username = UserHolder.getInstance().getUsername();
        List<UUID> myEventIDs = userManager.getEventsAttending(username);
        List<List<String>> eventsInfo = eventManager.getAllEventsInfo(myEventIDs); //duplicate
        generateEventButtons("EventInfoCancel", eventsInfo);
    }
}
