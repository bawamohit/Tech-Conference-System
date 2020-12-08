package GUI.AttendeeGUI.AvailableEvents;

import GUI.GUIController;
import GUI.MainController;
import GUI.ManagersStorage;
import GUI.UserHolder;
import UseCases.EventManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class AvailableEventsController implements GUIController {
    private EventManager eventManager;
    private String user;

    @FXML private GridPane gridPane;

    public void initialize(){
        this.eventManager = ManagersStorage.getInstance().getEventManager();
        this.user = UserHolder.getInstance().getUsername();
        LocalDateTime currTime = LocalDateTime.now();
        List<UUID> availableEventIDs = eventManager.getAvailableEvents(currTime);
        List<List<String>> eventsInfo = eventManager.getAllEventsInfo(availableEventIDs);

        int i = 0, j = 0;
        for(int p = 0; p < 15; p++) {
            Button button = new Button("event " + p);
            button.setPrefHeight(50);
            button.setPrefWidth(100);
            gridPane.add(button, i, j);
            if(i < 6){ i++; }else{ i = 0; j++;}
        }
    }

    @Override
    public void initData(MainController mainController) {

    }
}
