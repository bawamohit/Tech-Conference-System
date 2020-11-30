package GUI;

import JSONGateways.UserJSONGateway;
import UseCases.UserManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Prototype extends Application{
    private UserJSONGateway userJSONGateway;
    private String welcomeFXMLPath = "Welcome.fxml";
    private File userInfo = new File("./phase2/src/Data/userJSONManager.json");
    private UserManager userManager;

    @Override
    public void init() throws Exception {
        super.init();
        userJSONGateway = new UserJSONGateway();
        userManager = userJSONGateway.readFromFile(userInfo.getPath());
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(welcomeFXMLPath));

        Parent root = loader.load();
        Scene scene = new Scene(root, 300, 300);

        primaryStage.setTitle("Conference");
        primaryStage.setScene(scene);

        MainController mainController = loader.getController();
        mainController.initData(welcomeFXMLPath, userManager);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        try {
            userJSONGateway.saveToFile(userInfo.getPath(), userManager);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}