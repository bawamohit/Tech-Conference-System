package Gateways;

import java.io.*;

public class Gateway<T> {
    public T readFromFile(String path, Class<T> managerType) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        try {
            InputStream file = new FileInputStream(path); //the path needs to be a .ser so eventData.ser in parameter
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the Manager
            T manager = (T)input.readObject();
            input.close();
            return manager;
        } catch (IOException ex) {
            System.out.println("Cannot read from input file, returning a new MessageManager.");
            return managerType.newInstance();
        }
    }

    public void saveToFile(String filePath, T manager) throws IOException {

        OutputStream file = new FileOutputStream(filePath); //the path needs to be a .ser so eventData.ser in parameter
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutputStream output = new ObjectOutputStream(buffer);

        // serialize the manager object
        output.reset();
        output.writeObject(manager);
        output.close();
    }
}
