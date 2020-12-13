package Gateways;

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

/**
 * Reading and writing to JSON files for the EventManager
 */
public class EventGateway {

    /** Returns a EventManager object after reading from the event manager JSON file
     *
     * @param filepath The filepath of the event manager JSON file
     * @return EventManager created from the data in the JSON file
     * @throws IOException Throws exception if problems reading from file
     */
    public EventManager readFromFile(String filepath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filepath)));
        JSONObject jo = new JSONObject(content);

        EventManager em = new EventManager();

        if (jo.isEmpty())
            return em;

        for (String eventId : JSONObject.getNames(jo)) {
            JSONObject info = (JSONObject) jo.get(eventId);
            LocalDateTime startTime = LocalDateTime.parse((CharSequence) info.get("startTime"));
            LocalDateTime endTime = LocalDateTime.parse((CharSequence) info.get("endTime"));
            UUID id = UUID.fromString(eventId);

            em.addEvent((String) info.get("eventName"), (String) info.get("organizer"),
                    startTime, endTime, (String) info.get("roomName"), (Integer) info.get("maxCapacity"), id);

            JSONArray attendees = (JSONArray) info.get("attendees");

            for (Object attendee: attendees) {
                em.addAttendee(id, (String) attendee);
            }

            JSONArray speakers = (JSONArray) info.get("speakers");
            for (Object speaker : speakers) {
                em.addSpeaker(id, (String) speaker);
            }
        }

        return em;
    }

    /** Saves a the data in the EventManager in a JSON file
     *
     * @param filePath The filepath where we want to save the JSON file
     * @param eventManager The EventManager that we want to save
     * @throws FileNotFoundException Error when creating or opening the JSON file at the filepath
     */
    public void saveToFile(String filePath, EventManager eventManager) throws FileNotFoundException {
        JSONObject jo = new JSONObject();
        for (UUID id : eventManager.getEvents()) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>(7);

            map.put("eventName", eventManager.getEventName(id));
            map.put("speakers", eventManager.getEventSpeaker(id));
            map.put("organizer", eventManager.getEventOrganizer(id));
            map.put("attendees", eventManager.getEventAttendees(id));
            map.put("startTime", eventManager.getEventStartTime(id));
            map.put("endTime", eventManager.getEventEndTime(id));
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
