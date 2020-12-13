package GUI.SceneParents;

import GUI.DataHolders.EventHolder;
import GUI.DataHolders.ManagersStorage;
import GUI.DataHolders.UserHolder;
import GUI.WelcomeController;
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
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

public abstract class DisplayEventsController extends Observable implements Observer {
    private EventManager eventManager;
    private UserManager userManager;
    private String username;
    private SubScene subScene;
    private List<List<String>> eventsInfo;

    @FXML private GridPane gridPane;
    @FXML private GridPane subGridPane;
    FXMLLoader loader;

    public void initialize(){
        this.eventManager = ManagersStorage.getInstance().getEventManager();
        this.userManager = ManagersStorage.getInstance().getUserManager();
        this.username = UserHolder.getInstance().getUsername();
        List<UUID> myEventIDs = userManager.getEventsAttending(username);
        this.eventsInfo = eventManager.getAllEventsInfo(myEventIDs);
    }

    public void generateEventButtons(String path, List<List<String>> eventsInfo){
        int i = 0, j = 0;
        for(List<String> eventInfo: eventsInfo) {
            Button button = new Button(eventInfo.get(1) + "\nStarts: " + eventInfo.get(2) + "\nEnds:  " + eventInfo.get(3));
            button.setPrefHeight(75);
            button.setPrefWidth(225);
            button.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    EventHolder.getInstance().setEvent(UUID.fromString(eventInfo.get(0)));
                    loadSubScene(path);
                    if (path.equals("/GUI/AdminGUI/EventInfoDelete")){
                        observeEventInfoDelete();
                    }else if (path.equals("EventInfoCancel")){
                        observeEventInfoCancel();
                    }else if (path.equals("EventInfoModify")){
                        observeEventInfoModify();
                    }
                }
            });
            subGridPane.add(button, i, j);
            if(i < 3){ i++; }else{ i = 0; j++;}
        }
        loadSubScene("/GUI/Empty");

        gridPane.add(subScene, 0, 1);
    }

    private void loadSubScene(String path){
        loader = new FXMLLoader(getClass().getResource(path + ".fxml"));
        Parent root = null;
        try {
            root = loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        if(subScene == null) {
            subScene = new SubScene(root, 900, 125); //TODO initData maybe
        }else{
            subScene.setRoot(root);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }

    public void observeEventInfoDelete(){
        EventInfoController controller = loader.getController();
        controller.addObserver(this);
    }

    public void observeEventInfoCancel(){
        EventInfoController controller = loader.getController();
        controller.addObserver(this);
    }

    public void observeEventInfoModify(){
        EventInfoController controller = loader.getController();
        controller.addObserver(this);
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public List<List<String>> getEventsInfo() {
        return eventsInfo;
    }
}
