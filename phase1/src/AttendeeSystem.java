import java.util.Scanner;

public class AttendeeSystem extends TechConferenceSystem {
    private Presenter presenter;
    private UserManager um;
    private EventManager em;
    private MessageManager mm;

    public AttendeeSystem () {
        presenter = new Presenter();
        um = new UserManager();
        em = new EventManager();
        mm = new MessageManager();
    }

    public void run(){
        Scanner scanner = new Scanner(System.in);

        while(true){
            presenter.printAttendeeMenu();
            presenter.printPrompt();
            String attendeeChoice = scanner.nextLine();

            if (attendeeChoice.equals("0")){
                System.out.println("You have now logged out.");
                presenter.printWelcome();
            }

            else if (attendeeChoice.equals("1")){
                presenter.printAttendeeMessageMenu();
            }

            else if (attendeeChoice.equals("2")){
                em.getEvents();
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
