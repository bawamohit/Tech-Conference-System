package GUI.AttendeeGUI.AvailableEvents;

import GUI.GUIController;
import GUI.MainController;
import GUI.ManagersStorage;
import GUI.UserHolder;
import UseCases.EventManager;
import UseCases.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class EventInfoController implements GUIController {
    private EventManager eventManager;
    private UserManager userManager;
    private String username;
    private UUID eventID;
    private List<String> eventInfo;

    @FXML Label label1;
    @FXML Label label2;
    @FXML Label label3;
    @FXML Label label4;
    @FXML Label label5;
    @FXML Label label6;
    @FXML Label label7;
    @FXML Label label8;

    public void initialize(){
        this.eventManager = ManagersStorage.getInstance().getEventManager();
        this.userManager = ManagersStorage.getInstance().getUserManager();
        this.username = UserHolder.getInstance().getUsername();
        this.eventID = EventHolder.getInstance().getEventID();
        this.eventInfo = eventManager.getEventsInfo(eventID);

        label1.setText("Event Name: " + eventInfo.get(1));
        label2.setText("Start Time: " + eventInfo.get(3));
        label3.setText("End Time: " + eventInfo.get(4));
        label4.setText("Speakers: " + eventInfo.get(2));
        label5.setText("Organizers: " + eventInfo.get(6));
        label6.setText("Room: " + eventInfo.get(5));
        label7.setText("Maximum Capacity: " + eventInfo.get(7));
        label8.setText("Available Spots: " + eventInfo.get(8));
    }
    @Override
    public void initData(MainController mainController) {

    }
    
    @FXML public void handleSignUpButton(ActionEvent event){
        List<UUID> schedule = userManager.getEventsAttending(username);
        if(eventManager.scheduleOverlap(eventID, schedule)){
            String startTime = eventManager.getEventStartTime(eventID).toString();
            String endTime = eventManager.getEventEndTime(eventID).toString();
            alert("You have an event conflict at this time slot, cancel your other events that take place during" +
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
