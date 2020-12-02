package GUI.AttendeeMenu;

import GUI.GUIController;
import GUI.MainController;
import UseCases.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class AttendeeMenuController implements GUIController {
    private MainController mainController;
    @FXML private Text prompt;

    public void initData(MainController mainController){
        this.mainController = mainController;
    }

    @FXML protected void handleMessageButtonAction(ActionEvent event) {
        prompt.setText("sike");
    }

    @FXML protected void handleEventButtonAction(ActionEvent actionEvent) {
        mainController.setNewScene(actionEvent, "./EventMenu/EventMenu.fxml");
    }

    @FXML public void handleLogOutButtonAction(ActionEvent event) throws IOException {
        mainController.handleLogOutButtonAction(event);
    }
}
