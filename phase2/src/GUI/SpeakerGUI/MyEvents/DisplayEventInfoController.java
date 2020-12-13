package GUI.SpeakerGUI.MyEvents;

import GUI.EventInfoController;
import GUI.GUIController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.util.List;

public class DisplayEventInfoController extends GUI.EventInfoController implements GUIController {

    @FXML Button messageButton;
    @FXML TextField textField;

    @Override
    public void initialize() {
        super.initialize();
        textField.setVisible(false);
    }

    @FXML
    protected void handleMessageButton(ActionEvent actionEvent) {
        if (!textField.isVisible()) {
            textField.setVisible(true);
        } else {
            String content = textField.getText();
            if (!content.equals("")) {
                getMessageManager().messageAll(getUsername(), getEventManager().getEventAttendees(getEventID()), content);
            }
        }
    }
}
