import java.io.File;
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
    File eventManagerInfo = new File("./phase1/src/eventManager.ser");
    File messageManagerInfo = new File("./phase1/src/messageManager.ser");
    File userManagerInfo = new File("./phase1/src/userManager.ser");
    File roomManagerInfo = new File("./phase1/src/roomManager.ser");

    public TechConferenceSystem () {
        try {
            UserGateway userGateway = new UserGateway();
            um = userGateway.readFromFile(userManagerInfo.getPath());
            EventGateway eventGateway = new EventGateway();
            em = eventGateway.readFromFile(eventManagerInfo.getPath());
            MessageGateway messageGateway = new MessageGateway();
            mm = messageGateway.readFromFile(messageManagerInfo.getPath());
            RoomGateway roomGateway = new RoomGateway();
            rm = roomGateway.readFromFile(roomManagerInfo.getPath());
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
        while (true) {//TODO need exit option
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
                userGateway.saveToFile(userManagerInfo.getPath(), um);
                EventGateway eventGateway = new EventGateway();
                eventGateway.saveToFile(eventManagerInfo.getPath(), em);
                MessageGateway messageGateway = new MessageGateway();
                messageGateway.saveToFile(messageManagerInfo.getPath(), mm);
                RoomGateway roomGateway = new RoomGateway();
                roomGateway.saveToFile(roomManagerInfo.getPath(), rm);
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
        presenter.printAsk("Username");//TODO lowercase username
        String username = in.nextLine();
        presenter.printAsk("Password");//TODO lowercase password
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

    private String signUp(Scanner in) {//TODO sign-up successful prompt
        presenter.printAsk("Name");//TODO lowercase name
        String name = in.nextLine();
        //TODO need restriction on input, currently accepts empty string
        String[] info = askInfo(in);//TODO move isRegistered() here so user don't have to waste time inputting password?
        UserType accountType = askAccountType(in);

        if (um.registerUser(accountType, name, info[0], info[1])) {
            return info[0];
        }
        else {
            presenter.printUsernameExists();
            return null;
        }
    }

    private String startUp(Scanner in) {//TODO add exit option, start from 0
        String username = null;
        while (username == null) {
            presenter.printWelcome(); //TODO move outside of while-loop?
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
                    break; //TODO added this line
            }
        }
        return username;
    }
}
