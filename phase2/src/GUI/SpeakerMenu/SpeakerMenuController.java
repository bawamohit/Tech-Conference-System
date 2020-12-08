package GUI.SpeakerMenu;

import GUI.GUIController;
import GUI.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;


public class SpeakerMenuController implements GUIController {
    private MainController mainController;
    private String username;

    @FXML private Text prompt;

    public void initData(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML protected void handleLogOutButtonAction(ActionEvent event) throws IOException {
        mainController.handleLogOutButtonAction(event, true);
    }

    @FXML protected void handleRelaxButtonAction(ActionEvent event) {
        prompt.setText("Relax buddy.");
    }
}