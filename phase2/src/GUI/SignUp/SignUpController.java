package GUI.SignUp;

import Entities.UserType;
import GUI.GUIController;
import GUI.MainController;
import GUI.ManagersStorage;
import UseCases.UserManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController{
    private MainController mainController;
    private UserManager userManager;

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private TextField passwordFieldRe;
    @FXML private TextField nameField;
    @FXML private Text prompt;
    @FXML private ToggleGroup group;
    @FXML private RadioButton radioButton;
    @FXML private RadioButton radioButton2;

    public void initialize(){
        this.userManager = ManagersStorage.getInstance().getUserManager();
    }

    public void initData(MainController mainController){
        this.mainController = mainController;
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
            userManager.registerUser(userType, name, username, password);
            prompt.setText("Signed Up!");
        }
    }

    @FXML protected void handleCheckAvailableButtonAction(ActionEvent event) {
        if(userManager.isRegistered(usernameField.getText())) {
            prompt.setText("This username is already registered.");
        }else if(usernameField.getText().isEmpty()){
            prompt.setText("Please enter an username.");
        }else{
            prompt.setText("This username is available!");
        }
    }

    @FXML protected void handleBackButtonAction(ActionEvent event) throws IOException {
        mainController.handleLogOutButtonAction(event);
    }
}
