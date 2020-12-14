import GUI.DataHolders.ManagersStorage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main GUI Application
 */
public class GUIApplication extends javafx.application.Application {

    /**
     * Loads the Welcome scene
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Welcome.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.setTitle("Conference");
        primaryStage.setMinHeight(834);
        primaryStage.setMinWidth(1111);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Stops the run
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        ManagersStorage.getInstance().save();
    }

    /**
     * Main method to run program
     */
    public static void main(String[] args) {
        launch(args);
    }
}