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
                            // CASE FOR LOGGING IN
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
                                    // MAKE SURE THEY DIDNT JUST MESS UP PASSWORD
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
                            // NEW ACCOUNT CREATION
                            do
                            {
                                boolean taken = false;
                                writer.println("Please enter a username: ");
                                String newUsername;
                                newUsername = reader.readLine();
                                if (!userData.retrieveUserData(newUsername).equals(null))
                                {
                                    taken = true;
                                    writer.println("Sorry, that username is taken.");
                                }
                            } while (taken)

                            writer.println("Please enter your name: ");
                            String newAccName;
                            newAccName = reader.readLine();

                            writer.println("Please enter your email: ");
                            String newAccEmail;
                            newAccEmail = reader.readLine();

                            writer.println("Please enter a password: ");
                            String newAccPass;
                            newAccPass = reader.readLine();

                            writer.println("Please enter your birthday: ");
                            String newAccBirth;
                            newAccBirth = reader.readLine();

                            User newAccount = new User(newAccName, newAccEmail, newUsername, newAccPass, newAccBirth)
                            // HANDOFF TO HANDLE THE LOGGED IN CASE (USED UPON LOGIN TOO)
                            handleLoggedIn(serverSocket, newAccount);
                            break;
                            // Add more cases as needed for other characters
                        default:
                            // Do nothing 
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
                writer.println("2) Start New Conversation");
                writer.println("3) Search Users");
                    // add user
                    // block user
                writer.println("4) See My Account");
                writer.println("5) Quit");

                String userChoice;
                while ((userChoice = reader.readLine()) != null) {
                    switch (Integer.parseInt(userChoice)) {
                        case 1:
                            // VIEW CONVERSATIONS FOR THIS USER
                            ArrayList<Conversation> convos = findAvailableConversations(user);
                            int convoNum = 1;
                            for (Conversation convo : convos)
                            {
                                ArrayList<User> recipients = convo.getUsers();
                                writer.println(convoNum + ". Conversation with " );
                                for (User user : recipients)
                                {
                                    writer.print(user.getUsername() + " ");
                                }
                                convoNum++;
                            }
                            // CHECK WHICH CONVO THEY WANT
                            writer.println("What conversation number would you like to open?");
                            int convoChoice;
                            convoChoice = Integer.parseInt(reader.readLine());

                            // HOPEFULLY PRINTS OUT TEXTS
                            convos.writeMessageLogs(convos.get(convoChoice-1));

                            // option to send new text 
                            writer.println("What would you like to say?");
                            String sendThis;
                            sendThis = reader.readLine();
                            if (!(sendThis.equals("")))
                                user.sendTextMessage(convos.get(convoChoice-1), userChoice);
                            
                            

                            
                        case 2:
                            // WERE STARTING A NEW CONVO
                            writer.println("What user would you like to start a new conversation with?");
                            String newConvoUser;
                            newConvoUser = reader.readLine();

                            userData.writeDatabase();
                            User userForConvo = userData.retrieveUserData(newConvoUser);



                            
                        case 3: 
                            // WE ARE SEARCHING USERS

                        case 4:
                            // WE ARE EDITING THE CURRENT USERS ACCOUNT
                            writer.println(user.getUsername());
                            writer.println(user.getName());
                            writer.println(user.getBio());
                            writer.println(user.getEmail());

                            writer.println("What would you like to do: ");
                            writer.println("1) Change Username");
                            writer.println("2) Change Name");
                            writer.println("3) Update Bio");
                            writer.println("4) Change Email");

                            int accountChoice;
                            accountChoice = reader.readLine();
                            switch Integer.parseInt(accountChoice) {
                                case 1:
                                // EDIT USERNAME
                                case 2:
                                // EDIT NAME
                                case 3:
                                // EDIT BIO
                                case 4:
                                // EDIT EMAIL

                            }


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

    // log in -- DONE

    // create account  -- DONE

    // open conversations -- DONE
        // send message
        // close messages

    // start new conversation

    // Search users
        // add user as friend
        // block user

    // edit my user
}
