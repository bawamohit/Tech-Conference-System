package GUI.EventMenu;

import GUI.GUIController;
import GUI.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class EventMenuController implements GUIController {
    public Button availableEventButton;
    public Button myEventButton;
    public Button signUpEventButton;
    public Button cancelEventButton;
    private MainController mainController;
    @FXML private Text prompt;

    @Override
    public void initData(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    protected void handleAvailEventButtonAction() {
        List<UUID> availEvents = mainController.getEventManager().getAvailableEvents(LocalDateTime.now());
        String formattedOutput = formatInfo(mainController.getEventManager().getEventsStrings(availEvents));
        if (formattedOutput.equals("")) {
            prompt.setText("There are no available events.");
        }
        else{
            prompt.setText("The available events are \n" + formattedOutput);
        }
    }

    @FXML
    protected void handleMyEventsButtonAction(ActionEvent actionEvent) {
        prompt.setText("sike");
    }

    @FXML
    public void handleSignUpEventButtonAction(ActionEvent event) {
        prompt.setText("SIKE");
    }

    @FXML
    public void handleCancelEventButtonAction(ActionEvent event) {
        prompt.setText("sike");
    }

    public void handleBackButtonAction(ActionEvent event) {
        mainController.setNewScene(event, "AttendeeMenu/AttendeeMenu.fxml");
    }

    private String formatInfo(List<String> strings){
        StringBuilder info = new StringBuilder();
        for (int i = 0; i < strings.size(); i++){
            info.append("\n").append(i).append(". ").append(strings.get(i));
        }

        return info.toString();
    }
}

