package GUIControllers;

import UseCases.*;
import JSONGateways.UserJSONGateway;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainController {
    private UserManager userManager;
    private UserJSONGateway userJSONGateway;
    @FXML private Text sike;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;

    public void initData(File userInfo) {
        userJSONGateway = new UserJSONGateway();
        try {
            userManager = userJSONGateway.readFromFile(userInfo.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handleSignInButtonAction(ActionEvent event) {
        logIn(usernameField.getText(), passwordField.getText());
    }

    @FXML protected void handleSingUpButtonAction(ActionEvent event) {
        sike.setText("SIKE");
    }

    private void logIn(String username, String password){
        if (userManager.verifyLogin(username, password)) {
            sike.setText("Logged In! But not really...");
        } else{
            sike.setText("Log in failed.");
        }
    }
}
