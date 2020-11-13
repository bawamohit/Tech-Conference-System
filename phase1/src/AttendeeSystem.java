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
                getPresenter().printPrompt();
                getPresenter().printAttendeeMessageMenu();
                String messageChoice = scanner.nextLine();
                helperMessageSystem(messageChoice);
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

    private String makeOrderedPromptLists(List list){
        String numberedPrompt = new String();
        int i = 0;
        for (Object T: list){
            String num = Integer.toString(i);
            numberedPrompt += "\n" + num + ". " + T.toString();
        }
        return numberedPrompt;
    }

    private void helperMessageSystem(String choice){
        if (choice.equals("0")){
            //Message
            getPresenter().printUnderConstruction();
        }
        else if (choice.equals("1")){
            //edit Message
            getPresenter().printUnderConstruction();
        }
        else if (choice.equals("2")){
            // delete Message
            getPresenter().printUnderConstruction();
        }
        else{
            getPresenter().printInvalidInput();
            getPresenter().printAttendeeMessageMenu();
        }

    }

}
