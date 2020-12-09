package GUI.AttendeeGUI.MyEvents;

import GUI.AttendeeGUI.AbstractEventInfoController;
import GUI.AttendeeGUI.DashboardController;
import GUI.GUIController;
import GUI.MainController;
import GUI.ManagersStorage;
import GUI.UserHolder;
import UseCases.EventManager;
import UseCases.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EventInfoController extends AbstractEventInfoController implements GUIController {

    @FXML Label label1;
    @FXML Label label2;
    @FXML Label label3;
    @FXML Label label4;
    @FXML Label label5;
    @FXML Label label6;
    @FXML Label label7;
    @FXML Label label8;

    @Override
    public void initData(MainController mainController) {

    }

    @FXML public void handleCancelButton(ActionEvent event){
        eventManager.removeAttendee(username, eventID);
        userManager.removeEventAttending(username, eventID);
        refreshMyEventsScene();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Successfully Cancelled");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get().equals(ButtonType.CLOSE)){
            refreshMyEventsScene();
        }
    }

    @FXML private void refreshMyEventsScene(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Dashboard.fxml"));
        try {
            loader.load();
            DashboardController dashboardController;
            dashboardController = loader.getController();
            dashboardController.loadSubScene("MyEvents");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
