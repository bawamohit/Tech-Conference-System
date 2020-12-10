package GUI.AdminGUI;

import GUI.AttendeeGUI.EventHolder;
import GUI.DisplayEventsController;
import GUI.ManagersStorage;
import UseCases.EventManager;
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

public class DeletableEventsController extends DisplayEventsController {
    private EventManager eventManager;

    public void initialize(){
        this.eventManager = ManagersStorage.getInstance().getEventManager();
        List<UUID> deletableEventIds = eventManager.getEmptyEvents();
        List<List<String>> eventsInfo = eventManager.getAllEventsInfo(deletableEventIds);
        generateEventButtons("/GUI/AdminGUI/EventDeleteInfo", eventsInfo);

    }
}
