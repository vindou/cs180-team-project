import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServerClass implements Server {

    // start up databases
    public static ArrayList<User> userArray = new ArrayList<User>;
    public static UserDatabase userData = new UserDatabase(userArray, "userData.txt");
    public static ConversationDatabase convos = new ConversationDatabase("conversationData.txt")

    public static void main(String[] args) throws IOException {
        // Create a ServerSocket to listen for incoming connections
        ServerSocket serverSocket = new ServerSocket(4202); // Choose an appropriate port
        
        // Keep listening for connections as long as the server is running
        while (true) {
            // Accept a new client connection
            // System.out.println's will be removed later, currently just for testing
            System.out.println("Waiting for the client to connect...");
            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");
            
            // Handle the client connection in a separate thread
            new Thread(() -> handleClient(serverSocket)).start();
        }
    }
    
    private static void handleClient(Socket serverSocket) {
        try {
            // Create OOS for writing objects
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            // Create OIS for reading objects
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            
            // Example of reading data from the client
            String clientMessage;
            while ((clientMessage = reader.readLine()) != null) {
                System.out.println("Received from client: " + clientMessage);
                
                // Example of sending a response back to the client
                writer.println("Server received: " + clientMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the client socket when done
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
