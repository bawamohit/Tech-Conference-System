package GUI.Message;

import GUI.GUIController;
import GUI.MainController;
import GUI.ManagersStorage;
import GUI.UserHolder;
import UseCases.MessageManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

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
            for (String user: users){
                makeButton(user);
            }
            CollocutorHolder.getInstance().setUsername(users.get(0));
            loadSubScene("Chat");
        }else{
            loadSubScene("Empty");
        }
        gridPane.add(subScene, 1, 0);
    }


    @Override
    public void initData(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    protected void handleNewChatButtonAction(ActionEvent event) {
        deleteEmptyChatButtons();
        String user = searchField.getText();
        List<String> allUsers = ManagersStorage.getInstance().getUserManager().getUsernameList();
        List<String> contacts = ManagersStorage.getInstance().getMessageManager().getInboxes(username);
        if(contacts.contains(user)){
            CollocutorHolder.getInstance().setUsername(user);
            loadSubScene("Chat");

        }else if(allUsers.contains(user)){
            if (!buttonExists(user)){
                makeButton(user);
            }
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
    }

    /**
     * Boolean function checking if chat button with user already exists on the side chat bar.
     *
     * @param user user the logged in user wants to message
     * @return if chat button already exists
     */
    private boolean buttonExists(String user){
        List<Button> buttonList = new ArrayList<>();
        for (Node node: chatsContainer.getChildren()){
            if (node instanceof Button) {
                buttonList.add((Button) node);
            }
        }
        for (Button button: buttonList){
            if ((user + "\n").equals(button.getText())){
                return true;
            }
        }
        return false;
    }

    /**
     * This function deletes chat buttons on the side bar if the chat is empty
     */
    private void deleteEmptyChatButtons(){
        List<String> contacts = ManagersStorage.getInstance().getMessageManager().getInboxes(username);
        List<Button> buttonList = new ArrayList<>();
        for (Node node: chatsContainer.getChildren()){
            if (node instanceof Button) { buttonList.add((Button) node); }
        }
        for (Button button: buttonList){
            String user = button.getText().replaceAll("\n", "");
            if (!contacts.contains(user)){
                chatsContainer.getChildren().remove(buttonList.indexOf(button));
            }
        }
    }

    private void makeButton(String user){
        Button button = new Button();
        button.setPrefHeight(50);
        button.setPrefWidth(110);
        button.setText(user + "\n");
        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                CollocutorHolder.getInstance().setUsername(user);
                loadSubScene("Chat");
            }
        });
        chatsContainer.getChildren().add(0, button);
    }


    private void loadSubScene(String path){
        FXMLLoader loader;
        if(path.equals("Empty")){
            loader = new FXMLLoader(getClass().getResource("../../Empty.fxml"));
        }else{
            loader = new FXMLLoader(getClass().getResource(path + ".fxml"));
        }
        Parent root = null;
        try {
            root = loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        if(subScene == null) {
            subScene = new SubScene(root, 550, 600); //TODO initData maybe
        }else{
            subScene.setRoot(root);
        }
    }
}
