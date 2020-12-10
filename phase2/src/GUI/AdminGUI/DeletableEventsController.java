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

    @FXML private GridPane gridPane;
    @FXML private GridPane subGridPane;
    private SubScene subScene;

    public void initialize(){
        this.eventManager = ManagersStorage.getInstance().getEventManager();
        List<UUID> deletableEventIds = eventManager.getEmptyEvents();
        generateEventButtons(deletableEventIds, "");

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
            subScene = new SubScene(root, 700, 100); //TODO initData maybe
        }else{
            subScene.setRoot(root);
        }
    }
}
