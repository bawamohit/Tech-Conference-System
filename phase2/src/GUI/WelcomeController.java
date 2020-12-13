package GUI;

import GUI.DataHolders.ManagersStorage;
import GUI.DataHolders.UserHolder;
import GUI.SignUp.SignUpController;
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

public class WelcomeController{
    private UserManager userManager;
    private MessageManager messageManager;
    private String username;

    @FXML private Text prompt;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;

    public void initialize(){
        this.userManager = ManagersStorage.getInstance().getUserManager();
        this.messageManager = ManagersStorage.getInstance().getMessageManager();
        this.username = null;
    }

    public UserManager getUserManager(){
        return userManager;
    }

    public MessageManager getMessageManager() { return messageManager; }

    public String getUsername(){
        return username;
    }

    @FXML protected void handleSignInButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String pw = passwordField.getText();
        if (userManager.verifyLogin(username, pw)) {
            this.username = username;
            UserHolder.getInstance().setUsername(username);
            ((Node)event.getSource()).getScene().getWindow().hide();
            FXMLLoader loader;
            switch (userManager.getUserType(username)){
                case ATTENDEE:
                    loader = new FXMLLoader(getClass().getResource("AttendeeGUI/AttendeeDashboard.fxml"));
                    setNewScene(event, loader);
                    break;

                case ORGANIZER:
                    loader = new FXMLLoader(getClass().getResource("OrganizerGUI/OrganizerDashboard.fxml"));
                    setNewScene(event, loader);
                    break;

                case SPEAKER:
                    loader = new FXMLLoader(getClass().getResource("SpeakerGUI/SpeakerDashboard.fxml"));
                    setNewScene(event, loader);
                    break;

                case ADMIN:
                    loader = new FXMLLoader(getClass().getResource("AdminGUI/AdminDashboard.fxml"));
                    setNewScene(event, loader);
                    break;
            }
        } else{
            prompt.setText("Sign in failed.");
        }
    }

    @FXML protected void handleSignUpButtonAction(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp/SignUp.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        SignUpController signUpController = loader.getController();
        signUpController.initData(this);

        stage.setScene(scene);
    }

    public void setNewScene(ActionEvent event, FXMLLoader loader) {
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }
}
