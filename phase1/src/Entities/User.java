package Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class is one of the entity classes for this program, specifically for user.
 *
 */
public class User implements Serializable {

    private String name;
    private String username;
    private String password;
    private UserType userType;
    private List<UUID> eventsAttending;
    private List<String> friends;

    /** Creates an instance of User
     *
     * @param userType Attendee, Organizer, or Speaker
     * @param name Name of the User
     * @param username Login username of the User
     * @param password Login Password of the User
     */
    public User(UserType userType, String name, String username, String password) {
        this.userType = userType;
        this.name = name;
        this.username = username;
        this.password = password;
        this.eventsAttending = new ArrayList<>();
        this.friends = new ArrayList<>();
    }

    /** Setter for name of User
     *
     * @param name Name of user
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Setter for login password User
     *
     * @param password Password of User
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /** Setter for List of event names
     *
     * @param eventsAttending List of event names
     */
    public void setEventsAttending(List<UUID> eventsAttending) {
        this.eventsAttending = eventsAttending;
    }

    /** Setter for List of friend usernames
     *
     * @param friends List of friend usernames
     */
    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    /** Returns the name of the User
     *
     * @return Name of User
     */
    public String getName() {
        return name;
    }

    /** Returns the login username of the User
     *
     * @return username of User
     */
    public String getUsername() {
        return username;
    }

    /** Returns the login password of the User
     *
     * @return password of the User
     */
    public String getPassword() {
        return password;
    }

    /** Returns the type of User - Attendee, Organizer, or Speaker
     *
     * @return Type of User
     */
    public UserType getUserType() {
        return userType;
    }

    /** Returns the list of event names that the User is attending, organizing, or speaking at.
     *
     * @return User's list of event names
     */
    public List<UUID> getEventsAttending() {
        return eventsAttending;
    }

    /** Returns the list of friends that the user can message
     *
     * @return List of User's friends
     */
    public List<String> getFriends() {
        return friends;
    }
}

