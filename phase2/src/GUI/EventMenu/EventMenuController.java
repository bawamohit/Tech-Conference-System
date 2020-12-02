package GUI.EventMenu;

import GUI.GUIController;
import GUI.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;

public class EventMenuController implements GUIController {
    private MainController mainController;
    @FXML private Text prompt;

    @Override
    public void initData(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    protected void handleAvailEventButtonAction(ActionEvent event) {
        prompt.setText("sike");
    }

    @FXML
    protected void handleMyEventsButtonAction(ActionEvent actionEvent) {
        prompt.setText("sike");
    }

    @FXML
    public void handleSignUpEventButtonAction(ActionEvent event) {
        prompt.setText("SIKE");
    }

    @FXML
    public void handleCancelEventButtonAction(ActionEvent event) {
        prompt.setText("sike");
    }
}

