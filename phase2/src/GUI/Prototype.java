package GUI;

import GUIControllers.MainController;
import JSONGateways.UserJSONGateway;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Prototype extends Application implements GUI{
    File userInfo = new File("./phase2/src/Data/userJSONManager.json");

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Prototype.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root, 300, 300);

        primaryStage.setTitle("Conference");
        primaryStage.setScene(scene);

        MainController mainController = loader.getController();
        mainController.initData(userInfo);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}