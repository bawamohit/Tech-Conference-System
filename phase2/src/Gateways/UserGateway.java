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

public class UserGateway {

    public UserManager readFromFile(String filepath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filepath)));
        JSONObject jo = new JSONObject(content);

        UserManager um = new UserManager();

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
