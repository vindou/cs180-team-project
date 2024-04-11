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
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream()); 
            
                writer.println("What would you like to do: ");
                writer.println("1) Log In");
                writer.println("2) Create New Account");

                String clientChoice;
                while ((clientChoice = reader.readLine()) != null) {
                    switch (Integer.parseInt(clientChoice)) {
                        case 1:
                            writer.println("Please enter your username: ");
                            String username = reader.readLine();
                            User thisUser = retrieveUserData(username);

                            writer.println("Please enter your password: ");
                            String password = reader.readLine();
                            while (true)
                            {
                                if (thisUser.checkPassword(password))
                                {
                                    writer.println("Success!");
                                    // Handoff to method for logged in users
                                    handleLoggedIn(serverSocket, thisUser);
                                }
                                else
                                {
                                    while (true)
                                    {
                                    writer.println("Incorrect password, would you like to try again? (yes / no)");
                                    String passAnswer;
                                    passAnswer = reader.readLine();
                                    if (passAnswer.toUpperCase().equals("NO"))
                                    {
                                        writer.println("Goodbye!");
                                        return;
                                    }
                                    else if (!passAnswer.toUpperCase().equals("YES"))
                                    {
                                        writer.println("Not a valid choice.");
                                        return;
                                    }
                                    else
                                        break;
                                    }
                                }
                            }

                            break;
                        case 2:
                            current.setC(replacement);
                            break;
                        // Add more cases as needed for other characters
                        default:
                            // Do nothing if the character doesn't match
                            break;
                    }
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

    private static void handleLoggedIn(Socket serverSocket, User user) {
        try {
            // Create OOS for writing objects
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            // Create OIS for reading objects
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                writer.println("What would you like to do: ");
                writer.println("1) View Conversations");
                    // send message
                    // close conversations
                writer.println("2) Start New Conversation ");
                writer.println("3) Search Users");
                    // add user
                    // block user

                String userChoice;
                while ((userChoice = reader.readLine()) != null) {
                    switch (Integer.parseInt(userChoice)) {
                        case 1:
                            ArrayList<Conversation> convos = findAvailableConversations(user);
                            int convoNum = 1;
                            for (Conversation convo : convos)
                            {
                                ArrayList<User> recipients = convo.getUsers();
                                writer.println(convoNum + ") Conversation with " )
                                for (User user : recipients)
                                {
                                    writer.print(user.getUsername() + " ");
                                }
                                convoNum++;
                            }

                            

                            
                        case 2:
                            // something
                            
                        // Add more cases as needed 
                        default:
                            writer.println("Goodbye!");
                            break;
                    }
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

    // log in

    // create account

    // open conversation
        // send message
        // close messages

    // start new conversation

    // Search users
        // add user as friend
        // block user

    // edit my user
}
