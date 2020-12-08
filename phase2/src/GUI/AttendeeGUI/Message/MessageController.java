package GUI.AttendeeGUI.Message;

import GUI.AttendeeGUI.DashboardController;
import GUI.GUIController;
import GUI.MainController;
import GUI.ManagersStorage;
import GUI.UserHolder;
import UseCases.MessageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jdk.nashorn.internal.objects.Global;
import sun.applet.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageController implements GUIController {
    private MainController mainController;
    private MessageManager messageManager;
    private SubScene subScene;
    private String username;

    @FXML private SplitPane splitPane;
    @FXML private GridPane gridPane;
    @FXML private VBox chatsContainer;
    @FXML private TextField searchField;

    public void initialize(){
        searchField.textProperty().addListener((obj, oldVal, newVal) -> {
            handleSearchField();
        });
        username = UserHolder.getInstance().getUsername();
        messageManager = ManagersStorage.getInstance().getMessageManager();
        List<String> users = messageManager.getInboxes(username);
        if(!users.isEmpty()){
            CollocutorHolder.getInstance().setUsername(users.get(0));
            loadSubScene("Chat");
        }else{
            loadSubScene("Empty");
        }
        gridPane.add(subScene, 1, 0);
        makeButtons(users);
    }

    @Override
    public void initData(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    protected void handleNewChatButtonAction(ActionEvent event) {
        String user = searchField.getText();
        List<String> allUsers = ManagersStorage.getInstance().getUserManager().getUsernameList();
        List<String> contacts = ManagersStorage.getInstance().getMessageManager().getInboxes(username);
        if(contacts.contains(user)){
            CollocutorHolder.getInstance().setUsername(user);
            loadSubScene("Chat");
        }else if(allUsers.contains(user)){
            CollocutorHolder.getInstance().setUsername(user);
            loadSubScene("Chat");
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("This user does not exist.");

            alert.showAndWait();
        }
    }

    private void handleSearchField(){
        String prefix = searchField.getText();
        List<String> allUsers = messageManager.getInboxes(username);
        List<String> searchedUsers = new ArrayList<>();
        for(String user: allUsers){
            if(user.startsWith(prefix)){
                searchedUsers.add(user);
            }
        }
        chatsContainer.getChildren().clear();
        makeButtons(searchedUsers);
    }

    private void makeButtons(List<String> users){
        for (String user: users) {
            Button button = new Button();
            button.setPrefHeight(50);
            button.setPrefWidth(100);
            button.setText(user + "\n");
            button.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    CollocutorHolder.getInstance().setUsername(user);
                    loadSubScene("Chat");
                }
            });
            chatsContainer.getChildren().add(button);
        }
    }

    private void loadSubScene(String path){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path + ".fxml"));
        Parent root = null;
        try {
            root = loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        if(subScene == null) {
            subScene = new SubScene(root, 600, 600); //TODO initData maybe
        }else{
            subScene.setRoot(root);
        }
    }
}
