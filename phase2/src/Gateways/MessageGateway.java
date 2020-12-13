package Gateways;

import UseCases.MessageManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Reading and writing to JSON files for the MessageManager
 */
public class MessageGateway {

    /** Returns a MessageManager object after reading from the message manager JSON file
     *
     * @param filepath The filepath of the message manager JSON file
     * @return MessageManager created from the data in the JSON file
     * @throws IOException Throws exception if problems reading from file
     */
    public MessageManager readFromFile(String filepath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filepath)));
        JSONObject jo = new JSONObject(content);

        MessageManager mm = new MessageManager();

        if (jo.isEmpty())
            return mm;

        for (Object sender : jo.names()) {
            JSONObject jo2 = (JSONObject) jo.get((String) sender);
            if (jo2.isEmpty()) continue;
            for (Object receiver : jo2.names()) {
                JSONArray messages = jo2.getJSONArray((String) receiver);
                if (messages == null) continue;
                for (Object message : messages) {
                    JSONObject messageInfo = (JSONObject) message;
                    LocalDateTime time = LocalDateTime.parse((CharSequence) messageInfo.get("time"));

                    if (messageInfo.get("sender").equals(sender)) {
                        mm.addToSenderChat((String) sender,
                                (String) receiver,
                                (String) messageInfo.get("content"),
                                time);
                    }
                    else {
                        mm.addToReceiverChat((String) receiver,
                                (String) sender,
                                (String) messageInfo.get("content"),
                                time);
                    }
                }
            }
        }

        return mm;
    }

    /** Saves a the data in the MessageManager in a JSON file
     *
     * @param filePath The filepath where we want to save the JSON file
     * @param messageManager The MessageManager that we want to save
     * @throws FileNotFoundException Error when creating or opening the JSON file at the filepath
     */
    public void saveToFile(String filePath, MessageManager messageManager) throws FileNotFoundException {
        JSONObject jo = new JSONObject();
        List<String> users = messageManager.getUsers();
        for (String sender : users) {
            List<String> recipients = messageManager.getInboxes(sender);
            Map<String, Object> map = new LinkedHashMap<>(recipients.size());
            for (String recipient : recipients) {
                map.put(recipient, messageManager.getInbox(sender, recipient));
            }
            jo.put(sender, map);
        }

        PrintWriter pw = new PrintWriter(filePath);
        pw.write(jo.toString(4));

        pw.flush();
        pw.close();
    }
}
