package UI;

public class AttendeePresenter extends UserPresenter {

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

    /**
     * Prints a display of all events available for signup and their detailed info.
     *
     * @param compiled formatted string of all the aforementioned events
     */
    public void printAvailableEvents(String compiled) {
        System.out.println("The events available for sign-up are: \n" + compiled);
    }

    /**
     * Prints a request for number in list to determine which event to sign up for.
     */
    public void printAskSignUp() {
        System.out.println("Enter the number of the event you would like to register for:");
    }

    /**
     * Prints a request for number in list to determine which event to cancel sign up for.
     */
    public void printAskWhichEventCancel() {
        System.out.println("Enter the number of event you would like to remove sign up from:");
    }

    /**
     * Prints a notification that user has another event booked at a certain time.
     */
    public void printAlreadyBookedTime() {
        System.out.println("You have already booked for another event during the time of this event.");
    }

    /**
     * Prints a notification that user has signed up successfully.
     */
    public void printEventSignUpSuccess() {
        System.out.println("You have now signed up to this event.");
    }

    /**
     * Prints a notification that user has removed signup successfully.
     */
    public void printEventCancelSuccess() {
        System.out.println("You are now removed from this event.");
    }

    /**
     * Prints a notification that user did not sign up to a certain event.
     */
    public void printNotInEvent() {
        System.out.println("You have not signed up to this event.");
    }

}
