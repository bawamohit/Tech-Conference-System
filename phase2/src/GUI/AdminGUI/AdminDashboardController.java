package GUI.AdminGUI;

import GUI.*;
import UseCases.EventManager;
import UseCases.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Observable;

public class AdminDashboardController extends UserDashboardController {

    public void initialize(){
        super.initData("/GUI/AdminGUI/DeletableEvents");
        observeDisplayEvents();
    }

    public void handleDeleteEventButtonAction(ActionEvent actionEvent) {
        loadSubScene("/GUI/AdminGUI/DeletableEvents");
    }

    public void handleDeleteChatButtonAction(ActionEvent actionEvent) {
    }

    public void handleSelfDestructButtonAction(ActionEvent actionEvent) {
    }

    public void handleCancelButtonAction(ActionEvent actionEvent) {
    }

    @Override
    public void update(Observable o, Object arg) {
        loadSubScene("/GUI/AdminGUI/DeletableEvents");
    }

    public void observeDisplayEvents(){
        DisplayEventsController controller = getLoader().getController();
        controller.addObserver(this);
    }
}
