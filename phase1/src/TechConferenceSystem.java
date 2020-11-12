import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.UUID;

public class TechConferenceSystem {

    private Presenter presenter;
    private UserManager um;
    private EventManager em;
    private MessageManager mm;

    public TechConferenceSystem () {
        presenter = new Presenter();
        um = new UserManager();
        em = new EventManager();
        mm = new MessageManager();
    }

    public UserManager getUm() {
        return um;
    }

    public EventManager getEm() {
        return em;
    }

    public MessageManager getMm() {
        return mm;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public void run() {
        Scanner in = new Scanner(System.in);
        String username;

        while (true) {
            presenter.printWelcome();
            String loginOrSignUp = in.nextLine();

            if (loginOrSignUp.equals("1")) {
                presenter.printAskID();
                username = in.nextLine();
                presenter.printAskPassword();
                String password = in.nextLine();

                if (um.verifyLogin(username, password)) {
                    break;
                }
                else {
                    System.out.println("presenter.promptLoginFailed\n\n");
                }
            }
            else if (loginOrSignUp.equals("2")) {
                System.out.println("What is your name bro?");
                String name = in.nextLine();

                System.out.println("How bout a username fam?");
                username = in.nextLine();

                System.out.println("Ya need a password dawg?");
                String password = in.nextLine();

                System.out.println("What type of account?");
                String accountOption = in.nextLine();

                UserType accountType = null;

                if (accountOption.equals("1")){
                    accountType = UserType.ATTENDEE;
                }
                else if (accountOption.equals("2")){
                    accountType = UserType.ORGANIZER;
                }
                else if (accountOption.equals("3")){
                    accountType = UserType.SPEAKER;
                }

                if (accountType == null){
                    System.out.println("ERROR that is not a valid choice.\n\n");
                }
                else {
                    if (um.registerUser(accountType, name, username, password)) {
                        break;
                    } else {
                        System.out.println("ERROR Username already exists.\n\n");
                    }
                }
            }
            else {
                System.out.println("Error that is not a valid choice.\n\n");
            }
        }
        System.out.println("Hello " + um.getName(username) + "! You are logged in as a(n) " + um.getUserType(username));
    }
}
