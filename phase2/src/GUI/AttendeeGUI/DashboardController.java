package GUI.AttendeeGUI;

import GUI.AttendeeGUI.MyEvents.EventInfoController;
import GUI.GUIController;
import GUI.MainController;
import GUI.ManagersStorage;
import GUI.UserHolder;
import UseCases.EventManager;
import UseCases.UserManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class DashboardController implements GUIController {
    public Button cancelbutton;
    private MainController mainController;
    private String username;
    private SubScene subScene;
    private UserManager userManager;
    private EventManager eventManager;

    @FXML private AnchorPane anchorPane;
    @FXML private SplitPane splitPane;
    @FXML private GridPane gridPane;
    @FXML private Label profile;
    @FXML private Button availEventButton;
    @FXML private Button myEventButton;
    @FXML private Button messageButton;

    public void initialize(){
        this.username = UserHolder.getInstance().getUsername();
        profile.setText(username);
        loadSubScene("AvailableEvents");
        cancelbutton.setVisible(false);
        gridPane.add(subScene, 1, 0);
        this.userManager = ManagersStorage.getInstance().getUserManager();
        this.eventManager = ManagersStorage.getInstance().getEventManager();
    }

    public void initData(MainController mainController){
        this.mainController = mainController;
    }

    @FXML
    protected void handleMessageButtonAction(ActionEvent event) {
        loadSubScene("Message");
        cancelbutton.setVisible(false);
    }

    @FXML protected void handleAvailEventButtonAction(ActionEvent event) {
        loadSubScene("AvailableEvents");
        cancelbutton.setVisible(false);
    }

    @FXML protected void handleMyEventButtonAction(ActionEvent event) {
        loadSubScene("MyEvents");
        cancelbutton.setVisible(true);
    }

    @FXML public void handleLogOutButtonAction(ActionEvent event) throws IOException {
        mainController.handleLogOutButtonAction(event, true);
    }

    @FXML public void handleCancelButtonAction(ActionEvent event){
        FXMLLoader loader = new FXMLLoader((getClass().getResource("MyEvents/EventInfo.fxml")));
        try{
            loader.load();
            UUID eventID = ((EventInfoController)loader.getController()).eventID;
            eventManager.removeAttendee(username, eventID);
            userManager.removeEventAttending(username, eventID);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Successfully Cancelled");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()){
                loadSubScene("MyEvents");
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
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
