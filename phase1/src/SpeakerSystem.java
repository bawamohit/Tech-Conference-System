import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;


public class SpeakerSystem extends UserSystem{
    public SpeakerSystem (Presenter p, UserManager uMan, EventManager eMan, MessageManager mMan) {
        super(p, uMan, eMan, mMan);
    }

    public void run(String username){
        Scanner scan = new Scanner(System.in);

        while(true){
            presenter.printSpeakerMenu();
            String speakerChoice = scan.nextLine();

            switch (speakerChoice){
                case "0":
                    presenter.printLoggedOut();
                    break;
                case "1":
                    while (true) {
                        presenter.printSpeakerMessageMenu();
                        String messageChoice = scan.nextLine();
                        speakerHelperMessageSystem(username, messageChoice, scan);
                        if(messageChoice.equals("b")) {
                            break;
                        } else if (!messageChoice.matches("^[01234]$")) {
                            presenter.printInvalidInput();
                        }
                    }
                case "2":

                default:

            }
        }
    }

    //TODO: Complete this method
    public void speakerHelperMessageSystem(String username, String choice, Scanner scan) {
        super.helperMessageSystem(username, choice, scan);
        messageAll(username, choice, scan);
    }

    public void messageAll(String username, String choice, Scanner scan) {
        if (choice.equals("4")) {
            List<UUID> listEvents = userM.getEventsAttending(username);
            String listChoice = "";
            while (true) {
                presenter.printAskWhichEvents();
                int n = 0;
                for (UUID eventID : listEvents) {
                    presenter.printUCReturns(n + ": " + eventID);
                    n += 1;
                }
                listChoice = scan.nextLine();
                char[] listChoiceSorted = listChoice.toCharArray();
                Arrays.sort(listChoiceSorted);
                listChoice = new String(listChoiceSorted);
                String possibleChoices = "";
                for (int i = 0; i < n; i ++) {
                    possibleChoices = possibleChoices.concat(String.valueOf(i));
                }
                if (possibleChoices.contains(listChoice)) {
                    break;
                } else {
                    presenter.printInvalidInput();
                }
            }
            presenter.printAsk("message");
            String content = scan.nextLine();
            char[] listChoiceSorted = listChoice.toCharArray();
            for (Character eventID : listChoiceSorted) {
                List<String> attendeeList = eventM.getEventAttendees(listEvents.get((int)eventID));
                for (String user : attendeeList) {
                    messageM.sendMessage(username, user, content);
                }
            }
        }
    }
}
