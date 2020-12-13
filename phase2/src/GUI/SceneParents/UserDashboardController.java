package GUI.SceneParents;

import GUI.GUIController;
import GUI.WelcomeController;
import GUI.DataHolders.ManagersStorage;
import GUI.OrganizerGUI.ModifyEvent.EditEventController;
import GUI.OrganizerGUI.ModifyEvent.ModifySpeakerController;
import GUI.DataHolders.UserHolder;
import UseCases.EventManager;
import UseCases.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public abstract class UserDashboardController implements GUIController, Observer {
    private WelcomeController welcomeController;
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

    public void initData(WelcomeController welcomeController){
        this.welcomeController = welcomeController;
    }

    public WelcomeController getMainController() {
        return welcomeController;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Welcome.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.hide();

        stage.setScene(scene);
        stage.show();
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
