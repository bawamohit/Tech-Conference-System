package GUI.AttendeeGUI.Message;

import GUI.GUIController;
import GUI.MainController;
import GUI.ManagersStorage;
import GUI.UserHolder;
import UseCases.MessageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sun.applet.Main;

import java.util.ArrayList;
import java.util.List;

public class MessageController implements GUIController {
    private MainController mainController;
    private MessageManager messageManager;
    private  String username;

    @FXML private VBox chatsContainer;
    @FXML private TextField searchField;

    public void initialize(){
        username = UserHolder.getInstance().getUsername();
        messageManager = ManagersStorage.getInstance().getMessageManager();
        List<String> users = messageManager.getInboxes(username);
        for (int i = 0; i < users.size(); i++) {
            Button button = new Button();
            button.setPrefHeight(50);
            button.setPrefWidth(500);
            button.setText(users.get(i) + "\n");
            chatsContainer.getChildren().add(button);
        }
    }

    @Override
    public void initData(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    protected void handleNewChatButtonAction(ActionEvent event) {
    }
}
