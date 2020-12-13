package GUI.AdminGUI;

import GUI.DataHolders.ManagersStorage;
import UseCases.MessageManager;
import UseCases.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class DeleteChatController {
    @FXML private TextField username1Field;
    @FXML private TextField username2Field;

    private UserManager userManager;
    private MessageManager messageManager;

    public void initialize(){
        userManager = ManagersStorage.getInstance().getUserManager();
        messageManager = ManagersStorage.getInstance().getMessageManager();
    }

    public void handleDeleteChatButtonAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (username1Field.getText().equals("") || username2Field.getText().equals("")){
            alert.setContentText("Error: you must enter two usernames.");
        }
        else if (!userManager.isRegistered(username1Field.getText()) &&
                !userManager.isRegistered(username2Field.getText())){
            alert.setContentText("Error: both usernames do not exist.");
            username1Field.clear();
            username2Field.clear();
        }
        else if (!userManager.isRegistered(username1Field.getText())){
            alert.setContentText("Error: username 1 does not exist.");
            username1Field.clear();
        }
        else if (!userManager.isRegistered(username2Field.getText())){
            alert.setContentText("Error: username 2 does not exist.");
            username2Field.clear();
        }
        else {
            messageManager.deleteMutualThread(username1Field.getText(), username2Field.getText());
            alert.setContentText("Chat successfully deleted!");
            username1Field.clear();
            username2Field.clear();
        }
        alert.showAndWait();
    }
}
