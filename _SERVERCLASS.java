import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class _SERVERCLASS {
    public UserDatabase userDatabase = new UserDatabase("userData.txt");
    public ConversationDatabase conversationDatabase = new ConversationDatabase("conversationData.txt");
    private static int PORT_NUM = 5327;

    public _SERVERCLASS() {}
    public static void main(String[] args) {
        _SERVERCLASS mainServer = new _SERVERCLASS();
        try {
            ServerSocket serverSocket = new ServerSocket(PORT_NUM);
            System.out.printf("PORT_NUM = %d\nSERVER ONLINE", PORT_NUM);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("CLIENT CONNECTION MADE: " + clientSocket.getInetAddress());

                Thread clientThread = new Thread(new _CLIENTHANDLER(mainServer, clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            System.out.println("ERROR: INVALID PORT.");
        }
    }



    // things that do require logging in
    // searching for users

    public synchronized User verifyLogIn(String username, String password) {
        boolean userFound = false;

        ArrayList<Object> referenceDatabase = userDatabase.getUserArray();
        for (Object user : referenceDatabase) {
            User castUser = (User) user;
            if (castUser.getUsername() == username && castUser.checkPassword(password)) {
                userFound = true;
                return castUser;
            }
        }

        return null;
    }
    public synchronized boolean registerUser(User newUser) {
        boolean success = true;

        ArrayList<Object> referenceDatabase = userDatabase.getUserArray();
        for (Object user : referenceDatabase) {
            User castUser = (User) user;
            if (castUser.equals(newUser)) {
                success = false;
                break;
            }
        }

        if (success) {
            referenceDatabase.add(newUser);
            this.userDatabase.setUserArray(referenceDatabase);
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
        return conversationDatabase.findAvailableConversations(assocUser);
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
        return userDatabase.searchForUsers(query);
    }
}
