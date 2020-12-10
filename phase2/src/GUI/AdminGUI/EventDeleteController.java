package GUI.AdminGUI;

import GUI.AttendeeGUI.AbstractEventInfoController;
import GUI.GUIController;
import GUI.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class EventDeleteController extends AbstractEventInfoController implements GUIController {

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
    
    @FXML public void handleDeleteButton(ActionEvent event){
        List<UUID> schedule = userManager.getEventsAttending(username);
        if(eventManager.scheduleOverlap(eventID, schedule)){
            String startTime = eventManager.getEventStartTime(eventID).toString();
            String endTime = eventManager.getEventEndTime(eventID).toString();
            alert("You have an event conflict at this time slot, cancel your other events that take place during " +
                    startTime + " to " + endTime + ", to sign up for this event.");
        }else if(eventManager.isAttending(eventID, username)){
            alert("You're already signed up to the event");
        }else if(eventManager.isFull(eventID)){
            alert("Sorry, this event is fully booked. And no, you are not special enough to warrant an exception.");
        }else if(eventManager.getEventStartTime(eventID).isBefore(LocalDateTime.now())){
            alert("Sorry, this event is no longer available for sign up.");
        }else {
            eventManager.addAttendee(eventID, username);
            userManager.addEventAttending(username, eventID);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Signed Up!");

            alert.showAndWait();
        }
    }

    private void alert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sign Up Failed");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
