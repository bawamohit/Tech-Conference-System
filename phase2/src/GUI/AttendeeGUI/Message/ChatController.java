package GUI.AttendeeGUI.Message;

import GUI.GUIController;
import GUI.MainController;
import GUI.ManagersStorage;
import GUI.UserHolder;
import UseCases.MessageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class ChatController implements GUIController {
    private MessageManager messageManager;
    private String username;
    private String collocutor;
    private int index;

    @FXML private ScrollPane scrollPane;
    @FXML private TextField textField;
    @FXML private VBox chatBox;

    @Override
    public void initData(MainController mainController) {
    }

    public void initialize(){
        this.messageManager = ManagersStorage.getInstance().getMessageManager();
        this.username = UserHolder.getInstance().getUsername();
        this.collocutor = CollocutorHolder.getInstance().getUsername();
        if(messageManager.getInboxes(username).isEmpty()) return;
        List<List<String>> messages = messageManager.getInboxStringGUI(username, collocutor);
        for(List<String> messageInfo: messages) {
            displayMessage(messageInfo);
        }
    }

    @FXML private void handleSend(ActionEvent event){
        String content = textField.getText();
        messageManager.sendMessage(username, collocutor, content);
        int size = messageManager.getInboxStringGUI(username, collocutor).size();
        List<String> messageInfo = messageManager.getInboxStringGUI(username, collocutor).get(size - 1);
        displayMessage(messageInfo);
        textField.clear();
    }

    private void displayMessage(List<String> messageInfo){
        Label label=new Label(messageInfo.get(0) + "     " + messageInfo.get(1) + "\n" + messageInfo.get(2));
        label.setId(Integer.toString(index));
        HBox hBox=new HBox();
        hBox.getChildren().add(label);
        if(messageInfo.get(0).equals(username)){
            label.setTextAlignment(TextAlignment.RIGHT);
            hBox.setAlignment(Pos.BASELINE_RIGHT);
        }else{
            label.setTextAlignment(TextAlignment.LEFT);
            hBox.setAlignment(Pos.BASELINE_LEFT);
        }
        chatBox.getChildren().add(hBox);
        chatBox.setSpacing(10);
        index++;
    }
}
