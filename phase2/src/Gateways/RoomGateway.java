package Gateways;

import UseCases.RoomManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.UUID;

public class RoomGateway {

    public RoomManager readFromFile(String filepath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filepath)));
        JSONObject jo = new JSONObject(content);

        RoomManager rm = new RoomManager();

        if (jo.isEmpty())
            return rm;

        for (String roomName : JSONObject.getNames(jo)) {
            JSONObject info = (JSONObject) jo.get(roomName);

            rm.addRoom(roomName, (Integer) info.get("capacity"));

            JSONObject schedule = (JSONObject) info.get("schedule");

            if (schedule.isEmpty())
                continue;

            for (Object id : schedule.names()) {
                UUID eventId = UUID.fromString((String) id);
                JSONArray timing = (JSONArray) schedule.get((String) id);
                LocalDateTime startTime = LocalDateTime.parse(timing.getString(0));
                LocalDateTime endTime = LocalDateTime.parse(timing.getString(1));
                rm.addEventToSchedule(eventId, roomName, startTime, endTime);
            }
        }

        return rm;
    }

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
