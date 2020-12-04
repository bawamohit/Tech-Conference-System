package GUI.AttendeeMenu.Message;

import GUI.GUIController;
import GUI.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class MessageController implements GUIController {
    private MainController mainController;
    private String username;

    @FXML private ScrollPane container;
    @FXML private VBox chatsContainer;

    public void initialize() {

        for (int i = 1; i <= 10; i++) {
            Button button = new Button();
            button.setPrefWidth(500);
            chatsContainer.getChildren().add(button);
        }
    }

    @Override
    public void initData(MainController mainController) {
        this.mainController = mainController;
    }
}
