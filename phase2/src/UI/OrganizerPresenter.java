package UI;

public class OrganizerPresenter extends UserPresenter {

    /**
     * Prints the menu options specific to organizers.
     */
    public void printOrganizerMenu() {
        printCommonMenu();
        System.out.println(
                "2. Create Event\n" +
                        "3. Change Event Capacity\n" +
                        "4. Remove Event\n" +
                        "5. Create Speaker Account\n" +
                        "6. Create Room\n" +
                        "7. Add Speaker to Event"
        );
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
     * Prints a request for number in list to determine which event to sign up for.
     */
    public void printAskSignUp() {
        System.out.println("Enter the number of the event you would like to register the speaker for:");
    }

    public void printAskRemove() {
        System.out.println("Enter the number of the event you would like to cancel:");
    }
    /**
     * Prints a display of all events available for signup and their detailed info.
     *
     * @param compiled formatted string of all the aforementioned events
     */
    public void printAvailableEvents(String compiled) {
        System.out.println("The events available are: \n" + compiled);
    }

    /**
     * Prints a notification that user has successfully performed an action on an event.
     */
    public void printEventActionSuccess(String action) {
        System.out.println("Event successfully " + action + ".");
    }

    /**
     * Prints a notification that user has failed to perform an action on an event.
     */
    public void printEventActionFail(String action) {
        System.out.println("Error! Event not " + action + ".");
    }

    public void printNotASpeaker(){
        System.out.println("This user is not a speaker");
    }
}
