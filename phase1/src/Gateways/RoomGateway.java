package Gateways;

import java.io.*;
import UseCases.RoomManager;

/**
 * This class is one of the gateways of this program, specifically for RoomManager.
 * It is used to read and write an RoomManager object, by serialization.
 */
public class RoomGateway {

    /**
     * Implements a method used to read the .ser file, returning an RoomManager object
     *
     * @param path the file path of which this .ser file is stored.
     * @return RoomManager object stored in this file.
     */
    public RoomManager readFromFile(String path) throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(path); //the path needs to be a .ser so eventData.ser in parameter
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the EventManager
            RoomManager rm = (RoomManager) input.readObject();
            input.close();
            return rm;
        } catch (IOException ex) {
            return new RoomManager();
        }
    }

    /**
     * Implements a method used to save an RoomManager object to a designated .ser file.
     *
     * @param filePath the file path of which this .ser file will be stored.
     * @param roomManager the RoomManager object we would like to save.
     */
    public void saveToFile(String filePath, RoomManager roomManager) throws IOException {

        OutputStream file = new FileOutputStream(filePath); //the path needs to be a .ser so eventData.ser in parameter
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutputStream output = new ObjectOutputStream(buffer);

        // serialize the RoomManager object
        output.reset();
        output.writeObject(roomManager);
        output.close();
    }
}