package JSONGateways;

import UseCases.MessageManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class MessageJSONGateway {

//    public MessageManager readFromFile(String filepath) throws IOException {
//        String content = new String(Files.readAllBytes(Paths.get(filepath)));
//        JSONObject jo = new JSONObject(content);
//
//        MessageManager mm = new MessageManager();
//
//        for (Object sender : jo.names()) {
//            JSONObject jo2 = (JSONObject) jo.get((String) sender);
//            for (Object recipient : jo2.names()) {
//                JSONArray messages = jo2.getJSONArray((String) recipient);
//
//            }
//        }
//    }


    public void saveToFile(String filePath, MessageManager messageManager) throws FileNotFoundException {
        JSONObject jo = new JSONObject();
        List<String> users = messageManager.getUsers();
        for (String sender : users) {
            List<String> recipients = messageManager.getInboxes(sender);
            LinkedHashMap<String, Object> map = new LinkedHashMap<>(recipients.size());
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
