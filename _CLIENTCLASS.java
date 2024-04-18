import java.io.*;
import java.net.*;


public class _CLIENTCLASS {
    public static void main(String[] args) throws ClassNotFoundException {
        try {
            System.out.println("Attempting to create socket...");
            Socket socket = new Socket("localhost", 5329);
            System.out.println("Socket created successfully");

            _USERINTERFACE userInterface = new _USERINTERFACE();
            userInterface.run();

            _CLIENTSENDER clientSender = new _CLIENTSENDER(socket, userInterface);
            userInterface.setClientSender(clientSender);
            clientSender.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
