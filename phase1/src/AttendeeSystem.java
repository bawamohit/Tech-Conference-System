import java.util.Scanner;

public class AttendeeSystem extends UserSystem{
    public AttendeeSystem (Presenter p, UserManager uMan, EventManager eMan, MessageManager mMan) {
        super(p, uMan, eMan, mMan);
    }

    public void run(String username){
        Scanner scanner = new Scanner(System.in);

        while(true){
            getPresenter().printAttendeeMenu();
            getPresenter().printPrompt();
            String attendeeChoice = scanner.nextLine();

            if (attendeeChoice.equals("0")){
                System.out.println("You have now logged out.");
                return;
            }

            else if (attendeeChoice.equals("1")){
                getPresenter().printAttendeeMessageMenu();
            }

            else if (attendeeChoice.equals("2")){
                getEm().getEvents();
            }
            //need to figure out how to access entity
            //else if (attendeeChoice.equals("3")){
              //  um.getEventsAttending();
           // }
            //else if (attendeeChoice.equals("4")){

          //  }
        }
    }

}
