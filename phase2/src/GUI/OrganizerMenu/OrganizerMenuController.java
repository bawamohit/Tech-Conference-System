package GUI.OrganizerMenu;

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

public class OrganizerMenuController implements GUIController {
    private MainController mainController;
    private String username;

    @FXML private Text prompt;

    public void initData(MainController mainController){
        this.mainController = mainController;
    }

    @FXML protected void handleChillButtonAction(ActionEvent event) {
        prompt.setText("Take a chill pill.");
    }

    @FXML public void handleLogOutButtonAction(ActionEvent event) throws IOException {
        mainController.handleLogOutButtonAction(event, true);
    }
}
