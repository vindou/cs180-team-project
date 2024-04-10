import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientClass {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8080); // Change "localhost" to server IP if needed
        
        try {
            // Create OOS for writing objects
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            // Create OIS for reading objects
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            
            // Create and send object to server
            MyObject objectToSend = new MyObject(/* Initialize your object */);
            objectOutputStream.writeObject(objectToSend);
            objectOutputStream.flush();
            
            // Read response object from server
            Object receivedObject = objectInputStream.readObject();
            System.out.println("Received object from server: " + receivedObject);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    
    // open messages

        
    // send message

    // close messages

    // view other user

    // edit my user
}
