package GUI.OrganizerGUI.CreateEvent;

import Entities.UserType;
import GUI.DataHolders.ManagersStorage;
import GUI.DataHolders.UserHolder;
import UseCases.EventManager;
import UseCases.RoomManager;
import UseCases.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * The subscene where organizers can create events
 */
public class CreateEventController {
    private UserManager userManager;
    private RoomManager roomManager;
    private EventManager eventManager;
    private String username;

    @FXML private TextField eventNameField;
    @FXML private TextField startTimeField;
    @FXML private TextField endTimeField;
    @FXML private TextField eventCapacityField;
    @FXML private TextField roomNameField;
    @FXML private TextField speakersField;

    /**
     * Initializes the create event scene
     */
    public void initialize(){
        this.userManager = ManagersStorage.getInstance().getUserManager();
        this.roomManager  = ManagersStorage.getInstance().getRoomManager();
        this.eventManager = ManagersStorage.getInstance().getEventManager();
        this.username = UserHolder.getInstance().getUsername();
    }

    /**
     * Handles action of when the create event button is clicked.
     */
    @FXML  protected void handleCreateEventButtonAction(ActionEvent event) {
        String eventName = eventNameField.getText();
        String startTimeString = startTimeField.getText();
        String endTimeString = endTimeField.getText();
        String eventCapacityString = eventCapacityField.getText();
        String roomName = roomNameField.getText();
        List<String> speakers = Arrays.asList(speakersField.getText().split(","));

        if(eventName.isEmpty() || startTimeString.isEmpty() || endTimeString.isEmpty() ||
                eventCapacityString.isEmpty() || roomName.isEmpty()){
            createErrorAlert("Incomplete fields.");
            return;
        }else if(!validTime(startTimeString) || !validTime(endTimeString)){
            createErrorAlert("Invalid time format");
            return;
        }else if(!eventCapacityString.matches("\\d+")){
            createErrorAlert("Event capacity must be a natural number.");
            return;
        }
        for(String speaker: speakers){
            if(userManager.isNotUserType(speaker, UserType.SPEAKER)){
                createErrorAlert(speaker + " is not a speaker.");
                return;
            }
        }
        LocalDateTime startTime = getTime(startTimeString);
        LocalDateTime endTime = getTime(endTimeString);
        int eventCapacity = Integer.parseInt(eventCapacityString);

        if(startTime.isAfter(endTime)){
            createErrorAlert("Start time is after end time");
        }else if(startTime.isBefore(LocalDateTime.now())){
            createErrorAlert("Cannot create event that starts before current time.");
        }else if(!roomManager.roomExists(roomName)){
            createErrorAlert("This room does not exist");
        }else if(roomManager.cannotAddEvent(roomName, startTime, endTime)){
            createErrorAlert("This room is hosting an event during this time period.");
        }else if(roomManager.hasInsufficientSpace(roomName, speakers.size() + eventCapacity)){
            createErrorAlert("This event capacity exceeds the room capacity. Please choose another room");
        }else{
            UUID eventID = eventManager.addEvent(eventName, username, startTime, endTime, roomName, eventCapacity);
            eventManager.addSpeakers(eventID, speakers);
            roomManager.addEventToSchedule(eventID, roomName, startTime, endTime);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Event created!");
            alert.showAndWait();
            eventNameField.clear();
            startTimeField.clear();
            endTimeField.clear();
            eventCapacityField.clear();
            roomNameField.clear();
            speakersField.clear();
        }
    }

    private LocalDateTime getTime(String time) {
        String[] dateTimeString = time.split("[- :]");
        int[] dateTime = new int[5];
        for(int i = 0; i < dateTimeString.length; i++) dateTime[i] = Integer.parseInt(dateTimeString[i]);
        return LocalDateTime.of(dateTime[0], dateTime[1], dateTime[2], dateTime[3], dateTime[4]);
    }

    private boolean validTime(String time){
        String pattern = "^([0-9][0-9][0-9][0-9])-(0[1-9]|1[0-2])-([0-2][0-9]|3[0-1]) (0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$";
        return time.matches(pattern);
    }

    private void createErrorAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
