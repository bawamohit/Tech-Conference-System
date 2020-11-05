import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private List<User> users;

    public UserManager () {
        this.users = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User verifyLogin(String username, String password) { //ask about returning null
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
    }

    public UserType getUserType(User user){
        return user.getUserType();
    }
}
