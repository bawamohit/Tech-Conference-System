public class Presenter {
    public void printWelcome(){
        System.out.println("Welcome to the lamest Technology conference ever!!!\n" +
                "Press 1 to login to your account\n" +
                "Press 2 to sign up for an account");
    }

    public void printAskID(){
        System.out.println("\nPlease enter your user ID");
    }
    public void printAskPassword(){
        System.out.println("Please enter your password");
    }

    public void printWrongAccountInfo(){
        System.out.println("\nOOPS looks like you entered the wrong login information," +
                "Please enter your user ID again.\n" +
                "If you get it wong again FBI WILL BE KNOCKING ON YOUR DOOR IN 10 MINUTES\n" +
                "Or enter 'sign up' to sign up for an account.");
    }

    public void printPrompt(){
        System.out.println("Pick a number to perform the corresponding action, " +
                "or don't, I'm not yo dad\n\n ");
    }

    public void printAttendeeMenu(){
        printPrompt();
        System.out.println("0. Logout\n" +
                "1. Check Message History\n" +
                "2. Slide in that DM\n" +
                "3. View All Events\n" +
                "4. View My Events\n" +
                "5. Sign-up Event\n" +
                "6. Cancel Event");
    }

    public void printOrganizerMenu(){
        printAttendeeMenu();
        System.out.println("7. Reschedule Event\n" +
                "8. Remove Event\n" +
                "9. Create Speaker Account\n" +
                "10. Create Room\n" +
                "11. Message All");
    }

    public void printSpeakerMenu(){
        printAttendeeMenu();
        System.out.println("7. View My Talks\n" +
                "8. Message My Talk Attendees");
    }

    public void printBack(){
        System.out.println("Enter 'b' to go back to previous menu");
    }
    public void printIOException(){
        System.out.println("IOException");
    }

    public void printUnderConstruction(){
        System.out.println("Sorry! This feature is currently under construction.");
    }
}
