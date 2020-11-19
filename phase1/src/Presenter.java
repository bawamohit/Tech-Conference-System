public class Presenter {
// Main Menu
    public void printWelcome() {
        System.out.println("\nWelcome to the lamest Technology conference ever!!!\n" +
                "\nPress 1 to login to your account\n" +
                "\nPress 2 to sign up for an account\n");
    }

    public void printCommonMenu() {
        printPrompt();
        System.out.println("\n0. Logout\n" +
                "1. Messaging");
    }

    public void printAttendeeMenu() {
        printCommonMenu();
        System.out.println(
                "2. View All Available Events\n" +
                        "3. View My Events\n" +
                        "4. Sign-up Event\n" +
                        "5. Cancel Event\n");
    }

    public void printOrganizerMenu() {
        printCommonMenu();
        System.out.println(
                "2. Create Event\n" +
                        "3. Reschedule Event\n" +
                        "4. Remove Event\n" +
                        "5. Create Speaker Account\n" +
                        "6. Create Room\n");
    }

    public void printSpeakerMenu() {
        printCommonMenu();
        System.out.println("2. View My Talks\n");
    }

// Message Center
    public void printAttendeeMessageMenu() {
        printBack();
        printPrompt();
        System.out.println("\n0. Message\n" +
                "1. Edit a message\n" +
                "2. Delete a message\n" +
                "3. View message chats");
    }

    public void printOrganizerMessageMenu() {
        printAttendeeMessageMenu();
        System.out.println("4. Message all speakers\n" +
                "5. Message all attendees\n");
    }

    public void printSpeakerMessageMenu() {
        printAttendeeMessageMenu();
        System.out.println("4. Message all Attendees of your talk(s).\n");
    }

    public void printAskMsgReceiver() {
        System.out.println("Enter username you would like to message");
    }

    public void printMessageSent() {
        System.out.println("Message sent");
    }

    public void printAskWhichInbox() {
        System.out.println("Which contact inbox do you want to see? Type the username");
    }

    public void printAskWhichMessage() {
        System.out.println("Which message? (Enter a number)");
    }

    public void printAskWhichEvents() { System.out.println("Which event(s)? (Enter the number(s))"); }

    public void printUserDoesNotExist() {
        System.out.println("The user you are trying to message does not exist");
    }

// Creating an account
    public void printWrongAccountInfo() {
        System.out.println("\nOOPS looks like you entered the wrong login information," +
                "Please enter your username again.\n" +
                "If you get it wong again FBI WILL BE KNOCKING ON YOUR DOOR IN 10 MINUTES\n" +
                "Or enter 'sign up' to sign up for an account.");
    }

    public void printAskUserType() {
        System.out.println("\nPlease enter your account type.\n");
        System.out.println("0. Attendee\n" +
                "1. Organizer\n" +
                "2. Speaker\n");
    }

    public void printUserInfo(UserType usertype, String username, String pw) {
        System.out.println("\n The information for the " + usertype + " account you just created is: " +
                "\n Username: " + username +
                "\n Password: " + pw);
    }

// Event Actions
    public void printAvailableEvents(String compiled) {
        System.out.println("The events available for sign-up are: \n" + compiled);
    }
    public void printAskSignUp() {
        System.out.println("Enter the number of the event you would like to register for:");
    }

    public void printAskWhichEventCancel() {
        System.out.println("Enter the name of event you would like to remove sign up from:");
    }

    public void printAlreadyBookedTime() {
        System.out.println("You have already booked for another event during the time of this event.");
    }

    public void printEventSignUpSuccess() {
        System.out.println("You have now sign up to this event.");
    }

    public void printEventCancelSuccess() {
        System.out.println("You are now removed from this event.");
    }

    public void printNotInEvent() {
        System.out.println("You have not signed up to this event.");
    }

    public void printEventCreationSuccess() {
        System.out.println("Event successfully created.");
    }

    public void printEventCreationFail() {
        System.out.println("Error! Event not created.");
    }

    public void printNoEventsAvailable() {
        System.out.println("There are no events available for sign-up :(");
    }

    public void printEventFull() {
        System.out.println("Sorry, this event is full.");
    }

// :( There's a problem :(
    public void printInvalidInput() {
        System.out.println("\nInvalid input.\n");
    }

    public void printIOException() {
        System.out.println("IOException");
    }

    public void printDNE(String obj) {
        System.out.println("Sorry, " + obj + " does not exist in the system.");
    }

    public void printObjUnavailable(String obj) {
        System.out.println("Sorry, " + obj + " is unavailable.");
    }

    public void printObjectExists(String obj) { System.out.println("\n" + obj + "already exists\n"); }

    public void printUnprocessed() { System.out.println("Sorry, your request didn't go through. Please try again!"); }

// Action status
    public void printSuccess() {
        System.out.println("\nYou crazy son of a female dog, you did it.");
    }

    public void printFail() {
        System.out.println("\nAction failed. Dishonor on you, dishonor on your cow.");
    }

// Requests
    public void printAsk(String ask) {
        System.out.println("\nPlease enter your " + ask + ": ");
    }

    public void printPrompt() {
        System.out.println("\nPick a number to perform the corresponding action, " +
                "or don't, I'm not yo dad\n ");
    }

    public void printBack() {
        System.out.println("Enter 'b' to go back to previous menu.");
    }

// Other general statements
    public void printUnderConstruction() {
        System.out.println("Sorry! This feature is currently under construction.");
    }

    public void printUCReturns(Object obj) {
        System.out.println(obj);
    }

    public void printLoggedOut() {
        System.out.println("You have now logged out.");
    }

}

