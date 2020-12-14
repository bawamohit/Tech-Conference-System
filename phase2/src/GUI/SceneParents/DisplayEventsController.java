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

/**
 * This class is an abstract class for displaying events
 */
public abstract class DisplayEventsController extends Observable implements Observer {
    private EventManager eventManager;
    private UserManager userManager;
    private String username;
    private SubScene subScene;
    private List<List<String>> eventsInfo;

    @FXML private GridPane gridPane;
    @FXML private GridPane subGridPane;
    FXMLLoader loader;

    /**
     * Initializes the display events scene.
     */
    public void initialize(){
        this.eventManager = ManagersStorage.getInstance().getEventManager();
        this.userManager = ManagersStorage.getInstance().getUserManager();
        this.username = UserHolder.getInstance().getUsername();
        List<UUID> myEventIDs = userManager.getEventsAttending(username);
        this.eventsInfo = eventManager.getAllEventsInfo(myEventIDs);
    }

    /**
     * Generates event buttons too the given scene path, when given the events information
     *
     * @param path scene path
     * @param eventsInfo information of events user wants to display
     */
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
                        observeEventInfoController();
                    }else if (path.equals("EventInfoCancel")){
                        observeEventInfoController();
                    }else if (path.equals("EventInfoModify")){
                        observeEventInfoController();
                    }
                }
            });
            subGridPane.add(button, i, j);
            if(i < 3){ i++; }else{ i = 0; j++;}
        }
        loadSubScene("/GUI/Empty");

        gridPane.add(subScene, 0, 1);
    }

    /**
     * loads the subscene associated with the given path.
     *
     * @param path scene path
     */
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

    /**
     * This class is both an observer and observable. This method updates this class by notifying its observers whenever
     * the class that this class observes changes
     *
     * @param o the class that this class observes
     * @param arg an arbitrary argument
     */
    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }

    /**
     * Adds this class to the list of observers of the EventInfoController currently associated with loader.
     */
    public void observeEventInfoController(){
        EventInfoController controller = loader.getController();
        controller.addObserver(this);
    }

    /**
     * Implements a getter, for the information of all events
     *
     * @return events information
     */
    public List<List<String>> getEventsInfo() {
        return eventsInfo;
    }
}
