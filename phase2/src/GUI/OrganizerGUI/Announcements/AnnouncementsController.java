package GUI.OrganizerGUI.Announcements;

import Entities.UserType;
import GUI.DataHolders.ManagersStorage;
import GUI.DataHolders.UserHolder;
import UseCases.MessageManager;
import UseCases.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;

/**
 * The subscene where organizers can make announcements
 */
public class AnnouncementsController {
    @FXML private TextArea textArea;
    @FXML private CheckBox adminCheckBox;
    @FXML private CheckBox organizerCheckBox;
    @FXML private CheckBox speakerCheckBox;
    @FXML private CheckBox attendeeCheckBox;


    /**
     * Handles action when the send button is clicked. Sends the announcement.
     */
    @FXML
    protected void handleSendButtonAction(ActionEvent event){
        UserManager userManager = ManagersStorage.getInstance().getUserManager();
        MessageManager messageManager = ManagersStorage.getInstance().getMessageManager();
        String user = UserHolder.getInstance().getUsername();
        String content = textArea.getText();

        List<String> userList = new ArrayList<>();

        if(adminCheckBox.isSelected()){
            userList.addAll(userManager.getUserList(UserType.ADMIN));
        }
        if(organizerCheckBox.isSelected()){
            userList.addAll(userManager.getUserList(UserType.ORGANIZER));
        }
        if(speakerCheckBox.isSelected()){
            userList.addAll(userManager.getUserList(UserType.SPEAKER));
        }
        if(attendeeCheckBox.isSelected()){
            userList.addAll(userManager.getUserList(UserType.ATTENDEE));
        }

        messageManager.messageAll(user, userList, content);
        textArea.clear();
        //TODO prompts？
    }
}
