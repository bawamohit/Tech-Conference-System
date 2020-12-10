package GUI.OrganizerGUI;

import GUI.*;
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


public class OrganizerDashboardController implements GUIController {
    public Button addEventbutton;
    public Button removeEventbutton;
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
        loadSubScene("Message");
        gridPane.add(subScene, 1, 0);
        profile.setText(username);
        this.userManager = ManagersStorage.getInstance().getUserManager();
        this.eventManager = ManagersStorage.getInstance().getEventManager();
        this.roomManager = ManagersStorage.getInstance().getRoomManager();
        addEventbutton.setVisible(true);
        removeEventbutton.setVisible(true);
    }

    public void initData(MainController mainController){
        this.mainController = mainController;
    }

    @FXML protected void handleCreateEventButtonAction(ActionEvent event) {
        loadSubScene("CreateEvent");
        addEventbutton.setVisible(true);
        removeEventbutton.setVisible(true);
    }

    @FXML protected void handleModifyEventsButtonAction(ActionEvent event) {
        loadSubScene("Events");
        addEventbutton.setVisible(true);
        removeEventbutton.setVisible(true);
    }

    @FXML
    protected void handleMessageButtonAction(ActionEvent event) {
        loadSubScene("Message");
        removeEventbutton.setVisible(false);
        addEventbutton.setVisible(false);
    }

    @FXML protected void handleCreateAccountsButtonAction(ActionEvent event){
        loadSubScene("CreateAccounts");
        removeEventbutton.setVisible(false);
        addEventbutton.setVisible(false);
    }

    @FXML protected void handleCreateRoomButtonAction(ActionEvent event){
        loadSubScene("CreateRoom");
        removeEventbutton.setVisible(false);
        addEventbutton.setVisible(false);
    }

    @FXML public void handleLogOutButtonAction(ActionEvent event) throws IOException {
        mainController.handleLogOutButtonAction(event, true);
        removeEventbutton.setVisible(false);
        addEventbutton.setVisible(false);
    }

    @FXML public void handleRemoveEventButtonAction(ActionEvent event){
        FXMLLoader loader = new FXMLLoader((getClass().getResource("AvailableEvents/EventInfo.fxml")));
        if (ifEventButtonClicked()) {
            try{
                loader.load();
                UUID eventID = EventHolder.getInstance().getEventID();
                for (String username: eventManager.getEventAttendees(eventID)){
                    userManager.removeEventAttending(username, eventID);
                }
                if (eventManager.removeEvent(eventID) && roomManager.removeEventFromSchedule(eventID)){
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
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please click an event you would like to remove first");
            alert.showAndWait();
        }
    }

    @FXML private boolean ifEventButtonClicked() {
        return EventHolder.getInstance().getButtonClicked();
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
