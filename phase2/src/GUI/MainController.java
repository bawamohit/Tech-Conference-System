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

    @FXML private Text prompt;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;

    public void initData(MainController mainController){
        this.welcomeFXMLPath = mainController.getWelcomeFXMLPath();
        this.userManager = mainController.getUserManager();
    }

    public void initData(String welcomeFXMLPath, UserManager userManager) {
        this.welcomeFXMLPath = welcomeFXMLPath;
        this.userManager = userManager;
    }

    public String getWelcomeFXMLPath(){
        return welcomeFXMLPath;
    }

    public UserManager getUserManager(){
        return userManager;
    }

    @FXML protected void handleSignInButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String pw = passwordField.getText();
        if (userManager.verifyLogin(username, pw)) {
            switch (userManager.getUserType(username)){
                case ATTENDEE:
                    setNewScene(event, "AttendeeMenu/AttendeeMenu.fxml");
                    break;

                case ORGANIZER:
                    setNewScene(event, "OrganizerMenu/OrganizerMenu.fxml");
                    break;

                case SPEAKER:
                    setNewScene(event, "SpeakerMenu/SpeakerMenu.fxml");
                    break;
            }
        } else{
            prompt.setText("Sign in failed.");
        }
    }

    @FXML protected void handleSignUpButtonAction(ActionEvent event){
        setNewScene(event, "SignUp/SignUp.fxml");
    }

    public void setNewScene(ActionEvent event, String path) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
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
    //TODO create changeScene so we can have a back button

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
