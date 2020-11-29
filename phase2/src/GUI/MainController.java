package GUI;

import GUI.SignUp.SignUpController;
import UseCases.*;
import JSONGateways.UserJSONGateway;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainController {
    private String welcomeFXMLPath;
    private UserManager userManager;

    @FXML private Text prompt;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;

    public void initData(String welcomeFXMLPath, UserManager userManager) {
        this.welcomeFXMLPath = welcomeFXMLPath;
        this.userManager = userManager;
    }

    @FXML protected void handleSignInButtonAction(ActionEvent event) {
        logIn(usernameField.getText(), passwordField.getText());
    }

    @FXML protected void handleSignUpButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp/SignUp.fxml"));
        Parent signUpRoot = loader.load();
        Scene scene = new Scene(signUpRoot, 450, 300);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        SignUpController signUpController = loader.getController();
        signUpController.initData(welcomeFXMLPath, userManager);

        stage.setScene(scene);
        stage.show();
    }

    private void logIn(String username, String password){
        if (userManager.verifyLogin(username, password)) {
            prompt.setText("Logged In! But not really...");
        } else{
            prompt.setText("Log in failed.");
        }
    }
}
