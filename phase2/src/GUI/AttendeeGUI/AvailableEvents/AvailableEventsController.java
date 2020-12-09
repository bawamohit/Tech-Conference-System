package GUI.AttendeeGUI.AvailableEvents;

import GUI.AttendeeGUI.Message.CollocutorHolder;
import GUI.GUIController;
import GUI.MainController;
import GUI.ManagersStorage;
import GUI.UserHolder;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class AvailableEventsController implements GUIController {
    private EventManager eventManager;
    private String user;
    private SubScene subScene;

    @FXML private GridPane gridPane;
    @FXML private GridPane subGridPane;

    public void initialize(){
        this.eventManager = ManagersStorage.getInstance().getEventManager();
        this.user = UserHolder.getInstance().getUsername();
        LocalDateTime currTime = LocalDateTime.now();
        List<UUID> availableEventIDs = eventManager.getAvailableEvents(currTime);
        List<List<String>> eventsInfo = eventManager.getAllEventsInfo(availableEventIDs);

        int i = 0, j = 0;
        for(List<String> eventInfo: eventsInfo) {
            Button button = new Button(eventInfo.get(1) + "\nStarts: " + eventInfo.get(3) + "\nEnds:  " + eventInfo.get(4));
            button.setPrefHeight(75);
            button.setPrefWidth(200);
            button.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    EventHolder.getInstance().setEvent(UUID.fromString(eventInfo.get(0)));
                    loadSubScene("EventInfo");
                }
            });
            subGridPane.add(button, i, j);
            if(i < 3){ i++; }else{ i = 0; j++;}
        }
        loadSubScene("Empty");
        gridPane.add(subScene, 0, 1);
    }

    private void loadSubScene(String path){
        FXMLLoader loader;
        if(path.equals("Empty")){
            loader = new FXMLLoader(getClass().getResource("../../Empty.fxml"));
        }else{
            loader = new FXMLLoader(getClass().getResource(path + ".fxml"));
        }
        Parent root = null;
        try {
            root = loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        if(subScene == null) {
            subScene = new SubScene(root, 700, 115); //TODO initData maybe
        }else{
            subScene.setRoot(root);
        }
    }

    @Override
    public void initData(MainController mainController) {

    }
}
