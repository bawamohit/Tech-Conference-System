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

public class AdminDashboardController extends UserDashboardController {

    @FXML public Button cancelButton;

    public void initialize(){
        super.initialize("");
    }

    public void handleDeleteEventButtonAction(ActionEvent actionEvent) {
    }

    public void handleDeleteChatButtonAction(ActionEvent actionEvent) {
    }

    public void handleSelfDestructButtonAction(ActionEvent actionEvent) {
    }

    public void handleCancelButtonAction(ActionEvent actionEvent) {
    }
}
