package Gateways;

import java.io.*;
import UseCases.UserManager;

public class UserGateway {
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
