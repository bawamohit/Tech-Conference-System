package Gateways;

import java.io.*;
import UseCases.UserManager;

/**
 * This class is one of the gateways of this program, specifically for UserManager.
 * It is used to read and write an UserManager object, by serialization.
 */
public class UserGateway {

    /**
     * Implements a method used to read the .ser file, returning an UserManager object
     *
     * @param path the file path of which this .ser file is stored.
     * @return UserManager object stored in this file.
     */
    public UserManager readFromFile(String path) throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the UserManager
            UserManager userManager = (UserManager) input.readObject();
            input.close();
            return userManager;
        } catch (IOException ex) {
            return new UserManager();
        }
    }

    /**
     * Implements a method used to save an UserManager object to a designated .ser file.
     *
     * @param filePath the file path of which this .ser file will be stored.
     * @param userManager the UserManager object we would like to save.
     */
    public void saveToFile(String filePath, UserManager userManager) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutputStream output = new ObjectOutputStream(buffer);

        // serialize the UserManager object
        output.reset();
        output.writeObject(userManager);
        output.close();
    }

}
