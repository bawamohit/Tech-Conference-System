package GUI.SignUp;

import Entities.UserType;
import GUI.MainController;
import UseCases.UserManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {
    private String welcomeFXMLPath;
    private UserManager userManager;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private TextField passwordFieldRe;
    @FXML private TextField nameField;
    @FXML private Text prompt;
    @FXML private ToggleGroup group;
    @FXML private RadioButton radioButton;
    @FXML private RadioButton radioButton2;

    public void initData(String welcomeScene, UserManager userManager){
        this.welcomeFXMLPath = welcomeScene;
        this.userManager = userManager;
    }

    @FXML protected void handleSignUpButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String passwordRe = passwordFieldRe.getText();
        String name = nameField.getText();
        if(username.isEmpty() || password.isEmpty() || passwordRe.isEmpty() || name.isEmpty()){
            prompt.setText("Please complete the fields.");
        } else if(userManager.isRegistered(username)){
            prompt.setText("This username is already registered.");
        } else if(!password.equals(passwordRe)){
            prompt.setText("Your passwords do not match.");
        } else{
            UserType userType = null;
            if(radioButton.isSelected()){
                userType = UserType.ATTENDEE;
            }else if(radioButton2.isSelected()){
                userType = UserType.ORGANIZER;
            }
            userManager.registerUser(userType, name,
                    username, password);
            prompt.setText("Signed Up!");
        }
    }

    @FXML protected void handleCheckAvailableButtonAction(ActionEvent event) {
        if(userManager.isRegistered(usernameField.getText())) {
            prompt.setText("This username is already registered.");
        }else{
            prompt.setText("This username is available!");
        }
    }

    @FXML protected void handleBackButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../" + welcomeFXMLPath));
        Parent root = loader.load();
        Scene scene = new Scene(root, 300, 300);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        MainController mainController = loader.getController();
        mainController.initData(welcomeFXMLPath, userManager);

        stage.show();
    }
}
