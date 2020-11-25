package JSONGateways;

import UseCases.UserManager;
import org.json.JSONObject;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

public class UserJSONGateway {

    public void saveToFile(String filePath, UserManager userManager) throws FileNotFoundException {
        JSONObject jo = new JSONObject();
        for (String username : userManager.getAttendeeList()) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>(6);

            map.put("name", userManager.getName(username));
            map.put("password", userManager.getPassword(username));
            map.put("userType", userManager.getUserType(username).toString());
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
