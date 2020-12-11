package GUI.Message;

import GUI.GUIController;
import GUI.MainController;
import GUI.ManagersStorage;
import GUI.UserHolder;
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
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class ChatController implements GUIController {
    private MessageManager messageManager;
    private UserManager userManager;
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
        this.userManager = ManagersStorage.getInstance().getUserManager();
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
        HBox hBox1 = new HBox();
        hBox1.getChildren().add(label);

        Label content = new Label(messageInfo.get(2));
        content.setId(Integer.toString(index));
        content.setWrapText(true);
        content.setPrefWidth(335);
        HBox hBox2 = new HBox();
        hBox2.getChildren().add(content);

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
        vBox.getChildren().add(hBox1);
        vBox.getChildren().add(hBox2);
        chatBox.getChildren().add(vBox);
        chatBox.setSpacing(10);
        index++;
    }
}
