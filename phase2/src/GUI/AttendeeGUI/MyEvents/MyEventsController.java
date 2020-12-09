package GUI.AttendeeGUI.MyEvents;

import Entities.Event;
import GUI.AttendeeGUI.EventHolder;
import GUI.GUIController;
import GUI.MainController;
import GUI.ManagersStorage;
import GUI.UserHolder;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class MyEventsController implements GUIController {
    private MainController mainController;
    private String username;
    private EventManager eventManager;
    private UserManager userManager;
    @FXML private GridPane gridPane;
    @FXML private SubScene subScene;
    @FXML private GridPane subGridPane;

    public void initialize(){
        gridPane.getChildren().removeAll();
        this.eventManager = ManagersStorage.getInstance().getEventManager();
        this.userManager = ManagersStorage.getInstance().getUserManager();
        this.username = UserHolder.getInstance().getUsername();
        List<UUID> myEventIDs = userManager.getEventsAttending(username);
        List<List<String>> eventsInfo = eventManager.getAllEventsInfo(myEventIDs); //duplicate
        int i = 0, j = 0;
        for(List<String> eventInfo: eventsInfo) {
            Button button = new Button(eventInfo.get(1) + "\nStarts: " + eventInfo.get(3) + "\nEnds:  " + eventInfo.get(4));
            button.setPrefHeight(75);
            button.setPrefWidth(200);
            button.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    EventHolder.getInstance().setEvent(UUID.fromString(eventInfo.get(0)));
                    GUI.AttendeeGUI.EventHolder.getInstance().setButtonClicked(true);
                    loadSubScene("EventInfo");
                }
            });
            subGridPane.add(button, i, j);
            if(i < 3){ i++; }else{ i = 0; j++;}
        }
        loadSubScene("Empty");
        gridPane.add(subScene, 0, 1);
    }


    protected void loadSubScene(String path){ //duplicate
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
            subScene = new SubScene(root, 700, 100); //TODO initData maybe
        }else{
            subScene.setRoot(root);
        }
    }


    @Override
    public void initData(MainController mainController) {
        this.mainController = mainController;
    }
}
