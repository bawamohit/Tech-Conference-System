import java.util.List;
import java.util.Scanner;

public class AttendeeSystem extends UserSystem{
    public AttendeeSystem (Presenter p, UserManager uMan, EventManager eMan, MessageManager mMan) {
        super(p, uMan, eMan, mMan);
    }

    public void run(String username) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
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

            else if (attendeeChoice.equals("2")) {
                getPresenter().printUCReturns(getEm().getEvents());
            }
            else if (attendeeChoice.equals("3")) {//we need to make list of event names.
//                getPresenter().printUCReturns(getUm().getEventsAttending(username));
                getPresenter().printUnderConstruction();
            }
            else if (attendeeChoice.equals("4")) { //signup event
//                System.out.println(makeOrderedPromptLists(getEm().getEvents()));
                getPresenter().printUnderConstruction();
            }
            else if (attendeeChoice.equals("5")){ //cancel event
//                getPresenter().printUCReturns(getUm().getEventsAttending(username));
//                System.out.println("Type the name of event you would like to remove");
//                String eventChoice = scanner.nextLine();
                getPresenter().printUnderConstruction();
            }
            else {
                getPresenter().printInvalidInput();
            }
        }
    }

//    private String makeOrderedPromptLists(List list){
//        String numberedPrompt = new String();
//        int i = 0;
//        for (Object T: list){
//            String num = Integer.toString(i);
//            numberedPrompt += "\n" + num + ". " + T.toString();
//        }
//        return numberedPrompt;
//    }

    private void helperMessageSystem(String username, String choice, Scanner scanner){
        if (choice.equals("0")){
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
            // view inbox
            System.out.println("Which contact inbox do you want to see? Type the username");
            getPresenter().printUCReturns(getMm().getChats(username));
            String contact = scanner.nextLine();
            for (String message :getMm().getInbox(username, contact)){
                System.out.println(message);
            }
        }
        else if (choice.equals("b")){
             run(username);
        }
        else{
            getPresenter().printInvalidInput();
            getPresenter().printAttendeeMessageMenu();
        }

    }

}
