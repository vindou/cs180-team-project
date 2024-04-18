import javax.swing.*;
import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class _SERVERCLASS {
    public static UserDatabase userDatabase = new UserDatabase("userData.txt");
    public static ConversationDatabase conversationDatabase = new ConversationDatabase("conversationData.txt");
    private static int PORT_NUM = 5329;

    public _SERVERCLASS() {}
    public void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                saveData();
            }
        }, "Shutdown-thread"));
        _SERVERCLASS mainServer = new _SERVERCLASS();
        try {
            ServerSocket serverSocket = new ServerSocket(PORT_NUM);
            System.out.printf("PORT_NUM = %d\nSERVER ONLINE\n", PORT_NUM);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("\nCLIENT CONNECTION MADE: " + clientSocket.getInetAddress());

                Thread clientThread = new Thread(new _CLIENTHANDLER(mainServer, clientSocket));
                clientThread.start();
            }
        } catch (EOFException | NullPointerException e) {
            System.out.println("SAVING DATA");
            System.out.println("CLIENT DISCONNECTED");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR: INVALID PORT.");
        }
    }

    public synchronized void saveData() {
        System.out.println("SAVING DATA");
        userDatabase.writeDatabase();
        conversationDatabase.writeDatabase();
    }
    public synchronized User verifyLogIn(String username, String password) {
        boolean userFound = false;
        User foundUser = null;

        ArrayList<Object> referenceDatabase = userDatabase.getUserArray();
        for (Object user : referenceDatabase) {
            User castUser = (User) user;
            if (castUser.getUsername().equals(username)
                    || castUser.checkPassword(password)) {
                userFound = true;
                foundUser = castUser;
                break;
            }
        }

        if (userFound) {
            return foundUser;
        } else {
            return null;
        }
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

    public synchronized boolean removeFriend(User assocUser, User removedFriend) {
        // can be added or not
        return assocUser.removeFriend(removedFriend);
    }
    public synchronized boolean blockUser(User assocUser, User proposedBlock) {
       assocUser.removeFriend(proposedBlock);
       return assocUser.blockFriend(proposedBlock);
    }

    public synchronized ArrayList<User> getFriendList(User assocUser) {
        return assocUser.getFriends();
    }
    public synchronized ArrayList<Conversation> convosAvailable(User assocUser) {
        return conversationDatabase.findAvailableConversations(assocUser);
    }
    public synchronized boolean createConversation(ArrayList<User> users, String conversationName) {
        Conversation proposedConversation = new Conversation(conversationName, users, conversationDatabase);
        boolean isBlocked = false;

        for (User subUser : users) {
            for (User otherUser : users) {
                if (otherUser.getBlocked().contains(subUser)) {
                    isBlocked = true;
                }
            }
        }

        if (isBlocked) {
            return false;
        } else {
            return conversationDatabase.addConversation(proposedConversation);
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
        return userDatabase.searchForUsers(query);
    }
    public synchronized boolean checkForUsernameOriginality(String proposedUsername) {
        return userDatabase.originalUsername(proposedUsername);
    }
}
