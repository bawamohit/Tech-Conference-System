package Controllers;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import UseCases.*;
import UI.Presenter;
import Gateways.*;
import Entities.UserType;

public class TechConferenceSystem {

    private Presenter presenter;
    private UserManager um;
    private EventManager em;
    private MessageManager mm;
    private RoomManager rm;
    private UserGateway userGateway;
    private EventGateway eventGateway;
    private MessageGateway messageGateway;
    private RoomGateway roomGateway;
    File eventManagerInfo = new File("./src/Data/eventManager.ser");
    File messageManagerInfo = new File("./src/Data/messageManager.ser");
    File userManagerInfo = new File("./src/Data/userManager.ser");
    File roomManagerInfo = new File("./src/Data/roomManager.ser");

    public TechConferenceSystem () {
        try {
            userGateway = new UserGateway();
            eventGateway = new EventGateway();
            messageGateway = new MessageGateway();
            roomGateway = new RoomGateway();
            um = userGateway.readFromFile(userManagerInfo.getPath());
            em = eventGateway.readFromFile(eventManagerInfo.getPath());
            mm = messageGateway.readFromFile(messageManagerInfo.getPath());
            rm = roomGateway.readFromFile(roomManagerInfo.getPath());
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        presenter = new Presenter();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String loggedInUsername;
        while (true) {//TODO need exit option
            loggedInUsername = startUp(scanner);

            UserSystem system;
            if (um.getUserType(loggedInUsername) == UserType.ATTENDEE) {
                system = new AttendeeSystem();
            } else if (um.getUserType(loggedInUsername) == UserType.ORGANIZER) {
                system = new OrganizerSystem();
            } else {
                system = new SpeakerSystem();
            }
            system.run(loggedInUsername, this);

            //THIS IS WHERE WE CAN SAVE INFO (WRITE TO FILES)
            try {
                userGateway.saveToFile(userManagerInfo.getPath(), um);
                eventGateway.saveToFile(eventManagerInfo.getPath(), em);
                messageGateway.saveToFile(messageManagerInfo.getPath(), mm);
                roomGateway.saveToFile(roomManagerInfo.getPath(), rm);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public Presenter getPresenter(){
        return presenter;
    }

    public UserManager getUM(){
        return um;
    }
    public EventManager getEM(){
        return em;
    }

    public MessageManager getMM(){
        return mm;
    }

    public RoomManager getRM(){
        return rm;
    }

    private String startUp(Scanner scanner) {//TODO add exit option, start from 0
        String username = null;
        presenter.printWelcome();
        String loginOrSignUp = validInput("^[12]$", "option", scanner);

        while(username == null) {
            switch (loginOrSignUp) {
                case "1":
                    username = login(scanner);
                    break;
                case "2":
                    username = signUp(scanner);
                    break;
            }
            if(username == null){
                presenter.printWelcome();
                loginOrSignUp = validInput("^[12]$", "option", scanner);
            }
        }

        return username;
    }

    private String login(Scanner scanner) {
        presenter.printAsk("username");
        String username = scanner.nextLine();
        presenter.printAsk("password");
        String password = scanner.nextLine();

        if (um.verifyLogin(username, password)) {
            return username;
        }
        else {
            presenter.printWrongAccountInfo();
            return null;
        }
    }

    private String signUp(Scanner scanner) {
        presenter.printAskWithBack("username");
        String username = scanner.nextLine();
        if (username.equals("")) return null;
        while(um.isRegistered(username)){
            presenter.printUsernameTaken();
            username = scanner.nextLine();
            if (username.equals("")) return null;
        }

        presenter.printAsk("password");
        String password = validInput(".+", "password", scanner);
        UserType accountType = askAccountType(scanner);
        presenter.printAsk("name");
        String name = validInput(".+", "name", scanner);

        um.registerUser(accountType, name, username, password);
        presenter.printSignUpSuccessful(name);
        return username;
    }

    protected String validInput(String pattern, String field, Scanner scanner){
        String input = scanner.nextLine();
        while(!input.matches(pattern)){
            presenter.printInvalidField(field);
            input = scanner.nextLine();
        }
        return input;
    }

    private String nonEmptyInput(String input, String field, Scanner scanner) {
        while(input.equals("")){
            presenter.printInvalidField(field);
            input = scanner.nextLine();
        }
        return input;
    }

    private UserType askAccountType(Scanner scanner) {
        UserType accountType = null;
        while (accountType == null) {
            presenter.printAskUserType();
            String accountOption = scanner.nextLine();

            switch (accountOption) {
                case "0":
                    accountType = UserType.ATTENDEE;
                    break;
                case "1":
                    accountType = UserType.ORGANIZER;
                    break;
                default:
                    presenter.printInvalidInput();
                    break;
            }
        }
        return accountType;
    }
}
