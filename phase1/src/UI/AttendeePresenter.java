package UI;

public class AttendeePresenter extends Presenter {

    /**
     * Prints the menu options specific to attendees.
     */
    public void printAttendeeMenu() {
        printCommonMenu();
        System.out.println(
                "2. View All Available Events\n" +
                        "3. View My Events\n" +
                        "4. Sign-up Event\n" +
                        "5. Cancel Event\n");
    }

    /**
     * Prints the messaging menu options specific to attendees.
     */
    public void printAttendeeMessageMenu() {
        super.printCommonMessageMenu();
    }
}
