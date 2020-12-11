package GUI.AttendeeGUI.AvailableEvents;

import GUI.EventInfoController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class EventInfoSignUpController extends EventInfoController {
    
    @FXML public void handleSignUpButton(ActionEvent event){
        List<UUID> schedule = getUserManager().getEventsAttending(getUsername());

        if(getEventManager().isAttending(getEventID(), getUsername())) {
            alert("You're already signed up to the event");
        }else if(getEventManager().scheduleOverlap(getEventID(), schedule)){
            String startTime = getEventManager().getEventStartTime(getEventID()).toString();
            String endTime = getEventManager().getEventEndTime(getEventID()).toString();
            alert("You have an event conflict at this time slot, cancel your other events that take place during " +
                    startTime + " to " + endTime + ", to sign up for this event.");
        }else if(getEventManager().isFull(getEventID())){
            alert("Sorry, this event is fully booked. And no, you are not special enough to warrant an exception.");
        }else if(getEventManager().getEventStartTime(getEventID()).isBefore(LocalDateTime.now())){
            alert("Sorry, this event is no longer available for sign up.");
        }else {
            getEventManager().addAttendee(getEventID(), getUsername());
            getUserManager().addEventAttending(getUsername(), getEventID());
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
