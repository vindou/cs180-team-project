import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientClass {
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
        try {
            Socket socket = new Socket("localhost", 4202); // Change "localhost" to server IP if needed

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            // Scanner for reading user input from the terminal
            Scanner scanner = new Scanner(System.in);
            System.out.println("Scanning.");

            String serverMessage = "";

            // Keep reading server messages and interacting until "Goodbye!" is received
            while (true) {
                String line = reader.readLine();
                serverMessage = serverMessage + "\n" + line;

                if (line.equals("Goodbye!")) {
                    System.out.println(line);
                    break;
                }

                // Read server message and print it to the console
                if (line != null) {
                    if (line.endsWith("end")) {
                        System.out.println("Server: " + serverMessage.substring(0, serverMessage.length() - 3));
                        serverMessage = "";
                        String userInput = scanner.nextLine();
                        writer.write(userInput);
                        writer.println();
                        writer.flush();
                    } else if (line.endsWith("pass")) {
                        System.out.println("Server: " + serverMessage.substring(0, serverMessage.length() - 4));
                        serverMessage = "";
                    }
                }
            }

            writer.close();
            reader.close();
            socket.close();
            scanner.close();
        } catch (SocketException ignored) {}
    }
}
