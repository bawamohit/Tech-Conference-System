package GUI.AttendeeGUI;

import GUI.GUIController;
import GUI.MainController;
import GUI.UserHolder;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController implements GUIController {
    private MainController mainController;
    private String username;
    public SubScene subScene;

    @FXML private AnchorPane anchorPane;
    @FXML private SplitPane splitPane;
    @FXML private GridPane gridPane;
    @FXML private Label profile;
    @FXML private Button availEventButton;
    @FXML private Button myEventButton;
    @FXML private Button messageButton;

    public void initialize(){
        this.username = UserHolder.getInstance().getUsername();
        profile.setText(username);
        loadSubScene("AvailableEvents");
        gridPane.add(subScene, 1, 0);
    }

    public void initData(MainController mainController){
        this.mainController = mainController;
    }

    @FXML
    protected void handleMessageButtonAction(ActionEvent event) {
        loadSubScene("Message");
    }

    @FXML protected void handleAvailEventButtonAction(ActionEvent event) {
        loadSubScene("AvailableEvents");
    }

    @FXML protected void handleMyEventButtonAction(ActionEvent event) {
        loadSubScene("MyEvents");
    }

    @FXML public void handleLogOutButtonAction(ActionEvent event) throws IOException {
        mainController.handleLogOutButtonAction(event, true);
    }

    public void loadSubScene(String path){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path + "/" + path + ".fxml"));
        Parent root = null;
        try {
            root = loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        if(subScene == null) {
            subScene = new SubScene(root, 700, 600); //TODO initData maybe
        }else{
            subScene.setRoot(root);
        }
    }
}
