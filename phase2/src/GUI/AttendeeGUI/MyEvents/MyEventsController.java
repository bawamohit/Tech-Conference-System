package GUI.AttendeeGUI.MyEvents;

import Entities.Event;
import GUI.GUIController;
import GUI.MainController;
import GUI.ManagersStorage;
import GUI.UserHolder;
import UseCases.EventManager;
import UseCases.UserManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.UUID;

public class MyEventsController implements GUIController {
    private MainController mainController;
    private String username;
    private EventManager eventManager;
    private UserManager userManager;
    @FXML
    private GridPane gridPane;

    public void initialize(){
        username = UserHolder.getInstance().getUsername();
        eventManager = ManagersStorage.getInstance().getEventManager();
        userManager = ManagersStorage.getInstance().getUserManager();
        List<UUID> eventsAttending = userManager.getEventsAttending(username);
        int i = 0;
        int j = 0;
        for(UUID eventid: eventsAttending){
            Button button = new Button(eventManager.getEventName(eventid));
            button.setPrefHeight(50);
            button.setPrefWidth(100);
            gridPane.add(button, i, j);
            if(i < 6){
                i++;
            }
            else{
                i = 0; j++;
            }
        }
    }



    @Override
    public void initData(MainController mainController) {
        this.mainController = mainController;
    }
}
