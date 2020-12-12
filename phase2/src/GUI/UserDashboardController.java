package GUI;

import GUI.OrganizerGUI.ModifyEvent.EditEventController;
import GUI.OrganizerGUI.ModifyEvent.ModifySpeakerController;
import UseCases.EventManager;
import UseCases.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public abstract class UserDashboardController implements GUIController, Observer {
    private MainController mainController;
    private String username;
    private SubScene subScene;
    private UserManager userManager;
    private EventManager eventManager;
    private FXMLLoader loader;

    @FXML private GridPane gridPane;
    @FXML private Label profile;

    public void initData(String path){
        this.userManager = ManagersStorage.getInstance().getUserManager();
        this.eventManager = ManagersStorage.getInstance().getEventManager();
        this.username = UserHolder.getInstance().getUsername();
        profile.setText(userManager.getName(username));
        loadSubScene(path);
        gridPane.add(subScene, 1, 0);
    }

    public void initData(MainController mainController){
        this.mainController = mainController;
    }

    public MainController getMainController() {
        return mainController;
    }

    public String getUsername() {
        return username;
    }

    public SubScene getSubScene() {
        return subScene;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public Label getProfile() {
        return profile;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    /**
     * Handles action when the logout button is clicked. Reverts back to welcome scene.
     */
    @FXML public void handleLogOutButtonAction(ActionEvent event) throws IOException {
        mainController.handleLogOutButtonAction(event, true);
    }

    /**
     * Loads the subscene of the given path
     *
     * @param path path of subscene fxml file
     */
    public void loadSubScene(String path){
        loader = new FXMLLoader(getClass().getResource(path + ".fxml"));

        Parent root = null;
        try {
            root = loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        if(subScene == null) {
            subScene = new SubScene(root, 900, 700); //TODO initData maybe
        }else{
            subScene.setRoot(root);
        }
    }

    @Override
    public abstract void update(Observable o, Object arg);

    public void observeDisplayEvents(){
        DisplayEventsController controller = loader.getController();
        controller.addObserver(this);
    }

    public void observeModifySpeaker(){
        ModifySpeakerController controller = loader.getController();
        controller.addObserver(this);
    }

    public void observeEditEvent(){
        EditEventController controller = loader.getController();
        controller.addObserver(this);
    }
}
