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
import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * Reading and writing to JSON files for the RoomManager
 */
public class RoomGateway {

    /** Returns a RoomManager object after reading from the room manager JSON file
     *
     * @param filepath The filepath of the room manager JSON file
     * @return RoomManager created from the data in the JSON file
     * @throws IOException Throws exception if problems reading from file
     */
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

    /** Saves a the data in the RoomManager in a JSON file
     *
     * @param filePath The filepath where we want to save the JSON file
     * @param roomManager The RoomManager that we want to save
     * @throws FileNotFoundException Error when creating or opening the JSON file at the filepath
     */
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
