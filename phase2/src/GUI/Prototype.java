package GUI;

import Entities.Message;
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
    private UserGateway userGateway;
    private EventGateway eventGateway;
    private MessageGateway messageGateway;
    private String welcomeFXMLPath = "Welcome.fxml";
    private File userInfo = new File("./src/Data/userManager.json");
    private File eventInfo = new File("./src/Data/eventManager.json");
    private File messageInfo = new File("./src/Data/messageManager.json");
    private UserManager userManager;
    private EventManager eventManager;
    private MessageManager messageManager;

    @Override
    public void init() throws Exception {
        super.init();
        userGateway = new UserGateway();
        eventGateway = new EventGateway();
        messageGateway = new MessageGateway();
        userManager = userGateway.readFromFile(userInfo.getPath());
        eventManager = eventGateway.readFromFile(eventInfo.getPath());
        messageManager = messageGateway.readFromFile(messageInfo.getPath());
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(welcomeFXMLPath));

        Parent root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.setTitle("Conference");
        primaryStage.setMinHeight(834);
        primaryStage.setMinWidth(1111);
        primaryStage.setScene(scene);

        MainController mainController = loader.getController();
        mainController.initData(welcomeFXMLPath);
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