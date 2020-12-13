package GUI.OrganizerGUI.CreateAccounts;

import Entities.UserType;
import GUI.MainController;
import GUI.DataHolders.ManagersStorage;
import UseCases.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.io.IOException;

public class CreateAccountsController {
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
    @FXML private RadioButton radioButton3;
    @FXML private RadioButton radioButton4;

    /**
     * Initializes the Create Accounts scene.
     */
    public void initialize(){
        this.userManager = ManagersStorage.getInstance().getUserManager();
    }

    public void initData(MainController mainController){
        this.mainController = mainController;
    }


    /**
     * Handles action when the signup button is clicked. Signs up the account.
     */
    @FXML protected void handleSignUpButtonAction() {
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
            else if(radioButton3.isSelected()){
                userType = UserType.ADMIN;
            }
            else if(radioButton4.isSelected()){
                userType = UserType.SPEAKER;
            }
            userManager.registerUser(userType, name, username, password);
            prompt.setText("Signed Up!");
        }
    }

    /**
     * Handles action when the check availability button is clicked. Checks if username is available.
     */
    @FXML protected void handleCheckAvailableButtonAction() {
        if(userManager.isRegistered(usernameField.getText())) {
            prompt.setText("This username is already registered.");
        }else if(usernameField.getText().isEmpty()){
            prompt.setText("Please enter an username.");
        }else{
            prompt.setText("This username is available!");
        }
    }
}
