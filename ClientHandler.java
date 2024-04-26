import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is intended to instantiate an object
 * that handles server interactions with a client.
 *
 * @author Ellie Williams, Jack Juncker
 * @version April 1st, 2024.
 */

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private static ArrayList<Object> userArray = new ArrayList<>();
    private static UserDatabase userData;
    private static ConversationDatabase convos;

    public ClientHandler(Socket clientSocket, UserDatabase userData, ConversationDatabase convos) {
        this.clientSocket = clientSocket;
        ClientHandler.userData = userData;
        ClientHandler.convos = convos;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());

            writer.println("What would you like to do: \n1) Log In\n2) Create New Accountend");
            writer.flush();

            String clientChoice;
            outerloop:
            while (true) {
                clientChoice = reader.readLine();
                switch (Integer.parseInt(clientChoice)) {
                    case 1:
                        // CASE FOR LOGGING IN
                        boolean invalidUsername = false;
                        User thisUser = new User();
                        do {
                            writer.println("Please enter your username: end");
                            writer.flush();
                            String username = reader.readLine();
                            try {
                                thisUser = userData.retrieveUserData(username);
                                invalidUsername = false;
                            } catch (ActionNotAllowedException e) {
                                invalidUsername = true;
                                writer.println("User not found, try again? (yes / no)end");
                                writer.flush();

                                String passAnswer = reader.readLine();
                                if (passAnswer.equalsIgnoreCase("NO")) {
                                    writer.println("Goodbye!");
                                    writer.flush();
                                    break outerloop;
                                } else if (!passAnswer.equalsIgnoreCase("YES")) {
                                    writer.println("Not a valid choice.pass");
                                    writer.flush();
                                }
                            }
                        } while (invalidUsername);

                        if (!invalidUsername) {
                            writer.println("Please enter your password: end");
                            writer.flush();
                            String password = reader.readLine();
                            while (true) {
                                if (thisUser.checkPassword(password)) {
                                    writer.println("Success!pass");
                                    writer.flush();
                                    // Handoff to method for logged in users
                                    handleLoggedIn(clientSocket, thisUser); // Fixed parameter
                                } else {
                                    // MAKE SURE THEY DIDNT JUST MESS UP PASSWORD
                                    while (true) {
                                        writer.println("Incorrect password, would you like to try again? (yes / no)end");
                                        writer.flush();
                                        String passAnswer;
                                        passAnswer = reader.readLine();
                                        if (passAnswer.equalsIgnoreCase("NO")) {
                                            writer.println("Goodbye!");
                                            writer.flush();
                                            break outerloop;
                                        } else if (!passAnswer.equalsIgnoreCase("YES")) {
                                            writer.println("Not a valid choice.end");
                                            writer.flush();
                                        }
                                    }
                                }
                            }
                        }
                    case 2:
                        // NEW ACCOUNT CREATION
                        writer.println("Please enter a username: end");
                        writer.flush();
                        String newUsername = reader.readLine();

                        writer.println("Please enter your name: end");
                        writer.flush();
                        String newAccName = reader.readLine();

                        writer.println("Please enter your email: end");
                        writer.flush();
                        String newAccEmail = reader.readLine();

                        writer.println("Please enter a password: end");
                        writer.flush();
                        String newAccPass = reader.readLine();

                        writer.println("Please enter your birthday: end");
                        writer.flush();
                        String newAccBirth = reader.readLine();

                        User newAccount = new User(newAccName, newAccEmail, newUsername, newAccPass, newAccBirth);
                        userArray.add(newAccount);
                        userData = new UserDatabase(userArray, "userData.txt");
                        // HANDOFF TO HANDLE THE LOGGED IN CASE (USED UPON LOGIN TOO)
                        handleLoggedIn(clientSocket, newAccount); // Fixed parameter
                        break;
                    // Add more cases as needed for other characters
                    default:
                        writer.println("Sorry, there appears to have been an error.end");
                        break;
                }
            }
        } catch (IOException ignored) {}

        try {

            clientSocket.close();
        } catch (IOException ignored) {}
    }

    private static void handleLoggedIn(Socket socket, User user) { // Fixed parameter
        System.out.println("SUCCESSFULLY ENTERED LOGGED IN PHASE");
        try {

            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String userChoice;
            outerloop:
            while (true) {
                writer.println("What would you like to do: \n" +
                    "1) View Conversations\n" +
                    "2) Start New Conversation\n" +
                    "3) Search Users\n" +
                    "4) See My Account\n" +
                    "5) Quitend");
                // search should have add user and block user
                writer.flush();
                userChoice = reader.readLine();
                switch (Integer.parseInt(userChoice)) {
                    case 1:
                        // VIEW CONVERSATIONS FOR THIS USER
                        ArrayList<Conversation> convosForUser = convos.findAvailableConversations(user);
                        if (convosForUser.size() > 0) {
                            int convoNum = 1;
                            for (Conversation convo : convosForUser) {
                                ArrayList<User> recipients = convo.getUsers();
                                writer.println(convoNum + ". Conversation with end");
                                writer.flush();
                                String names = "";
                                for (User recipient : recipients) { // Fixed variable name
                                    names = names + (recipient.getUsername() + " "); // Fixed variable name
                                }
                                writer.println(names + "end");
                                convoNum++;
                            }

                            // CHECK WHICH CONVO THEY WANT
                            writer.println("What conversation number would you like to open?end");
                            writer.flush();
                            int convoChoice;
                            convoChoice = Integer.parseInt(reader.readLine());

                            // HOPEFULLY PRINTS OUT TEXTS
                            convos.writeMessageLogs(convosForUser.get(convoChoice - 1));

                            // option to send new text
                            boolean again = false;
                            innerloop:
                            do {
                                writer.println("What would you like to say?end");
                                writer.flush();
                                String sendThis;
                                sendThis = reader.readLine();
                                if (!(sendThis.isEmpty()))
                                    convosForUser.get(convoChoice - 1).addMessage(new TextMessage(user, sendThis));

                                writer.println("Would you like to send another message? ('yes' / 'no')end");
                                writer.flush();
                                String runItBack;
                                runItBack = reader.readLine();
                                if (runItBack.equalsIgnoreCase("YES")) {
                                    again = true;
                                } else {
                                    break innerloop;
                                }
                            } while (again);
                        } else {
                            writer.println("You have no conversations open!pass");
                            writer.flush();
                        }
                        break;
                    case 2:
                        // WE'RE STARTING A NEW CONVO
                        writer.println("What user would you like to start a new conversation with?end");
                        writer.flush();
                        String newConvoUser;
                        newConvoUser = reader.readLine();

                        userData.writeDatabase();

                        try {
                            User userForConvo = userData.retrieveUserData(newConvoUser);

                            // DATA FOR INSTANTIATING CONVO
                            ArrayList<User> theGuys = new ArrayList<>();
                            theGuys.add(user);
                            theGuys.add(userForConvo); // Added semicolon

                            Conversation theNewConvo = new Conversation(theGuys);

                            // sends new text
                            writer.println("What would you like to say?end");
                            writer.flush();
                            String sendThis;
                            sendThis = reader.readLine();
                            if ( !(sendThis.isEmpty()) ) {
                                theNewConvo.addMessage(new TextMessage(user, sendThis));
                                writer.println("Message sent.pass");
                            }
                        } catch (ActionNotAllowedException e) {
                            writer.println("Can't find that user!pass");
                        }

                        break;
                    case 3:
                        // Search Users
                        writer.println("Enter the username to search:end");
                        writer.flush();
                        String searchUsername = reader.readLine();
                        try {
                            User searchedUser = userData.retrieveUserData(searchUsername);
                            if (searchedUser != null) {
                                writer.println("User found:\nUsername: " + searchedUser.getUsername() +
                                        "\nName: " + searchedUser.getName() + "\nBio: " + searchedUser.getBio() + "end");
                                writer.flush();
                                // Provide options to add as friend or block user
                                writer.println("""
                                        Would you like to:
                                        1) Add as Friend
                                        2) Block Userend""");
                                writer.flush();
                                String actionChoice = reader.readLine();
                                switch (Integer.parseInt(actionChoice)) {
                                    case 1:
                                        user.addFriend(searchedUser);
                                        writer.println("User added as friend.pass");
                                        writer.flush();
                                        break;
                                    case 2:
                                        user.blockFriend(searchedUser);
                                        writer.println("User blocked.pass");
                                        writer.flush();
                                        break;
                                    default:
                                        writer.println("Invalid choice.pass");
                                        writer.flush();
                                        break;
                                }
                            }
                        } catch (ActionNotAllowedException e) {
                            writer.println("User not found.pass");
                            writer.flush();
                        }
                        break;
                    case 4:
                        // EDIT CLIENT USER
                        try {
                            // Present current user's account information
                            writer.println("Username: " + user.getUsername() + "\nName: " + user.getName() +
                                    "\nBio: " + user.getBio() + "\nEmail: " + user.getEmail() + "\n" +
                                    """
                                    What would you like to edit:
                                    1) Change Username
                                    2) Change Name
                                    3) Update Bio
                                    4) Change Emailend""");
                            writer.flush();
                            int editChoice = Integer.parseInt(reader.readLine());

                            switch (editChoice) {
                                case 1: // Change Username
                                    writer.println("Enter new username:end");
                                    writer.flush();
                                    String newUsername = reader.readLine();
                                    // Check if the new username is not already taken
                                    try {
                                        userData.retrieveUserData(newUsername);
                                        writer.println("Sorry, that username is already taken.pass");
                                        writer.flush();
                                    } catch (ActionNotAllowedException e) {
                                        user.setUsername(newUsername);
                                        writer.println("Username updated successfully.pass");
                                        writer.flush();
                                    }
                                    break;
                                case 2: // Change Name
                                    writer.println("Enter new name: end");
                                    writer.flush();
                                    String newName = reader.readLine();
                                    user.setName(newName);
                                    writer.println("Name updated successfully.pass");
                                    writer.flush();
                                    break;
                                case 3: // Update Bio
                                    writer.println("Enter new bio:end");
                                    writer.flush();
                                    String newBio = reader.readLine();
                                    user.setBio(newBio);
                                    writer.println("Bio updated successfully.pass");
                                    writer.flush();
                                    break;
                                case 4: // Change Email
                                    writer.println("Enter new email:end");
                                    writer.flush();
                                    String newEmail = reader.readLine();
                                    user.setEmail(newEmail);
                                    writer.println("Email updated successfully.pass");
                                    writer.flush();
                                    break;
                                default:
                                    writer.println("Invalid choice.end");
                                    writer.flush();
                                    break;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        writer.println("Goodbye!");
                        writer.flush();
                        writer.close();
                        reader.close();
                        break;
                }
            }
        } catch (IOException ignored) {
        } catch (ActionNotAllowedException e) {
            throw new RuntimeException(e);
        }
    }
}
