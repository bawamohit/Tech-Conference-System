public class Presenter {

    public void printWelcome(){
        System.out.println("\nWelcome to the lamest Technology conference ever!!!\n" +
                "\nPress 1 to login to your account\n" +
                "\nPress 2 to sign up for an account\n");
    }

    public void printAsk(String ask) { System.out.println("\nPlease enter your " + ask + ": "); }

    public void printInvalidInput() { System.out.println("\nInvalid input.\n");}

    public void printUsernameExists() { System.out.println("\nUsername already exists\n");}

    public void printWrongAccountInfo(){
        System.out.println("\nOOPS looks like you entered the wrong login information," +
                "Please enter your username again.\n" +
                "If you get it wong again FBI WILL BE KNOCKING ON YOUR DOOR IN 10 MINUTES\n" +
                "Or enter 'sign up' to sign up for an account.");
    }

    public void printUnderConstruction(){
        System.out.println("Sorry! This feature is currently under construction.");
    }

    public void printPrompt(){
        System.out.println("\nPick a number to perform the corresponding action, " +
                "or don't, I'm not yo dad\n ");
    }

    public void printAskUserType() {
        System.out.println("\nPlease enter your account type.\n");
        System.out.println("0. Attendee\n" +
                "1. Organizer\n" +
                "2. Speaker\n");
    }

    public void printCommonMenu() {
        printPrompt();
        System.out.println("\n0. Logout\n" +
                "1. Messaging");
    }

    public void printAttendeeMenu(){
        printCommonMenu();
        System.out.println(
                "2. View All Available Events\n" +
                "3. View My Events\n" +
                "4. Sign-up Event\n" +
                "5. Cancel Event\n");
    }

    public void printOrganizerMenu(){
        printCommonMenu();
        System.out.println(
                "2. Create Event\n" +
                "3. Reschedule Event\n" +
                "4. Remove Event\n" +
                "5. Create Speaker Account\n" +
                "6. Create Room\n");
    }

    public void printSpeakerMenu(){
        printCommonMenu();
        System.out.println("2. View My Talks\n");
    }

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

    public void printAvailableEvents(String compiled){
        System.out.println("The events available for sign-up are: \n" + compiled);
    }

    public void printSuccess(){
        System.out.println("\nYou crazy son of a female dog, you did it.");
    }

    public void printFail(){
        System.out.println("\nAction failed. Dishonor on you, dishonor on your cow.");
    }

    public void printBack(){
        System.out.println("Enter 'b' to go back to previous menu");
    }

    public void printIOException(){
        System.out.println("IOException");
    }

    public void printUCReturns(Object obj){
        System.out.println(obj);
    }

    public void printAskMsgReceiver(){
        System.out.println("Enter username you would like to message");
    }

    public void printMessageSent(){
        System.out.println("Message sent");
    }

    public void printAskWhichInbox(){
        System.out.println("Which contact inbox do you want to see? Type the username");
    }

    public void printAskWhichMessage() { System.out.println("Which message? (Enter a number)"); }

    public void printLoggedOut(){
        System.out.println("You have now logged out.");
    }

    public void printUserDoesntExist(){
        System.out.println("The user you are trying to message does not exist");
    }

    public void printUserInfo(UserType usertype, String username, String pw){
        System.out.println("\n The information for the " + usertype + " account you just created is: " +
                "\n Username: "+ username +
                "\n Password: " + pw);
    }
}
