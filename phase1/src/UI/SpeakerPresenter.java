package UI;

public class SpeakerPresenter extends UserPresenter {

    /**
     * Prints the menu options specific to speakers.
     */
    public void printSpeakerMenu() {
        printCommonMenu();
        System.out.println("2. View My Talks\n");
    }

    /**
     * Prints the message menu options specific to speakers.
     */
    public void printSpeakerMessageMenu() {
        printCommonMessageMenu();
        System.out.println("4. Message all Attendees of your talk(s).\n");
    }
}
