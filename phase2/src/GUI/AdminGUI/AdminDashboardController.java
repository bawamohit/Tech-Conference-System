package GUI.AdminGUI;

import GUI.SceneParents.UserDashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.Observable;

/**
 * The dashboard for admins
 */
public class AdminDashboardController extends UserDashboardController {

    /**
     * Initializes the Admin dashboard, and displays deletable events
     */
    public void initialize(){
        super.initData("/GUI/AdminGUI/DisplayDeletableEvents");
        observeDisplayEvents();
    }

    /**
     * Handles action when the Delete Events button is clicked. Display deletable events.
     */
    public void handleDeleteEventButtonAction(ActionEvent actionEvent) {
        loadSubScene("/GUI/AdminGUI/DisplayDeletableEvents");
        observeDisplayEvents();
    }

    /**
     * Handles action when the Message button is clicked. Display Message scene.
     */
    public void handleMessageButtonAction(ActionEvent actionEvent) {
        loadSubScene("/GUI/Message/Message");
    }

    /**
     * Handles action when the Delete Chat button is clicked. Display Delete Chat scene.
     */
    public void handleDeleteChatButtonAction(ActionEvent actionEvent) {
        loadSubScene("/GUI/AdminGUI/DeleteChat");
    }

    /**
     * Updates the scene after an action.
     */
    @Override
    public void update(Observable o, Object arg) {
        loadSubScene("/GUI/AdminGUI/DisplayDeletableEvents");
        observeDisplayEvents();
    }
}
