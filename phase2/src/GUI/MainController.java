package GUI;

import GUI.SignUp.SignUpController;
import UseCases.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController implements GUIController{
    private String welcomeFXMLPath;
    private UserManager userManager;
    private EventManager eventManager;
    private MessageManager messageManager;
    private String username;

    @FXML private Text prompt;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;

    public void initData(MainController mainController){
        this.welcomeFXMLPath = mainController.getWelcomeFXMLPath();
        this.userManager = mainController.getUserManager();
        this.messageManager = mainController.getMessageManager();
        this.username = null;
    }

    public void initData(String welcomeFXMLPath) {
        this.welcomeFXMLPath = welcomeFXMLPath;
        this.userManager = ManagersStorage.getInstance().getUserManager();
        this.eventManager = ManagersStorage.getInstance().getEventManager();
        this.messageManager = ManagersStorage.getInstance().getMessageManager();
//        Font.loadFont(getClass().getResourceAsStream("/Resources/MontserratBlack.ttf"), 14);
//        Font.loadFont(getClass().getResourceAsStream("/Resources/MontserratBold.ttf"), 14);
//        Font.loadFont(getClass().getResourceAsStream("/Resources/MontserratExtrabold.ttf"), 14);
//        Font.loadFont(getClass().getResourceAsStream("/Resources/MontserratMed.ttf"), 14);
//        Font.loadFont(getClass().getResourceAsStream("/Resources/MontserratReg.ttf"), 14);
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
                    loader = new FXMLLoader(getClass().getResource("AttendeeGUI/Dashboard.fxml"));
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

                case ADMIN:
                    loader = new FXMLLoader(getClass().getResource("AdminGUI/Dashboard.fxml"));
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
        //TODO new FXMLLoader(getClass().getResource("")); //relative or absolute?
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        GUIController controller = loader.getController();
        controller.initData(this);
        stage.show();
    }

    public void handleLogOutButtonAction(ActionEvent event, boolean closeStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(welcomeFXMLPath));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        if(closeStage) stage.hide();

        GUIController controller = loader.getController();
        controller.initData(this);
        UserHolder.getInstance().setUsername(null);

        stage.setScene(scene);
        stage.show();
    }
}
