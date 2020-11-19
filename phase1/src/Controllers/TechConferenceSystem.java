package Controllers;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import Use_cases.*;
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
    File eventManagerInfo = new File("./phase1/src/eventManager.ser");
    File messageManagerInfo = new File("./phase1/src/messageManager.ser");
    File userManagerInfo = new File("./phase1/src/userManager.ser");
    File roomManagerInfo = new File("./phase1/src/roomManager.ser");

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
        Scanner in = new Scanner(System.in);
        String loggedInUsername;
        while (true) {//TODO need exit option
            loggedInUsername = startUp(in);

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
            presenter.printObjectExists("Username");
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
