package Gateways;
import UseCases.EventManager;

import java.io.*;

/**
 * This class is one of the gateways of this program, specifically for EventManager.
 * It is used to read and write an EventManager object, by serialization.
 */
public class EventGateway {

    /**
     * Implements a method used to read the .ser file, returning an EventManager object
     *
     * @param path the file path of which this .ser file is stored.
     * @return EventManager object stored in this file.
     */
    public EventManager readFromFile(String path) throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(path); //the path needs to be a .ser so eventData.ser in parameter
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the EventManager
            EventManager em = (EventManager) input.readObject();
            input.close();
            return em;
        } catch (IOException ex) {
            return new EventManager();
        }
    }

    /**
     * Implements a method used to save an EventManager object to a designated .ser file.
     *
     * @param filePath the file path of which this .ser file will be stored.
     * @param eventManager the EventManager object we would like to save.
     */
    public void saveToFile(String filePath, EventManager eventManager) throws IOException {

        OutputStream file = new FileOutputStream(filePath); //the path needs to be a .ser so eventData.ser in parameter
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutputStream output = new ObjectOutputStream(buffer);

        // serialize the EventManager object
        output.reset();
        output.writeObject(eventManager);
        output.close();
    }
}