package UseCases;

import java.util.*;
import Entities.User;
import Entities.UserType;

/** Handles user related functions of this program.
 */
public class UserManager {

    private HashMap<String, User> usernamesToUsers;

    /** Creates an instance of UserManager
     */
    public UserManager () {
        usernamesToUsers = new HashMap<>();
    }

    /** Creates a list of usernames of all registered users and returns it.
     *
     * @return List of usernames
     */
    public List<String> getUsernameList() {
        Collection<String> users = usernamesToUsers.keySet();
        return new ArrayList<>(users);
    }

    //TODO javadoc
    public List<String> getUserList(UserType userType){
        ArrayList<String> userList = new ArrayList<>();

        for(String username : getUsernameList()){
            if (getUserType(username) == userType) {
                userList.add(username);
            }
        }

        return userList;
    }

    public boolean isNotUserType(String username, UserType userType){
        return !getUserList(userType).contains(username);
    }

    /** Determines whether the given username is registered in this userManager
     *
     * @param username A string object that represents the username to be checked
     *
     * @return true if the username is registered, and false otherwise
     */
    public boolean isRegistered(String username) {
        return usernamesToUsers.containsKey(username);
    }

    /**
     * Creates a new user object and add it to the usernamesToUsers hashmap iff the given username is not taken.
     *
     * @param userType The type of the user to be created.
     * @param name The name of the user to be created.
     * @param username The username of the user to be created
     * @param password The pass of the user to be created.
     */
    public void registerUser(UserType userType, String name, String username, String password) {
        usernamesToUsers.put(username, new User(userType, name, username, password));
    }

    /** Checks if the given username and password match the login credentials of a registered user.
     *
     * @param username A string object that represents the username to be checked
     * @param password A string object that represents the password to be checked
     *
     * @return true if there exists a user with this exact username and password, and false otherwise.
     */
    public boolean verifyLogin(String username, String password) { //ask about returning null
        if (isRegistered(username)) {
            return usernamesToUsers.get(username).getPassword().equals(password);
        }
        return false;
    }

    /** Updates the name of this user.
     *
     * @param username The username of the user whose name shall be updated
     * @param newName The name that shall replace the user's old name.
     *
     * @return True if the update was performed successfully, and false otherwise.
     */
    public boolean updateName(String username, String newName) {
        if (isRegistered(username)) {
            usernamesToUsers.get(username).setName(newName);
            return true;
        }
        return false;
    }

    /** Changes the password of the user
     *
     * @param username Username of the user whose password is to be changed
     * @param newPassword The new password of the user
     * @return true if the password is changed successfully
     */
    public boolean updatePassword(String username, String newPassword) {
        if (isRegistered(username)) {
            usernamesToUsers.get(username).setPassword(newPassword);
            return true;
        }
        return false;
    }

    /** Adds an event id to a desired user's list of events that they are signed up for.
     *
     * @param username The username of the user who is signing up for an event.
     * @param eventId The id of the event that the user is being signed up for.
     */
    public void addEventAttending(String username, UUID eventId) {
        List<UUID> eventsAttending = usernamesToUsers.get(username).getEventsAttending();
        eventsAttending.add(eventId);
        usernamesToUsers.get(username).setEventsAttending(eventsAttending);
    }

    /** Removes an event id from a particular user's list of events that they are signed up for.
     *
     * @param username The username of the user who is removing this event.
     * @param eventId The id of the event that is being removed.
     */
    //TODO change javadoc accordingly
    public void removeEventAttending(String username, UUID eventId) {
        List<UUID> eventsAttending = usernamesToUsers.get(username).getEventsAttending();
        eventsAttending.remove(eventId);
        usernamesToUsers.get(username).setEventsAttending(eventsAttending);
    }

    /** Adds a friend to the list of friends that a particular user has
     *
     * @param username The username of the user who is adding a friend
     * @param friendUsername The username of the friend that is being added
     *
     */
    public void addFriend(String username, String friendUsername) {
        List<String> friends = usernamesToUsers.get(username).getFriends();
        friends.add(friendUsername);
        usernamesToUsers.get(username).setFriends(friends);
    }

    /** Gets the name of a particular user.
     *
     * @param username The username of the user whose name is being retrieved.
     *
     * @return a string that is the name of the user associated with the username.
     */
    public String getName(String username) {
        return usernamesToUsers.get(username).getName();
    }

    /** Gets the password of a particular user.
     *
     * @param username The username of the user whose password is being retrieved.
     *
     * @return a string that is the password of the user associated with the username.
     */
    public String getPassword(String username) {
        return usernamesToUsers.get(username).getPassword();
    }

    /** Gets the list of event ids of the events that a particular user is attending.
     *
     * @param username The username of the user whose event list is being retrieved.
     *
     * @return the list of the event ids
     */
    public List<UUID> getEventsAttending(String username) {
        return usernamesToUsers.get(username).getEventsAttending();
    }

    /** Gets a list of usernames of the friends of a particular user.
     *
     * @param username The username of the user whose friend list is being retrieved.
     *
     * @return the list of the user's friends
     */
    public List<String> getFriends(String username) {
        return usernamesToUsers.get(username).getFriends();
    }

    /** Gets the type (Attendee, Organizer, or Speaker) of a particular user.
     *
     * @param username The username of the user whose type is being retrieved.
     *
     * @return the Usertype of the user associated with username
     */
    public UserType getUserType(String username){
        return usernamesToUsers.get(username).getUserType();
    }
}
