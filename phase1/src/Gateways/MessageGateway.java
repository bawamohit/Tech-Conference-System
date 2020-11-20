package Gateways;

import java.io.*;
import UseCases.MessageManager;

public class MessageGateway {
    public MessageManager readFromFile(String path) throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the MessageManager
            MessageManager messageManager= (MessageManager) input.readObject();
            input.close();
            return messageManager;
        } catch (IOException ex) {
            return new MessageManager();
        }
    }

    public void saveToFile(String filePath, MessageManager messageManager) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutputStream output = new ObjectOutputStream(buffer);

        // serialize the messageManager object
        output.reset();
        output.writeObject(messageManager);
        output.close();
    }
}
