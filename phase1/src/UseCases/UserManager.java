package UseCases;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import Entities.User;
import Entities.UserType;

public class UserManager implements Serializable {

    private HashMap<String, User> usernamesToUsers;

    public UserManager () {
        usernamesToUsers = new HashMap<>();
    }

    public List<String> getUsernameList() {
        Collection<String> users = usernamesToUsers.keySet();
        return new ArrayList<>(users);
    }

    public boolean isRegistered(String username) {
        return usernamesToUsers.containsKey(username);
    }

    public boolean registerUser(UserType userType, String name, String username, String password) {
        if (isRegistered(username)){
            return false;
        }
        usernamesToUsers.put(username, new User(userType, name, username, password));
        return true;
    }

    public boolean verifyLogin(String username, String password) { //ask about returning null
        if (isRegistered(username)) {
            return usernamesToUsers.get(username).getPassword().equals(password);
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

    public boolean addEventAttending(String username, UUID eventId) {
        List<UUID> eventsAttending = usernamesToUsers.get(username).getEventsAttending();
        if (eventsAttending.contains(eventId)) {
            return false;
        }
        eventsAttending.add(eventId);
        usernamesToUsers.get(username).setEventsAttending(eventsAttending);
        return true;
    }

    public boolean removeEventAttending(String username, UUID eventId) {
        List<UUID> eventsAttending = usernamesToUsers.get(username).getEventsAttending();
        if (eventsAttending.remove(eventId)) {
            usernamesToUsers.get(username).setEventsAttending(eventsAttending);
            return true;
        }
        return false;
    }

    public void addFriend(String username, String friendUsername) {
        List<String> friends = usernamesToUsers.get(username).getFriends();
        friends.add(friendUsername);
        usernamesToUsers.get(username).setFriends(friends);
    }

    public String getName(String username) {
        return usernamesToUsers.get(username).getName();
    }

    public List<UUID> getEventsAttending(String username) {
        return usernamesToUsers.get(username).getEventsAttending();
    }

    public List<String> getFriends(String username) {
        return usernamesToUsers.get(username).getFriends();
    }

    public UserType getUserType(String username){
        return usernamesToUsers.get(username).getUserType();
    }

    public boolean scheduleNotOverlap(LocalDateTime existingTime, LocalDateTime newTime){
        return (!(newTime.isAfter(existingTime.minusHours(1))) || !(newTime.isBefore(existingTime.plusHours(1))));
    }
}
