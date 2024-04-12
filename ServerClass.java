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
                writer.flush();

                String clientChoice;
                while ((clientChoice = reader.readLine()) != null) {
                    switch (Integer.parseInt(clientChoice)) {
                        case 1:
                            // CASE FOR LOGGING IN
                            writer.println("Please enter your username: ");
                            writer.flush();
                            String username = reader.readLine();
                            User thisUser = retrieveUserData(username);

                            writer.println("Please enter your password: ");
                            writer.flush();
                            String password = reader.readLine();
                            while (true)
                            {
                                if (thisUser.checkPassword(password))
                                {
                                    writer.println("Success!");
                                    writer.flush();
                                    // Handoff to method for logged in users
                                    handleLoggedIn(serverSocket, thisUser);
                                }
                                else
                                {
                                    // MAKE SURE THEY DIDNT JUST MESS UP PASSWORD
                                    while (true)
                                    {
                                    writer.println("Incorrect password, would you like to try again? (yes / no)");
                                    writer.flush();
                                    String passAnswer;
                                    passAnswer = reader.readLine();
                                    if (passAnswer.toUpperCase().equals("NO"))
                                    {
                                        writer.println("Goodbye!");
                                        writer.flush();
                                        return;
                                    }
                                    else if (!passAnswer.toUpperCase().equals("YES"))
                                    {
                                        writer.println("Not a valid choice.");
                                        writer.flush();
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
                                writer.flush();
                                String newUsername;
                                newUsername = reader.readLine();
                                if (!userData.retrieveUserData(newUsername).equals(null))
                                {
                                    taken = true;
                                    writer.println("Sorry, that username is taken.");
                                    writer.flush();
                                }
                            } while (taken)

                            writer.println("Please enter your name: ");
                            writer.flush();
                            String newAccName;
                            newAccName = reader.readLine();

                            writer.println("Please enter your email: ");
                            writer.flush();
                            String newAccEmail;
                            newAccEmail = reader.readLine();

                            writer.println("Please enter a password: ");
                            writer.flush();
                            String newAccPass;
                            newAccPass = reader.readLine();

                            writer.println("Please enter your birthday: ");
                            writer.flush();
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
                writer.flush();

                String userChoice;
                while ((userChoice = reader.readLine()) != null) {
                    switch (Integer.parseInt(userChoice)) {
                        case 1:
                            // VIEW CONVERSATIONS FOR THIS USER
                            ArrayList<Conversation> convosForUser = findAvailableConversations(user);
                            int convoNum = 1;
                            for (Conversation convo : convosForUser)
                            {
                                ArrayList<User> recipients = convo.getUsers();
                                writer.println(convoNum + ". Conversation with " );
                                writer.flush();
                                for (User user : recipients)
                                {
                                    writer.print(user.getUsername() + " ");
                                }
                                convoNum++;
                            }
                            // CHECK WHICH CONVO THEY WANT
                            writer.println("What conversation number would you like to open?");
                            writer.flush();
                            int convoChoice;
                            convoChoice = Integer.parseInt(reader.readLine());

                            // HOPEFULLY PRINTS OUT TEXTS
                            convos.writeMessageLogs(convosForUser.get(convoChoice-1));

                            // option to send new text 
                            do {
                                writer.println("What would you like to say?");
                                writer.flush();
                                String sendThis;
                                sendThis = reader.readLine();
                                if (!(sendThis.equals("")))
                                    convosForUser.get(convoChoice-1).addMessage(new TextMessage(user, sendThis));
                                boolean again = false;

                                writer.println("Would you like to send another message? ('yes' / 'no')");
                                writer.flush();
                                String runItBack;
                                runItBack = reader.readLine();
                                if (runItBack.toUpperCase().equals("YES"))
                                    again = true;
                            } while (again);
                            
                        case 2:
                            // WE'RE STARTING A NEW CONVO
                            writer.println("What user would you like to start a new conversation with?");
                            writer.flush();
                            String newConvoUser;
                            newConvoUser = reader.readLine();

                            userData.writeDatabase();
                            User userForConvo = userData.retrieveUserData(newConvoUser);

                            // DATA FOR INSTANTIATING CONVO
                            ArrayList<User> theGuys = new ArrayList<>();
                            theGuys.add(user);
                            theGuys.add(userForConvo)

                            Conversation theNewConvo = new Conversation(theGuys, convos);

                            // sends new text 
                            writer.println("What would you like to say?");
                            writer.flush();
                            String sendThis;
                            sendThis = reader.readLine();
                            if (!(sendThis.equals("")))
                                theNewConvo.addMessage(new TextMessage(user, sendThis));

                            
                        case 3: 
                            // Search Users
                            writer.println("Enter the username to search:");
                            writer.flush();
                            String searchUsername = reader.readLine();
                            User searchedUser = userData.retrieveUserData(searchUsername);
                            if (searchedUser != null) {
                                writer.println("User found:");
                                writer.println("Username: " + searchedUser.getUsername());
                                writer.println("Name: " + searchedUser.getName());
                                writer.println("Bio: " + searchedUser.getBio());
                                writer.flush();
                                // Provide options to add as friend or block user
                                writer.println("Would you like to:");
                                writer.println("1) Add as Friend");
                                writer.println("2) Block User");
                                writer.flush();
                                String actionChoice = reader.readLine();
                                switch (Integer.parseInt(actionChoice)) {
                                    case 1:
                                        user.addFriend(searchedUser);
                                        writer.println("User added as friend.");
                                        writer.flush();
                                    case 2:
                                        user.blockFriend(searchedUser);
                                        writer.println("User blocked.");
                                        writer.flush();
                                    default:
                                        writer.println("Invalid choice.");
                                        writer.flush();
                                }
                            } else {
                                writer.println("User not found.");
                                writer.flush();
                            }

                        case 4:
                            // EDIT CLIENT USER
                            try 
                            {
                                // Present current user's account information
                                writer.println("Username: " + user.getUsername());
                                writer.println("Name: " + user.getName());
                                writer.println("Bio: " + user.getBio());
                                writer.println("Email: " + user.getEmail());
                                writer.flush();
                                
                                // Provide options to edit account
                                writer.println("What would you like to edit:");
                                writer.println("1) Change Username");
                                writer.println("2) Change Name");
                                writer.println("3) Update Bio");
                                writer.println("4) Change Email");
                                writer.flush();
                                int editChoice = Integer.parseInt(reader.readLine());
                                
                                switch (editChoice) {
                                    case 1: // Change Username
                                        writer.println("Enter new username:");
                                        writer.flush();
                                        String newUsername = reader.readLine();
                                        // Check if the new username is not already taken
                                        if (userData.retrieveUserData(newUsername) != null) {
                                            writer.println("Sorry, that username is already taken.");
                                            writer.flush();
                                        } else {
                                            user.setUsername(newUsername);
                                            writer.println("Username updated successfully.");
                                            writer.flush();
                                        }
                                    case 2: // Change Name
                                        writer.println("Enter new name:");
                                        writer.flush();
                                        String newName = reader.readLine();
                                        user.setName(newName);
                                        writer.println("Name updated successfully.");
                                        writer.flush();
                                    case 3: // Update Bio
                                        writer.println("Enter new bio:");
                                        writer.flush();
                                        String newBio = reader.readLine();
                                        user.setBio(newBio);
                                        writer.println("Bio updated successfully.");
                                        writer.flush();
                                    case 4: // Change Email
                                        writer.println("Enter new email:");
                                        writer.flush();
                                        String newEmail = reader.readLine();
                                        user.setEmail(newEmail);
                                        writer.println("Email updated successfully.");
                                        writer.flush();
                                    default:
                                        writer.println("Invalid choice.");
                                        writer.flush();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        // Add more cases as needed 
                        default:
                            writer.println("Goodbye!");
                            writer.flush();
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
        // send message -- DONE
        // close messages -- MAYBE IDRK WHAT I MEANT BY THIS 

    // start new conversation -- DONE

    // Search users -- DONE
        // add user as friend -- DONE
        // block user -- DONE

    // edit my user -- DONE
}
