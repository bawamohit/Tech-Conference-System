import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class TechConferenceSystem {

    protected UserManager um;
//    protected EventManager em;
//    protected MessageManager mm;

    public TechConferenceSystem () {
        um = new UserManager();
        //em = new EventManager(eventData);
        //mm = new MessageManager(messageData);
    }

    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String username;
            System.out.println("presenter.promptUsername");
            while (true) {
                try {
                    username = br.readLine();
                    break;

                } catch (IOException e) {
                    System.out.println("presenter.promptInvalidInput");
                }
            }

            String password;
            System.out.println("presenter.promptPassword");
            while (true) {
                try {
                    password = br.readLine();
                    break;
                } catch (IOException e) {
                    System.out.println("presenter.promptInvalidInput");
                }
            }

            if (um.verifyLogin(username, password)) {
                System.out.println("presenter.promptLoginSucceeded");
                break;
            }
            else {
                System.out.println("presenter.promptLoginFailed");
                System.out.print("\033[H\033[2J");
                System.out.flush();
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
