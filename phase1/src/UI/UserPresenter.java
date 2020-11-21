package UI;

import Entities.UserType;

import java.sql.SQLOutput;

public class UserPresenter {
// Main Menu

    /**
     * Prints the welcome message.
     */
    public void printWelcome() {
        System.out.println("\nWelcome To The Lamest Technology Conference Ever!!!\n" +
                "\nPress 0 to exit the program\n" +
                "\nPress 1 to login to your account\n" +
                "\nPress 2 to sign up for an account\n");
    }

    /**
     * Prints the menu options common to all user types.
     */
    public void printCommonMenu() {
        printPrompt();
        System.out.println("0. Logout\n" +
                "1. Messaging");
    }

// Message Center
    /**
     * Prints the message menu options specific to attendees.
     */
    public void printCommonMessageMenu() {
        printPrompt();
        System.out.println("0. Back to previous Menu\n" +
                "1. Direct message\n" +
                "2. Delete a message\n" +
                "3. View message chats");
    }

    /**
     * Prints a request for the username that the user would like to message.
     */
    public void printAskMsgReceiver() {
        System.out.println("Enter the username you would like to message");
    }

    /**
     * Prints notification that message has been sent successfully.
     */
    public void printMessageSent() {
        System.out.println("Message sent");
    }

    /**
     * Prints a request for the inbox the user would like to see.
     */
    public void printAskWhichInbox() {
        System.out.println("Which contact inbox do you want to see? Type the username");
    }

    /**
     * Prints a  request for the message the user would like to choose.
     */
    public void printAskWhichMessage() {
        System.out.println("Which message? (Enter the number)");
    }

    /**
     * Prints a  //TODO.
     */
    public void printUserDoesNotExist() {
        System.out.println("The user you are trying to message does not exist");
    }

// Creating an account
    /**
     * Prints error message that wrong account information was submitted.
     */
    public void printWrongAccountInfo() {
        System.out.println("\nOOPS looks like you entered the wrong login information," +
                "If you get it wrong again FBI WILL BE KNOCKING ON YOUR DOOR IN 10 MINUTES\n");
    }

    /**
     * Prints request for user to select account type in signup.
     */
    public void printAskUserType() {
        System.out.println("\nPlease enter your account type.\n");
        System.out.println("0. Attendee\n" +
                "1. Organizer\n");
    }

    /**
     * Prints the usertype, username, and password for user created.
     */
    public void printUserInfo(UserType usertype, String username, String pw) {
        System.out.println("\n The information for the " + usertype + " account you just created is: " +
                "\n Username: " + username +
                "\n Password: " + pw);
    }

// Event Actions
    /**
     * Prints a user's attending events.
     *
     * @param compiled formatted string of all the aforementioned events
     * @param role the role of the user; could be "attend" or "talk at"
     */
    public void printMyEvents(String compiled, String role) {
        System.out.println("The events you have signed up to " +  role + " are: \n" + compiled);
    }

    /**
     * Prints a  request for the event the user would like to choose.
     */
    public void printAskWhichEvents() { System.out.println("Which event(s)? (Enter the number(s))"); }

    /**
     * Prints a notification that the event requested is full.
     */
    public void printEventFull() {
        System.out.println("Sorry, this event is full.");
    }

// :( There's a problem :(
    /**
     * Prints error message for invalid input.
     */
    public void printInvalidInput() {
        System.out.println("\nInvalid input, please try again.");
    }

    /**
     * Prints error message that something does not exist in the system.
     *
     * @param obj the object that does not exist; could be a user, event, room, etc.
     */
    public void printDNE(String obj) {
        System.out.println("Sorry, " + obj + " does not exist in the system.");
    }

    /**
     * Prints error message that something is unavailable.
     *
     * @param obj the object that is unavailable; could be a user, event, room, etc.
     */
    public void printObjUnavailable(String obj) {
        System.out.println("Sorry, " + obj + " is unavailable.");
    }

    /**
     * Prints error message that something already exists in the system.
     *
     * @param obj the object that already exists in the system; could be a user, event, room, etc.
     */
    public void printObjectExists(String obj) { System.out.println("\n" + obj + " already exists\n"); }

    /**
     * Prints error message that username already exists in the system and prompts another input.
     */
    public void printUsernameTaken() { System.out.println("\nSorry, this username is taken, " +
            "please try another username.\n"); }

    /**
     * Prints error message that an empty string is inputted.
     *
     * @param field is the input field
     */
    public void printInvalidField(String field){
        System.out.println("This string is not a valid " + field + ", please enter a valid " + field + ".");
    }

    /**
     * Prints error message that request has failed.
     */
    public void printUnprocessed() { System.out.println("Sorry, your request didn't go through. Please try again!"); }

// Action status
    /**
     * Prints success message.
     */
    public void printSuccess() {
        System.out.println("\nYou crazy son of a female dog, you did it.");
    }

    /**
     * Prints fail message.
     */
    public void printFail() {
        System.out.println("\nAction failed. Dishonor on you, dishonor on your cow.");
    }

// Requests
    /**
     * Prints request for input of a certain object.
     *
     * @param ask the object that requires the input information
     */
    public void printAsk(String ask) {
        System.out.println("\nPlease enter the " + ask + ": ");
    }

    /**
     * Prints request for input of a certain object with the option to go back
     * @param ask the object that requires the input information
     */
    public void printAskWithBack(String ask) {
        System.out.println("\nPlease enter the " + ask +
                ", or press enter to go back to the previous step.");
    }

    /**
     * Prints message deleted successfully
     */
    public void printMessageDeleted(){
        System.out.println("The message has been successfully deleted.");
    }

    /**
     * Prints the option to enter to go back
     */
    public void printBackToMainMenu(){
        System.out.println("Or press enter to go back to the main menu");
    }

    /**
     * Prints request for input of a number in a list of actions.
     */
    public void printPrompt() {
        System.out.println("\nPick a number to perform the corresponding action." +
                " Or don't, I'm not yo dad\n");
    }

// Other general statements
    /**
     * Prints function under construction message.
     */
    public void printUnderConstruction() {
        System.out.println("Sorry! This feature is currently under construction.");
    }

    /**
     * Prints the object
     */
    public void printUCReturns(Object obj) {
        System.out.println(obj);
    }

    /**
     * Prints a notification that user has successfully logged out.
     */
    public void printLoggedOut() {
        System.out.println("You have now logged out.");
    }

    /**
     * Prints the user has signed up successfully
     */
    public void printSignUpSuccessful(String name){
        System.out.println("Congratulations! " + name + " is now a valued member of the lamest technology conference ever!");
    }
}

