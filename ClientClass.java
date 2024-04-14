import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientClass {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", 4202); // Change "localhost" to server IP if needed
        // Create OOS for writing objects
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        // Create OIS for reading objects
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream());

        // Scanner for reading user input from the terminal
        Scanner scanner = new Scanner(System.in);

        String serverMessage;

        // Keep reading server messages and interacting until "Goodbye!" is received
        while (true) {

            // Read server message and print it to the console
            serverMessage = reader.readLine();
            System.out.println("Server: " + serverMessage);

            // If the server indicates that the interaction is over, break out of the loop
            if (serverMessage.equals("Goodbye!")) {
                break;
            }

            String userInput = scanner.nextLine();
            writer.println(userInput);
            writer.flush();


        }

        // Close resources
        writer.close();
        reader.close();
        socket.close();
        scanner.close();
    }
    // open messages

    // send message

    // close messages

    // view other user

    // edit my user
}
