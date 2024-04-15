import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * This class represents a server class that can be called to
 * instantiate a running server that accepts connections through
 * a specified port.
 *
 *
 * @author Jack Juncker, Ellie Williams
 * @version April 2nd, 2024.
 */

public class ServerClass extends Thread {
    private ServerSocket serverSocket;
    private static ArrayList<Object> userArray = new ArrayList<>(); // Moved to ServerClass
    private static UserDatabase userData; // Moved to ServerClass
    private static ConversationDatabase convos; // Moved to ServerClass

    public ServerClass(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        userData = new UserDatabase("userData.txt"); // Initialize UserDatabase
        convos = new ConversationDatabase("conversationData.txt"); // Initialize ConversationDatabase
    }

    public void run() {
        System.out.println("Server started. Listening for connections...");
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                // Delegate client handling to a new thread
                Thread thread = new Thread(new ClientHandler(clientSocket, userData, convos));
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() throws IOException {
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        ServerClass server = new ServerClass(4205);
        server.start();
    }

    public synchronized User verifyLogIn(String username, String password) {
        boolean userFound = false;

        ArrayList<Object> referenceDatabase = userData.getUserArray();
        for (Object user : referenceDatabase) {
            User castUser = (User) user;
            if (castUser.getUsername() == username && castUser.checkPassword(password)) {
                userFound = true;
                return castUser;
            }
        }
        return null;
    }

    public synchronized ConversationDatabase getConvos() {
        return convos;
    }
    public synchronized boolean registerUser(User newUser) {
        boolean success = true;

        ArrayList<Object> referenceDatabase = userData.getUserArray();
        for (Object user : referenceDatabase) {
            User castUser = (User) user;
            if (castUser.equals(newUser)) {
                success = false;
                break;
            }
        }

        if (success) {
            referenceDatabase.add(newUser);
            this.userData.setUserArray(referenceDatabase);
        }

        return success;
    }
    public synchronized boolean addFriend(User assocUser, User proposedFriend) {
        // can be added or not
        return assocUser.addFriend(proposedFriend);
    }
    public synchronized boolean blockUser(User assocUser, User proposedBlock) {
        assocUser.removeFriend(proposedBlock);
        return assocUser.blockFriend(proposedBlock);
    }
    public synchronized ArrayList<Conversation> convosAvailable(User assocUser) {
        return convos.findAvailableConversations(assocUser);
    }
    public synchronized boolean createConversation(Conversation conversation) {
        try {
            ArrayList<Object> originalArray = convos.getConversationArray();
            originalArray.add(conversation);
            convos.setConversationArray(originalArray);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public synchronized boolean sendMessage(Conversation assocConversation, TextMessage sentMessage) {
        try {
            assocConversation.addMessage(sentMessage);
            return true;
        } catch (ActionNotAllowedException ignored) {
            return false;
        }
    }
    public synchronized boolean deleteMessage(Conversation assocConversation, TextMessage deletedMessage) {
        try {
            assocConversation.deleteMessage(deletedMessage);
            return true;
        } catch (ActionNotAllowedException ignored) {
            return false;
        }
    }
    public synchronized ArrayList<User> searchForUsers(String query) {
        return userData.searchForUsers(query);
    }
}