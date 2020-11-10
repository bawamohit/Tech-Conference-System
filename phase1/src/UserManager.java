import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UserManager {

    private HashMap<String, User> usernamesToUsers;

    public UserManager () {
        usernamesToUsers = new HashMap<>();
    }

    public List<User> getUserList() {
        return (List<User>) usernamesToUsers.values();
    }

    public List<String> getUsernameList() {
        return (List<String>) usernamesToUsers.keySet();
    }

    public void setUsernamesToUsers(HashMap<String, User> newMap) {
        usernamesToUsers = newMap;
    }

    private boolean isRegistered(String username) {
        return usernamesToUsers.containsKey(username);
    }

    public boolean registerUser(User user) {
        if (isRegistered(user.getUsername())){
            return false;
        }
        usernamesToUsers.put(user.getUsername(), user);
        return true;
    }

    public boolean verifyLogin(String username, String password) { //ask about returning null
        if (isRegistered(username)) {
            if (usernamesToUsers.get(username).getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public boolean updateName(String username, String newName) {
        if (isRegistered(username)) {
            usernamesToUsers.get(username).setName(newName);
            return true;
        }
        return false;
    }

    public boolean updatePassword(String username, String newPassword) {
        if (isRegistered(username)) {
            usernamesToUsers.get(username).setPassword(newPassword);
            return true;
        }
        return false;
    }

    public UserType getUserType(String username){
        return usernamesToUsers.get(username).getUserType();
    }

}
