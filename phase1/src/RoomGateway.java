import java.io.*;
import java.util.HashMap;


public class RoomGateway implements Serializable {

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
            System.out.println("Cannot read from input file, returning a new RoomManager.");
            return new RoomManager();
        }
    }

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