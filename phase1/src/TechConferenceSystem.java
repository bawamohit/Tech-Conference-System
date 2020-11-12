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

        while (true) {
            presenter.printWelcome();
            String loginOrSignUp = in.nextLine();

            if (loginOrSignUp.equals("1")) {
                String[] info = askInfo(in);
                UserType accountType = askAccountType(in);

                if (um.verifyLogin(info[0], info[1])) {
                    menu(accountType);
                    String option = in.nextLine();
                    if (option.equals("1")) {
                        message(accountType);
                        presenter.printBack();
                    }
                    break;
                } else {
                    presenter.printWrongAccountInfo();
                }
            } else if (loginOrSignUp.equals("2")) {
                presenter.printAsk("name");
                String name = in.nextLine();

                String[] info = askInfo(in);
                UserType accountType = askAccountType(in);

                if (um.registerUser(accountType, name, info[0], info[1])) {
                    menu(accountType);
                    String option = in.nextLine();
                    if (option.equals("1")) {
                        message(accountType);
                        presenter.printBack();
                    }
                    break;
                } else {
                    presenter.printUsernameExists();
                }
            } else {
                presenter.printInvalidInput();
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

    public void menu(UserType accountType) {
        if (accountType == UserType.ATTENDEE) {
            presenter.printAttendeeMenu();
        } else if (accountType == UserType.ORGANIZER) {
            presenter.printOrganizerMenu();
        } else {
            presenter.printSpeakerMenu();
        }
    }

    public void message(UserType accountType) {
        if (accountType == UserType.ATTENDEE) {
            presenter.printAttendeeMessageMenu();
        } else if (accountType == UserType.ORGANIZER) {
            presenter.printOrganizerMessageMenu();
        } else {
            presenter.printSpeakerMessageMenu();
        }
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
