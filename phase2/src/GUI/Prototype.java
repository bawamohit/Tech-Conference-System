package GUI;

import Gateways.EventGateway;
import Gateways.UserGateway;
import UseCases.EventManager;
import UseCases.UserManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Prototype extends Application{
    private UserGateway userGateway;
    private EventGateway eventGateway;
    private String welcomeFXMLPath = "Welcome.fxml";
    private File userInfo = new File("./src/Data/userManager.json");
    private File eventInfo = new File("./src/Data/eventManager.json");
    private UserManager userManager;
    private EventManager eventManager;

    @Override
    public void init() throws Exception {
        super.init();
        userGateway = new UserGateway();
        eventGateway = new EventGateway();
        userManager = userGateway.readFromFile(userInfo.getPath());
        eventManager = eventGateway.readFromFile(eventInfo.getPath());
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(welcomeFXMLPath));

        Parent root = loader.load();
        Scene scene = new Scene(root, 300, 300);

        primaryStage.setTitle("Conference");
        primaryStage.setScene(scene);

        MainController mainController = loader.getController();
        mainController.initData(welcomeFXMLPath, userManager, eventManager);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        try {
            userGateway.saveToFile(userInfo.getPath(), userManager);
            eventGateway.saveToFile(eventInfo.getPath(), eventManager);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}