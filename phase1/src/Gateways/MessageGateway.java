package Gateways;

import java.io.*;
import UseCases.MessageManager;

/**
 * This class is one of the gateways of this program, specifically for MessageManager.
 * It is used to read and write an MessageManager object, by serialization.
 */
public class MessageGateway {

    /**
     * Implements a method used to read the .ser file, returning an MessageManager object
     *
     * @param path the file path of which this .ser file is stored.
     * @return MessageManager object stored in this file.
     */
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

    /**
     * Implements a method used to save an MessageManager object to a designated .ser file.
     *
     * @param filePath the file path of which this .ser file will be stored.
     * @param messageManager the MessageManager object we would like to save.
     */
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
