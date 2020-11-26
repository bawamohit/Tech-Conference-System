package JSONGateways;

import UseCases.RoomManager;

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

public class RoomJSONGateway {

    public void saveToFile(String filePath, RoomManager roomManager) throws FileNotFoundException {
        JSONObject jo = new JSONObject();
        for (String room : roomManager.getRooms()) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>(3);

            map.put("capacity", roomManager.getRoomCapacity(room));
            map.put("schedule", roomManager.getRoomSchedule(room));

            jo.put(room, map);
        }

        PrintWriter pw = new PrintWriter(filePath);
        pw.write(jo.toString(4));

        pw.flush();
        pw.close();
    }
}
