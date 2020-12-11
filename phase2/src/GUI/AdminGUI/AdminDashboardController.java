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
