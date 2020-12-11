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
    @FXML public Button removeEventButton;
    @FXML public Button modifySpeakerButton;
    @FXML public Button rescheduleEventButton;
    private MainController mainController;
    private String username;
    private SubScene subScene;
    @FXML private GridPane gridPane;
    @FXML private Label profile;
    private UserManager userManager;
    private EventManager eventManager;
    private RoomManager roomManager;


    /**
     * Initializes the Dashboard scene.
     */
    @FXML public void initialize(){
        this.username = UserHolder.getInstance().getUsername();
        loadSubScene("Message");
        gridPane.add(subScene, 1, 0);
        profile.setText(username);
        this.userManager = ManagersStorage.getInstance().getUserManager();
        this.eventManager = ManagersStorage.getInstance().getEventManager();
        this.roomManager = ManagersStorage.getInstance().getRoomManager();
        removeEventButton.setVisible(false);
        modifySpeakerButton.setVisible(false);
        rescheduleEventButton.setVisible(false);
    }

    //TODO idk what to write for this javadoc
    public void initData(MainController mainController){
        this.mainController = mainController;
    }

    /**
     * Handles action of when the create event button is clicked. Loads CreateEvent subscene
     */
    @FXML protected void handleCreateEventButtonAction(ActionEvent event) {
        loadSubScene("CreateEvent");
        removeEventButton.setVisible(false);
        modifySpeakerButton.setVisible(false);
        rescheduleEventButton.setVisible(false);
    }

    /**
     * Handles action of when the modify event button is clicked. Loads ModifyEvent subscene
     */
    @FXML protected void handleModifyEventsButtonAction(ActionEvent event) {
        loadSubScene("Events");
        removeEventButton.setVisible(true);
        modifySpeakerButton.setVisible(true);
        rescheduleEventButton.setVisible(true);
    }

    /**
     * Handles action when the message button is clicked. Loads Message subscene
     */
    @FXML
    protected void handleMessageButtonAction(ActionEvent event) {
        loadSubScene("Message");
        removeEventButton.setVisible(false);
        modifySpeakerButton.setVisible(false);
        rescheduleEventButton.setVisible(false);
    }

    /**
     * Handles action when the create account button is clicked. Loads CreateEvent subscene
     */
    @FXML protected void handleCreateAccountsButtonAction(ActionEvent event){
        loadSubScene("CreateAccounts");
        removeEventButton.setVisible(false);
        modifySpeakerButton.setVisible(false);
        rescheduleEventButton.setVisible(false);
    }

    /**
     * Handles action when the create room button is clicked. Loads CreateRoom subscene
     */
    @FXML protected void handleCreateRoomButtonAction(ActionEvent event){
        loadSubScene("CreateRoom");
        removeEventButton.setVisible(false);
        modifySpeakerButton.setVisible(false);
        rescheduleEventButton.setVisible(false);
    }

    /**
     * Handles action when the logout button is clicked. Reverts back to welcome scene.
     */
    @FXML public void handleLogOutButtonAction(ActionEvent event) throws IOException {
        mainController.handleLogOutButtonAction(event, true);
        removeEventButton.setVisible(false);
        modifySpeakerButton.setVisible(false);
        rescheduleEventButton.setVisible(false);
    }

    /**
     * Handles action when the reschedule event button is clicked.
     */
    @FXML public void handleEditEventButtonAction(ActionEvent event){
        loadSubScene("EditEvent");
    }

    /**
     * Handles action when the add speaker button is clicked
     */
    @FXML public void handleModifySpeakerButtonAction(){
        loadSubScene("ModifySpeaker");
    }

    /**
     * Handles action when the remove event button is clicked.
     */
    @FXML public void handleRemoveEventButtonAction(ActionEvent event){
        FXMLLoader loader = new FXMLLoader((getClass().getResource("Events/EventInfo.fxml")));
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
                        loadSubScene("Events");
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


    /**
     * Loads the subscene of the given path
     *
     * @param path path of subscene fxml file
     */
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
