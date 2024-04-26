import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class FinalServerClass {
    public static UserDatabase userDatabase = new UserDatabase("userData.txt");
    public static ConversationDatabase conversationDatabase = new ConversationDatabase("conversationData.txt");
    private static int PORT_NUM = 5329;

    public FinalServerClass() {}
    public void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                saveData();
            }
        }, "Shutdown-thread"));
        FinalServerClass mainServer = new FinalServerClass();
        try {
            ServerSocket serverSocket = new ServerSocket(PORT_NUM);
            System.out.printf("PORT_NUM = %d\nSERVER ONLINE\n", PORT_NUM);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("\nCLIENT CONNECTION MADE: " + clientSocket.getInetAddress());

                Thread clientThread = new Thread(new FinalClientHandler(mainServer, clientSocket));
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
                    && castUser.checkPassword(password)) {
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
            userDatabase.setUserArray(referenceDatabase);
        }

        return success;
    }
    public synchronized boolean addFriend(User assocUser, User proposedFriend) {
        boolean success = assocUser.addFriend(proposedFriend);
        // can be added or not
        return success;
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
        ArrayList<User> friends = assocUser.getFriends();
        return friends;
    }
    public synchronized ArrayList<Conversation> convosAvailable(User assocUser) {
        ArrayList<Conversation> convos = conversationDatabase.findAvailableConversations(assocUser);
        return convos;
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
            conversationDatabase.addConversation(proposedConversation);
            System.out.println(conversationDatabase.getConversationArray().size());
            return true;
        }
    }
    public synchronized boolean sendMessage(Conversation assocConversation, TextMessage sentMessage) {
        boolean success = false;
        for (Object convo : this.conversationDatabase.getConversationArray()) {
            Conversation transConvo = (Conversation) convo;
            if (transConvo.equals(assocConversation)) {
                try {
                    ((Conversation) convo).addMessage(sentMessage);
                    success = true;
                    break;
                } catch (ActionNotAllowedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (success) {
            System.out.println("message sucessfully sent");
            return success;
        } else {
            return false;
        }
    }
    public synchronized boolean deleteMessage(Conversation assocConversation, TextMessage deletedMessage) {
        boolean success = false;
        for (Object convo : this.conversationDatabase.getConversationArray()) {
            Conversation transConvo = (Conversation) convo;
            if (transConvo.equals(assocConversation)) {
                try {
                    ((Conversation) convo).deleteMessage(deletedMessage);
                    success = true;
                    break;
                } catch (ActionNotAllowedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (success) {
            return success;
        } else {
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
