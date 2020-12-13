package GUI;

import GUI.DataHolders.ManagersStorage;
import Gateways.EventGateway;
import Gateways.MessageGateway;
import Gateways.UserGateway;
import UseCases.EventManager;
import UseCases.MessageManager;
import UseCases.UserManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Prototype extends Application{

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Welcome.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.setTitle("Conference");
        primaryStage.setMinHeight(834);
        primaryStage.setMinWidth(1111);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        ManagersStorage.getInstance().save();
    }

    public static void main(String[] args) {
        launch(args);
    }
}