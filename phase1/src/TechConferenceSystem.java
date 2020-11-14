import java.io.IOException;
import java.util.Scanner;

public class TechConferenceSystem {

    private Presenter presenter;
    private UserManager um;
    private EventManager em;
    private MessageManager mm;
    private RoomManager rm;
    private AttendeeSystem as;
    private OrganizerSystem os;
    private SpeakerSystem ss;
    String userManagerInfo;
    String eventManagerInfo;
    String messageManagerInfo;

    public TechConferenceSystem (String userManagerInfo, String eventManagerInfo, String messageManagerInfo) {
        this.userManagerInfo = userManagerInfo;
        this.eventManagerInfo = eventManagerInfo;
        this.messageManagerInfo = messageManagerInfo;
        try {
            UserGateway userGateway = new UserGateway();
            um = userGateway.readFromFile(userManagerInfo);
            EventGateway eventGateway = new EventGateway();
            em = eventGateway.readFromFile(eventManagerInfo);
            MessageGateway messageGateway = new MessageGateway();
            mm = messageGateway.readFromFile(messageManagerInfo);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        presenter = new Presenter();

        as = new AttendeeSystem(presenter, um, em, mm);
        os = new OrganizerSystem(presenter, um, em, mm, rm);
        ss = new SpeakerSystem(presenter, um, em, mm);
    }

    public void run() {
        Scanner in = new Scanner(System.in);
        String loggedInUsername = null;
        while (true) {
            loggedInUsername = startUp(in);

            if (um.getUserType(loggedInUsername) == UserType.ATTENDEE) {
                as.run(loggedInUsername);
            } else if (um.getUserType(loggedInUsername) == UserType.ORGANIZER) {
                os.run(loggedInUsername);
            } else {
                ss.run(loggedInUsername);
            }
            //THIS IS WHERE WE CAN SAVE INFO (WRITE TO FILES)
            try {
                UserGateway userGateway = new UserGateway();
                userGateway.saveToFile(userManagerInfo, um);
                EventGateway eventGateway = new EventGateway();
                eventGateway.saveToFile(eventManagerInfo, em);
                MessageGateway messageGateway = new MessageGateway();
                messageGateway.saveToFile(messageManagerInfo, mm);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private UserType askAccountType(Scanner in) {
        UserType accountType = null;
        while (accountType == null) {
            presenter.printAskUserType();
            String accountOption = in.nextLine();

            switch (accountOption) {
                case "0":
                    accountType = UserType.ATTENDEE;
                    break;
                case "1":
                    accountType = UserType.ORGANIZER;
                    break;
                case "2":
                    accountType = UserType.SPEAKER;
                    break;
                default:
                    presenter.printInvalidInput();
                    break;
            }
        }
        return accountType;
    }

    private String[] askInfo(Scanner in) {
        presenter.printAsk("Username");
        String username = in.nextLine();
        presenter.printAsk("Password");
        String password = in.nextLine();
        String[] info = new String[2];
        info[0] = username;
        info[1] = password;
        return info;
    }

    private String login(Scanner in) {
        String[] info = askInfo(in);

        if (um.verifyLogin(info[0], info[1])) {
            return info[0];
        }
        else {
            presenter.printWrongAccountInfo();
            return null;
        }
    }

    private String signUp(Scanner in) {
        presenter.printAsk("Name");
        String name = in.nextLine();

        String[] info = askInfo(in);
        UserType accountType = askAccountType(in);

        if (um.registerUser(accountType, name, info[0], info[1])) {
            return info[0];
        }
        else {
            presenter.printUsernameExists();
            return null;
        }
    }

    private String startUp(Scanner in) {
        String username = null;
        while (username == null) {
            presenter.printWelcome();
            String loginOrSignUp = in.nextLine();

            switch (loginOrSignUp) {
                case "1":
                    username = login(in);
                    break;
                case "2":
                    username = signUp(in);
                    break;
                default:
                    presenter.printInvalidInput();
            }
        }
        return username;
    }
}
