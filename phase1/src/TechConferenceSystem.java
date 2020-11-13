import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

public class TechConferenceSystem {

    private Presenter presenter;
    private UserManager um;
    private EventManager em;
    private MessageManager mm;
    private AttendeeSystem as;
    private OrganizerSystem os;
    private SpeakerSystem ss;

    public TechConferenceSystem () {
        presenter = new Presenter();
        um = new UserManager();
        em = new EventManager();
        mm = new MessageManager();
        as = new AttendeeSystem(presenter, um, em, mm);
        os = new OrganizerSystem(presenter, um, em, mm);
        ss = new SpeakerSystem(presenter, um, em, mm);
    }

    public void run() {
        Scanner in = new Scanner(System.in);
        String loggedInUsername;
        while (true) {
            while (true) {
                presenter.printWelcome();
                String loginOrSignUp = in.nextLine();

                if (loginOrSignUp.equals("1")) {
                    String[] info = askInfo(in);

                    if (um.verifyLogin(info[0], info[1])) {
                        loggedInUsername = info[0];
                        break;
                    }
                    else {
                        presenter.printWrongAccountInfo();
                    }
                }
                else if (loginOrSignUp.equals("2")) {
                    presenter.printAsk("name");
                    String name = in.nextLine();

                    String[] info = askInfo(in);
                    UserType accountType = askAccountType(in);

                    if (um.registerUser(accountType, name, info[0], info[1])) {
                        loggedInUsername = info[0];
                        break;
                    }
                    else {
                        presenter.printUsernameExists();
                    }
                }
                else {
                    presenter.printInvalidInput();
                }
            }

            if (um.getUserType(loggedInUsername) == UserType.ATTENDEE) {
                as.run(loggedInUsername);
            } else if (um.getUserType(loggedInUsername) == UserType.ORGANIZER) {
                os.run(loggedInUsername);
            } else {
                ss.run(loggedInUsername);
            }
        }
    }

    public UserType askAccountType(Scanner in) {
        UserType accountType = null;
        while (true) {
            presenter.printAskUserType();
            String accountOption = in.nextLine();

            if (accountOption.equals("0")){
                accountType = UserType.ATTENDEE;
                break;
            } else if (accountOption.equals("1")){
                accountType = UserType.ORGANIZER;
                break;
            } else if (accountOption.equals("2")){
                accountType = UserType.SPEAKER;
                break;
            } else {
                presenter.printInvalidInput();
            }
        }
        return accountType;
    }

    public String[] askInfo(Scanner in) {
        presenter.printAsk("user ID");
        String username = in.nextLine();
        presenter.printAsk("password");
        String password = in.nextLine();
        String[] info = new String[2];
        info[0] = username;
        info[1] = password;
        return info;
    }
}
