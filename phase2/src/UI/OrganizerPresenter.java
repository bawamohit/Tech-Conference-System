package UI;

public class OrganizerPresenter extends UserPresenter {

    /**
     * Prints the menu options specific to organizers.
     */
    public void printOrganizerMenu() {
        printCommonMenu();
        System.out.println(
                "2. Create Event\n" +
                        "3. Reschedule Event\n" +
                        "4. Remove Event\n" +
                        "5. Create Speaker Account\n" +
                        "6. Create Room\n");
    }

    /**
     * Prints the message menu options specific to organizers.
     */
    public void printOrganizerMessageMenu() {
        printCommonMessageMenu();
        System.out.println("4. Message all speakers\n" +
                "5. Message all attendees\n");
    }

    /**
     * Prints a notification that user has successfully created an event.
     */
    public void printEventCreationSuccess() {
        System.out.println("Event successfully created.");
    }

    /**
     * Prints a notification that user has failed to create an event.
     */
    public void printEventCreationFail() {
        System.out.println("Error! Event not created.");
    }
}
