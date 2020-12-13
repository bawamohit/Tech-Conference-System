package GUI.AdminGUI;

import GUI.SceneParents.UserDashboardController;
import javafx.event.ActionEvent;

import java.util.Observable;

public class AdminDashboardController extends UserDashboardController {

    public void initialize(){
        super.initData("/GUI/AdminGUI/DisplayDeletableEvents");
        observeDisplayEvents();
    }

    public void handleDeleteEventButtonAction(ActionEvent actionEvent) {
        loadSubScene("/GUI/AdminGUI/DisplayDeletableEvents");
        observeDisplayEvents();
    }

    public void handleDeleteChatButtonAction(ActionEvent actionEvent) {
        loadSubScene("/GUI/AdminGUI/DeleteChat");
    }

    @Override
    public void update(Observable o, Object arg) {
        loadSubScene("/GUI/AdminGUI/DisplayDeletableEvents");
        observeDisplayEvents();
    }
}
