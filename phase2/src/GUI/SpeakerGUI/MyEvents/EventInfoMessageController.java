package GUI.SpeakerGUI.MyEvents;

import GUI.SceneParents.EventInfoController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EventInfoMessageController extends EventInfoController {

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
