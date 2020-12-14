package GUI.SpeakerGUI.MyEvents;

import GUI.SceneParents.EventInfoController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Event Info subscene for Speaker
 */
public class EventInfoMessageController extends EventInfoController {

    @FXML Button messageButton;
    @FXML TextField textField;

    /**
     * Initializes the Event Info subscene for Speaker. Displays the clicked event info
     */
    @Override
    public void initialize() {
        super.initialize();
        textField.setVisible(false);
    }

    /**
     * Handles the action when the Message Attendees button is clicked. Displays a text field prompt for the message
     * and sends the message if valid.
     */
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
