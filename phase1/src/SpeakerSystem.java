import java.util.Scanner;


public class SpeakerSystem extends UserSystem{
    public SpeakerSystem (Presenter p, UserManager uMan, EventManager eMan, MessageManager mMan) {
        super(p, uMan, eMan, mMan);
    }

    public void run(String Username){
        Scanner scan = new Scanner(System.in);
        getPresenter().printSpeakerMenu();
        String speakerChoice = scan.nextLine();

        switch (speakerChoice){
            case "0":

            default:

        }
//
//        //message all attendee in a talk
//        //view all talk
//
//        while(true){
//            presenter.printSpeakerMenu();
//            String speakerChoice = scan.nextLine();
//            if (speakerChoice.equals("7")){
//
//            }
//        }
    }
}
