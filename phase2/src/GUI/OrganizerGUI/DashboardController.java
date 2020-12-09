package GUI.OrganizerGUI;

import GUI.OrganizerGUI.AvailableEvents.EventInfoController;
import GUI.GUIController;
import GUI.MainController;
import GUI.ManagersStorage;
import GUI.UserHolder;
import UseCases.EventManager;
import UseCases.RoomManager;
import UseCases.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


public class DashboardController implements GUIController {
    private MainController mainController;
    private String username;
    private SubScene subScene;
    @FXML private GridPane gridPane;
    @FXML private Label profile;
    private UserManager userManager;
    private EventManager eventManager;
    private RoomManager roomManager;


    public void initialize(){
        this.username = UserHolder.getInstance().getUsername();
        loadSubScene("AvailableEvents");
        gridPane.add(subScene, 1, 0);
        profile.setText(username);
        this.userManager = ManagersStorage.getInstance().getUserManager();
        this.eventManager = ManagersStorage.getInstance().getEventManager();
        this.roomManager = ManagersStorage.getInstance().getRoomManager();
    }

    public void initData(MainController mainController){
        this.mainController = mainController;
    }

    @FXML protected void handleAvailEventButtonAction(ActionEvent event) {
        loadSubScene("AvailableEvents");
    }

    @FXML
    protected void handleMessageButtonAction(ActionEvent event) {
        loadSubScene("Message");
    }

    @FXML protected void handleCreateSpeakerButtonAction(ActionEvent event){
        //loadSubScene("Speakers");
    }

    @FXML protected void handleCreateRoomButtonAction(ActionEvent event){
        //loadSubScene("Rooms");
    }
    @FXML public void handleLogOutButtonAction(ActionEvent event) throws IOException {
        mainController.handleLogOutButtonAction(event, true);
    }

    @FXML public void handleAddEventButtonAction(){

    }
    @FXML public void handleRemoveEventButtonAction(ActionEvent event){
        FXMLLoader loader = new FXMLLoader((getClass().getResource("AvailableEvents/EventInfo.fxml")));
        try{
            loader.load();
            UUID eventID = ((EventInfoController)loader.getController()).eventID;
            if(eventManager.removeEvent(eventID) && roomManager.removeEventFromSchedule(eventID)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Successfully Removed");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()){
                    loadSubScene("AvailableEvents");
                }
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
