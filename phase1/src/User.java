import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private String username;
    private String password;
    private UserType userType;
    private List<String> eventsAttending;
    private List<User> friends;

    public User(UserType userType, String name, String username, String password) {
        this.userType = userType;
        this.name = name;
        this.username = username;
        this.password = password;
        this.eventsAttending = new ArrayList<>();
        this.friends = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEventsAttending(List<String> eventsAttending) {
        this.eventsAttending = eventsAttending;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }

    public List<String> getEventsAttending() {
        return eventsAttending;
    }

    public List<User> getFriends() {
        return friends;
    }
}

