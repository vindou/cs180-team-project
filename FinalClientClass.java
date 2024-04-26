import java.io.*;
import java.net.*;


public class FinalClientClass {
    public static void main(String[] args) throws ClassNotFoundException {
        try {
            System.out.println("Attempting to create socket...");
            Socket socket = new Socket("localhost", 5329);
            System.out.println("Socket created successfully");

            FinalUserInterface userInterface = new FinalUserInterface();
            userInterface.run();

            FinalClientSender clientSender = new FinalClientSender(socket, userInterface);
            userInterface.setClientSender(clientSender);
            clientSender.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
