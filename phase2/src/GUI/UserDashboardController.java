package GUI;

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

public class UserDashboardController implements GUIController {
    private MainController mainController;
    private String username;
    private SubScene subScene;
    private UserManager userManager;
    private EventManager eventManager;

    @FXML private GridPane gridPane;
    @FXML private Label profile;

    public void initData(String path){
        this.username = UserHolder.getInstance().getUsername();
        profile.setText(username);
        loadSubScene(path);
        gridPane.add(subScene, 1, 0);
        this.userManager = ManagersStorage.getInstance().getUserManager();
        this.eventManager = ManagersStorage.getInstance().getEventManager();
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

    @FXML public void handleLogOutButtonAction(ActionEvent event) throws IOException {
        mainController.handleLogOutButtonAction(event, true);
    }

    public void loadSubScene(String path){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path + "/" + path + ".fxml"));
        Parent root = null;
        try {
            root = loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        if(subScene == null) {
            subScene = new SubScene(root, 700, 600); //TODO initData maybe
        }else{
            subScene.setRoot(root);
        }
    }
}
