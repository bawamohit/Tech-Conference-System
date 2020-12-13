package Gateways;

import Entities.UserType;
import UseCases.UserManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * Reading and writing to JSON files for the UserManager
 */
public class UserGateway {

    /** Returns a UserManager object after reading from the user manager JSON file
     *
     * @param filepath The filepath of the user manager JSON file
     * @return UserManager created from the data in the JSON file
     * @throws IOException Throws exception if problems reading from file
     */
    public UserManager readFromFile(String filepath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filepath)));
        JSONObject jo = new JSONObject(content);

        UserManager um = new UserManager();

        if (jo.isEmpty())
            return um;

        for (String username : JSONObject.getNames(jo)) {
            JSONObject info = (JSONObject) jo.get(username);
            UserType userType = UserType.valueOf((String) info.get("userType"));

            um.registerUser(userType, (String) info.get("name"), username, (String) info.get("password"));

            JSONArray eventIds = (JSONArray) info.get("eventsAttending");
            for (Object eventId : eventIds) {
                um.addEventAttending(username, UUID.fromString((String) eventId));
            }
            JSONArray friends = (JSONArray) info.get("friends");
            for (Object friend : friends) {
                um.addFriend(username, (String) friend);
            }
        }

        return um;
    }

    /** Saves a the data in the UserManager in a JSON file
     *
     * @param filePath The filepath where we want to save the JSON file
     * @param userManager The UserManager that we want to save
     * @throws FileNotFoundException Error when creating or opening the JSON file at the filepath
     */
    public void saveToFile(String filePath, UserManager userManager) throws FileNotFoundException {
        JSONObject jo = new JSONObject();
        for (String username : userManager.getUsernameList()) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>(6);

            map.put("userType", userManager.getUserType(username).toString());
            map.put("name", userManager.getName(username));
            map.put("password", userManager.getPassword(username));
            map.put("eventsAttending", userManager.getEventsAttending(username));
            map.put("friends", userManager.getFriends(username));

            jo.put(username, map);
        }

        PrintWriter pw = new PrintWriter(filePath);
        pw.write(jo.toString(4));

        pw.flush();
        pw.close();
    }
}
