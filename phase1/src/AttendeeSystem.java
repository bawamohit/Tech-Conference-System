import java.util.List;
import java.util.Scanner;

public class AttendeeSystem extends UserSystem{
    public AttendeeSystem (Presenter p, UserManager uMan, EventManager eMan, MessageManager mMan) {
        super(p, uMan, eMan, mMan);
    }

    public void run(String username) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            getPresenter().printPrompt();
            getPresenter().printAttendeeMenu();
            String attendeeChoice = scanner.nextLine();

            if (attendeeChoice.equals("0")) {
                System.out.println("You have now logged out.");
                break;
            } else if (attendeeChoice.equals("1")) {
                getPresenter().printAttendeeMessageMenu();
                String messageChoice = scanner.nextLine();
                helperMessageSystem(username, messageChoice, scanner);
            }

            //4. Sign-up Event\n" +
            //                "5. Cancel Event\n");
            else if (attendeeChoice.equals("2")) {
                getPresenter().printUCReturns(getEm().getEvents());
            }
//            else if (attendeeChoice.equals("3")) {//we need to make list of event names.
//                getPresenter().printUCReturns(getUm().getEventsAttending(username));
//            }
//            else if (attendeeChoice.equals("4")) {
//                makeOrderedPromptLists(getEm().getEvents())
//            }
//            else if (attendeeChoice.equals("5")){
//                getPresenter().printUCReturns(getUm().getEventsAttending(username));
//                System.out.println("Type the name of event you would like to remove");
//                String eventChoice = scanner.nextLine();}
        }
    }

//    private String makeOrderedPromptLists(List list){
//        String numberedPrompt = new String();
//        int i = 0;
//        for (T :list){
//            numberedPrompt += "\n" + i + ". " + T.toString();
//            i += 1;
//        }
//        return numberedPrompt;
//    }

    private void helperMessageSystem(String username, String choice, Scanner scanner){
        if (choice.equals("0")){
            //Message
            //input username
            //show list of contacts
            System.out.println("Enter username you would like to message");
            String reciever = scanner.nextLine();
            System.out.println("Enter your message");
            String message = scanner.nextLine();
            getMm().sendMessage(username, reciever, message);
            System.out.println("Message sent");
        }
        else if (choice.equals("1")){
            //edit Message
            getPresenter().printUnderConstruction();
        }
        else if (choice.equals("2")){
            // delete Message
            getPresenter().printUnderConstruction();
        }
        else if (choice.equals("3")){
            // delete Message
            System.out.println("Which contact inbox do you want to see?");
            getPresenter().printUCReturns(getMm().getChats(username));
            String contact = scanner.nextLine();
            for (Message message :getMm().getChat(username, contact)){
                System.out.println(message.getContent());
            };
        }
        else{
            getPresenter().printInvalidInput();
            getPresenter().printAttendeeMessageMenu();
        }

    }

}
