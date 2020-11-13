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
