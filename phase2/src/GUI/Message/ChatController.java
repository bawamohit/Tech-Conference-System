package GUI.Message;

import GUI.DataHolders.CollocutorHolder;
import GUI.WelcomeController;
import GUI.DataHolders.ManagersStorage;
import GUI.DataHolders.UserHolder;
import UseCases.MessageManager;
import UseCases.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * The subscene for message chats
 */
public class ChatController {
    private MessageManager messageManager;
    private UserManager userManager;
    private String username;
    private String collocutor;
    private int index;

    @FXML private ScrollPane scrollPane;
    @FXML private TextField textField;
    @FXML private VBox chatBox;

    /**
     * Initializes the chat scene.
     */
    public void initialize(){
        this.messageManager = ManagersStorage.getInstance().getMessageManager();
        this.userManager = ManagersStorage.getInstance().getUserManager();
        this.username = UserHolder.getInstance().getUsername();
        this.collocutor = CollocutorHolder.getInstance().getUsername();
        if(messageManager.getInboxes(username).isEmpty()) return;
        List<List<String>> messages = messageManager.getInboxStringGUI(username, collocutor);
        for(List<String> messageInfo: messages) {
            displayMessage(messageInfo);
        }
    }

    /**
     * Handles action when the send button is clicked. Sends the message.
     */
    @FXML private void handleSend(ActionEvent event){
        String content = textField.getText();
        if(!content.isEmpty()){
            messageManager.sendMessage(username, collocutor, content);
            int size = messageManager.getInboxStringGUI(username, collocutor).size();
            List<String> messageInfo = messageManager.getInboxStringGUI(username, collocutor).get(size - 1);
            displayMessage(messageInfo);
            textField.clear();
        }
    }

    private void displayMessage(List<String> messageInfo){
        String name = userManager.getName(messageInfo.get(0));
        Label label = new Label( name + "     " + messageInfo.get(1));
        label.setStyle("-fx-font-size: 14px;-fx-font-weight: bold; -fx-text-fill: #ffffff;");
        HBox hBox1 = new HBox();
        hBox1.getChildren().add(label);

        Label content = new Label(messageInfo.get(2));
        content.setId(Integer.toString(index));
        content.setWrapText(true);
        content.setMaxWidth(335);
        content.setStyle("-fx-font-size: 14px;-fx-font-weight: normal; -fx-text-fill: #ffffff; -fx-padding: 5px;" +
                "-fx-background-color: rgba(108, 145, 191, 0.6); -fx-background-radius: 10 10 10 10;");
        HBox hBox2 = new HBox();
        hBox2.getChildren().add(content);
        hBox2.setStyle("-fx-wrap-text: true; " +
                "-fx-text-align: right;");

        if(messageInfo.get(0).equals(username)){
            if(messageInfo.get(2).length() <= 50) {
                content.setAlignment(Pos.BASELINE_RIGHT);
            }
            hBox1.setAlignment(Pos.BASELINE_RIGHT);
            hBox2.setAlignment(Pos.BASELINE_RIGHT);
        }else{
            hBox1.setAlignment(Pos.BASELINE_LEFT);
            hBox2.setAlignment(Pos.BASELINE_LEFT);
        }

        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.getChildren().add(hBox1);
        vBox.getChildren().add(hBox2);
        chatBox.getChildren().add(vBox);
        chatBox.setSpacing(10);
        index++;
    }
}
