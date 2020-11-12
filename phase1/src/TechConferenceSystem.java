import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class TechConferenceSystem {

    protected Presenter presenter;
    protected UserManager um;
//    protected EventManager em;
//    protected MessageManager mm;

    public TechConferenceSystem () {
        presenter = new Presenter();
        um = new UserManager();
        //em = new EventManager(eventData);
        //mm = new MessageManager(messageData);
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        presenter.printWelcome();

        while (true) {
            presenter.printAskID();
            String username = sc.nextLine();
            presenter.printAskPassword();
            String password = sc.nextLine();
            if (um.verifyLogin(username, password)) {
                presenter.printAttendeeMenu();//implement corresponding stuff
                break;
            }
            else {
                presenter.printWrongAccountInfo();
            }
        }
    }

//    public void loginUser (String username, String password) {
//
//    }
//
//    public void eventSignUp(String username, UUID eventId){
//        em.addAttendee(username, eventId);
//    }
//
//    public void eventCancelAttendee(String username, UUID eventId){
//        em.removeAttendee(username, eventId);
//    }

}
