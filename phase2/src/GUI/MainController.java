package GUI;

import UseCases.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController implements GUIController{
    private String welcomeFXMLPath;
    private UserManager userManager;
    private EventManager eventManager;
    private MessageManager messageManager;

    @FXML private Text prompt;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;

    public void initData(MainController mainController){
        this.welcomeFXMLPath = mainController.getWelcomeFXMLPath();
        this.userManager = mainController.getUserManager();
        this.messageManager = mainController.getMessageManager();
    }

    public void initData(String welcomeFXMLPath, UserManager userManager, EventManager eventManager,
                         MessageManager messageManager) {
        this.welcomeFXMLPath = welcomeFXMLPath;
        this.userManager = userManager;
        this.eventManager = eventManager;
        this.messageManager = messageManager;
    }

    public String getWelcomeFXMLPath(){
        return welcomeFXMLPath;
    }

    public UserManager getUserManager(){
        return userManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public MessageManager getMessageManager() { return messageManager; }

    @FXML protected void handleSignInButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String pw = passwordField.getText();
        if (userManager.verifyLogin(username, pw)) {
            FXMLLoader loader = null;
            switch (userManager.getUserType(username)){
                case ATTENDEE:
                    loader = new FXMLLoader(getClass().getResource("AttendeeMenu/AttendeeMenu.fxml"));
                    setNewScene(event, loader);
                    break;

                case ORGANIZER:
                    loader = new FXMLLoader(getClass().getResource("OrganizerMenu/OrganizerMenu.fxml"));
                    setNewScene(event, loader);
                    break;

                case SPEAKER:
                    loader = new FXMLLoader(getClass().getResource("SpeakerMenu/SpeakerMenu.fxml"));
                    setNewScene(event, loader);
                    break;
            }
        } else{
            prompt.setText("Sign in failed.");
        }
    }

    @FXML protected void handleSignUpButtonAction(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp/SignUp.fxml"));
        setNewScene(event, loader);
    }

    public void setNewScene(ActionEvent event, FXMLLoader loader) {
        //TODO new FXMLLoader(getClass().getResource("")); //relative or absolute?
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        GUIController controller = loader.getController();
        controller.initData(this);

        stage.setScene(scene);
        stage.show();
    }

    public void handleLogOutButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(welcomeFXMLPath));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        GUIController controller = loader.getController();
        controller.initData(this);

        stage.setScene(scene);
        stage.show();
    }
}
