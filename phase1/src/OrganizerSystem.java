import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class OrganizerSystem extends UserSystem{
    private RoomManager rm;

    public OrganizerSystem (Presenter p, UserManager uMan, EventManager eMan, MessageManager mMan, RoomManager rMan) {
        super(p, uMan, eMan, mMan);
        RoomManager rm = rMan;
    }

    public void run(String username){
        System.out.println("testing");
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H"); //determines pattern we want to string to be
        Presenter p = getPresenter();
        EventManager em = getEm();
        getPresenter().printOrganizerMenu();
        String option = sc.nextLine();
        if (option.equals("0")) {
            while (true) {
                p.printAsk("event's name");
                String eventName = sc.nextLine();
                p.printAsk("event speaker's name");
                String speaker = sc.nextLine();
                p.printAsk("event's start time (enter a number from 9-17)");
                String time = sc.nextLine();
                p.printAsk("event's room name (enter room name)");
                String roomName = sc.nextLine();
                LocalDateTime startTime = LocalDateTime.parse(time, formatter);
                if (em.addEvent(eventName, speaker, username, startTime, roomName)){
                    p.printSuccess();
                    //return to menu
                    break;
                }else{
                    p.printFail();
                    //prompt again
                }
            }
        }else if (option.equals("1")){
            getPresenter().printOrganizerMessageMenu();
            String messageChoice = sc.nextLine();
            helperMessageSystem(username, option, sc);
        }else if (option.equals("8")){
            p.printUnderConstruction();
                // ask for event id
                // remove event
                // print action successful or unsuccessful
        }else if (option.equals("9")){
            while (true){
                // create speaker account stuff
            }
        }else if (option.equals("10")){
            while (true){
                p.printAsk("new room's name");
                String roomName = sc.nextLine();
//                p.printAsk("new room's maximum capacity");
//                String capacity = sc.nextLine();
                if (rm.addRoom(roomName, 2)){
                    p.printSuccess();
                    //return to menu
                    break;
                }else{
                    p.printFail();
                }
            }
        }
    }

    private void helperMessageSystem(String username, String option, Scanner sc) {
        if (option.equals("0")){
            getPresenter().printAskMsgReceiver();
            String receiver = sc.nextLine();
            getPresenter().printAsk("message");
            String message = sc.nextLine();
            getMm().sendMessage(username, receiver, message);
            getPresenter().printMessageSent();
        } else if (option.equals("1")){
            //edit Message
            getPresenter().printUnderConstruction();
        } else if (option.equals("2")){
            // delete Message
            System.out.println(getMm().getChats(username));
            getPresenter().printAskWhichInbox();
            String inboxChoice = sc.nextLine();
            List<String> inbox = getMm().getInbox(username, inboxChoice);
            int n = 0;
            for (String message : inbox) {
                System.out.println(n + ": " + message);
                n += 1;
            }
            getPresenter().printAskWhichMessage();
            String content = sc.nextLine();
            getMm().deleteMessage(getMm().getChat(username, inboxChoice).get(n));
        } else if (option.equals("3")){
            // view inbox
            getPresenter().printAskWhichInbox();
            getPresenter().printUCReturns(getMm().getChats(username));
            String contact = sc.nextLine();
            for (String message :getMm().getInbox(username, contact)){
                System.out.println(message);
            }
        } else if (option.equals("4") || option.equals("5")) {
            getPresenter().printAsk("message");
            String content = sc.nextLine();
            List<String> userList = getUm().getUsernameList();
            for (String user : userList) {
                if (option.equals("4") && getUm().getUserType(user) == UserType.SPEAKER) {
                    getMm().sendMessage(username, user, content);
                } else if (getUm().getUserType(user) == UserType.ORGANIZER) {
                    getMm().sendMessage(username, user, content);
                }
            }
        } else if (option.equals("b")){
            run(username);
        } else {
            getPresenter().printInvalidInput();
            getPresenter().printAttendeeMessageMenu();
        }
    }

}
