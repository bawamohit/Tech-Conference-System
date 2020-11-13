import java.time.LocalDateTime;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class OrganizerSystem extends UserSystem{
    private RoomManager rm;

    public OrganizerSystem (Presenter p, UserManager uMan, EventManager eMan, MessageManager mMan, RoomManager rMan) {
        super(p, uMan, eMan, mMan); //should we change variables in usersystem to protected? so that we don't have to
        rm = rMan;
    }
    public void run(String username){
        System.out.println("testing");
        Scanner sc = new Scanner(System.in);
        Presenter p = getPresenter(); //can we not do this
        EventManager em = getEm(); //can we not do this
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H"); //determines pattern we want to string to be
        p.printOrganizerMenu();
        String option = sc.nextLine();
        if(option.equals("0")) {
            while (true) {
                p.printAsk("event's name");
                String eventName = sc.nextLine();
                p.printAsk("event speaker's name");
                String speaker = sc.nextLine();
                p.printAsk("event's start time (enter a number from 9-17)");
                String time = sc.nextLine();
                LocalDateTime startTime = LocalDateTime.parse(time, formatter);
                if (em.addEvent(eventName, speaker, username, startTime)){
                    p.printSuccess();
                    //return to menu
                    break;
                }else{
                    p.printFail();
                    //prompt again
                }
            }
        }else if (option.equals("1")){
            p.printUnderConstruction();
        }else if (option.equals("8")){
            p.printUnderConstruction();
                // ask for event id
                // remove event
                // print action successful or unsuccessful
        }else if (option.equals("9")){
            while (true){
                // create speaker account stuff
            }
        }else if (option.equals("10")){
            while (true){
                p.printAsk("new room's name");
                String roomName = sc.nextLine();
//                p.printAsk("new room's maximum capacity");
//                String capacity = sc.nextLine();
                if (rm.addRoom(roomName, 2)){
                    p.printSuccess();
                    //return to menu
                    break;
                }else{
                    p.printFail();
                }
            }
        }
    }

}
