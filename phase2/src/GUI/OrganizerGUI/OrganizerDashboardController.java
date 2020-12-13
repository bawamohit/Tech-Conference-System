package GUI.OrganizerGUI;

import GUI.*;
import GUI.SceneParents.UserDashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.Observable;

/**
 * The dashboard for organizers
 */
public class OrganizerDashboardController extends UserDashboardController{

    /**
     * Initializes the Dashboard scene.
     */
    @FXML public void initialize(){
        super.initData("/GUI/OrganizerGUI/ModifyEvent/DisplayModifiableEvents");
        observeDisplayEvents();
    }

    /**
     * Handles action of when the create event button is clicked. Loads CreateEvent subscene
     */
    @FXML protected void handleCreateEventButtonAction(ActionEvent event) {
        loadSubScene("/GUI/OrganizerGUI/CreateEvent/CreateEvent");
    }

    /**
     * Handles action of when the modify event button is clicked. Loads ModifyEvent subscene
     */
    @FXML protected void handleModifyEventsButtonAction(ActionEvent event) {
        loadSubScene("/GUI/OrganizerGUI/ModifyEvent/DisplayModifiableEvents");
        observeDisplayEvents();
    }

    /**
     * Handles action when the message button is clicked. Loads Message subscene
     */
    @FXML
    protected void handleMessageButtonAction(ActionEvent event) {
        loadSubScene("/GUI/Message/Message");
    }

    /**
     * Handles action when the announcements button is clicked. Loads Announcements subscene
     */
    @FXML
    protected void handleAnnouncementsButtonAction(ActionEvent event) {
        loadSubScene("/GUI/OrganizerGUI/Announcements/Announcements");
    }

    /**
     * Handles action when the create account button is clicked. Loads CreateEvent subscene
     */
    @FXML protected void handleCreateAccountsButtonAction(ActionEvent event){
        loadSubScene("/GUI/OrganizerGUI/CreateAccounts/CreateAccounts2");
    }

    /**
     * Handles action when the create room button is clicked. Loads CreateRoom subscene
     */
    @FXML protected void handleCreateRoomButtonAction(ActionEvent event){
        loadSubScene("/GUI/OrganizerGUI/CreateRoom/CreateRoom");
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg.equals("ModifySpeaker")){
            loadSubScene("/GUI/OrganizerGUI/ModifyEvent/ModifySpeaker");
            observeModifySpeaker();
        }
        else if (arg.equals("EditEvent")){
            loadSubScene("/GUI/OrganizerGUI/ModifyEvent/EditEvent");
            observeEditEvent();
        }
        else if (arg.equals("ModifyEvent")){
            loadSubScene("/GUI/OrganizerGUI/ModifyEvent/DisplayModifiableEvents");
            observeDisplayEvents();
        }
    }
}
