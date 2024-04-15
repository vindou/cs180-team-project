import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientClass {
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", 4205); // Change "localhost" to server IP if needed

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream());

        // Scanner for reading user input from the terminal
        Scanner scanner = new Scanner(System.in);

        String serverMessage = "";

        // Keep reading server messages and interacting until "Goodbye!" is received
        while (true) {
            String line = reader.readLine();
            serverMessage = serverMessage + "\n" + line;

            // If the server indicates that the interaction is over, break out of the loop
            if (line.equals("Goodbye!")) {
                System.out.println(serverMessage);
                break;
            }

            // Read server message and print it to the console
            if (line != null) {
                if (line.endsWith("end")) {
                    System.out.println("Server: " + serverMessage.substring(0,serverMessage.length()-3));
                    serverMessage = "";
                    String userInput = scanner.nextLine();
                    writer.write(userInput);
                    writer.println();
                    writer.flush();
                }
                if (line.endsWith("pass")) {
                    System.out.println("Server: " + serverMessage.substring(0,serverMessage.length()-4));
                    serverMessage = "";
                }
            }
        }

        // Close resources
        writer.close();
        reader.close();
        socket.close();
        scanner.close();
    }
}
