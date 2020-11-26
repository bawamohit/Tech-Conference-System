package JSONGateways;

import UseCases.EventManager;

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

public class EventJSONGateway {

    public EventManager readFromFile(String filepath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filepath)));
        JSONObject jo = new JSONObject(content);

        EventManager em = new EventManager();

        for (String eventId : JSONObject.getNames(jo)) {
            JSONObject info = (JSONObject) jo.get(eventId);
            LocalDateTime startTime = LocalDateTime.parse((CharSequence) info.get("startTime"));
            UUID id = UUID.fromString(eventId);

            em.addEvent((String) info.get("eventName"), (String) info.get("speaker"), (String) info.get("organizer"),
                        startTime, (String) info.get("roomName"), (Integer) info.get("maxCapacity"), id);

            JSONArray attendees = (JSONArray) info.get("attendees");

            for (Object attendee: attendees) {
                em.addAttendee((String) attendee, id);
            }
        }

        return em;
    }

    public void saveToFile(String filePath, EventManager eventManager) throws FileNotFoundException {
        JSONObject jo = new JSONObject();
        for (UUID id : eventManager.getEvents()) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>(7);

            map.put("eventName", eventManager.getEventName(id));
            map.put("speaker", eventManager.getEventSpeaker(id));
            map.put("organizer", eventManager.getEventOrganizer(id));
            map.put("attendees", eventManager.getEventAttendees(id));
            map.put("startTime", eventManager.getEventStartTime(id));
            map.put("roomName", eventManager.getEventRoomName(id));
            map.put("maxCapacity", eventManager.getEventMaxCapacity(id));

            jo.put(id.toString(), map);
        }

        PrintWriter pw = new PrintWriter(filePath);
        pw.write(jo.toString(4));

        pw.flush();
        pw.close();
    }
}
