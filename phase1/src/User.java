import java.util.List;

public class User {

    private String name;
    private String username;
    private String password;
    private UserType userType;
    private List<Event> eventsAttending;
    private List<User> friends;

    public User(UserType userType, String name, String username, String password) {
        this.userType = userType;
        this.name = name;
        this.username = username;
        this.password = password;
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
}

