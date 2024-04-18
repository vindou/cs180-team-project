import org.w3c.dom.Text;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class _CLIENTHANDLER implements Runnable {
    private _SERVERCLASS accessedServer;
    private Socket clientSocket;
    private User assocUser;
    private ObjectInputStream objectReader;
    private ObjectOutputStream objectSender;

    public _CLIENTHANDLER(_SERVERCLASS accessedServer, Socket clientSocket) {
        this.accessedServer = accessedServer;
        this.clientSocket = clientSocket;
        this.assocUser = null;
    }

    public void run() {
        try {
            this.objectSender = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            objectSender.writeObject("PING");
            objectSender.flush();
            System.out.println("PING");
            this.objectReader = new ObjectInputStream(clientSocket.getInputStream());
            while (true) {
                String clientRequest = (String) objectReader.readObject();
                if (clientRequest != null) {
                    if (clientRequest.equals("LOGIN_REQUEST")) {
                        String username = (String) objectReader.readObject();
                        String password = (String) objectReader.readObject();

                        this.assocUser = this.accessedServer.verifyLogIn(username, password);
                        if (this.assocUser == null) {
                            // LOGIN FAILED
                            objectSender.writeObject("LOGIN_DENIED");
                            objectSender.flush();

                        } else {
                            // LOGIN SUCCESS
                            objectSender.writeObject("LOGIN_SUCCESS");
                            objectSender.flush();
                        }
                    } else if (clientRequest.equals("REGISTRATION_REQUEST")) {
                        User proposedUser = (User) objectReader.readObject();
                        boolean success = this.accessedServer.registerUser(proposedUser);

                        if (success) {
                            this.assocUser = proposedUser;

                            objectSender.writeObject("REGISTRATION_SUCCESS");
                            objectSender.flush();
                        } else {
                            objectSender.writeObject("REGISTRATION_FAILURE");
                            objectSender.flush();
                        }
                    } else if (clientRequest.equals("VERIFY_USERNAME_AVAILABILITY")) {
                        String proposedUsername = (String) objectReader.readObject();
                        boolean originality = this.accessedServer.checkForUsernameOriginality(proposedUsername);

                        if (!originality) {
                            objectSender.writeObject("USERNAME_UNAVAILABLE");
                            objectSender.flush();
                        } else {
                            objectSender.writeObject("USERNAME_AVAILABLE");
                            objectSender.flush();
                        }
                    } else if (clientRequest.equals("FRIEND_ADDITION_REQUEST")) {
                        User proposedFriend = (User) objectReader.readObject();
                        boolean success = this.accessedServer.addFriend(this.assocUser, proposedFriend);

                        if (success) {
                            objectSender.writeObject("FRIEND_ADDITION_SUCCESS");
                            objectSender.flush();
                        } else {
                            objectSender.writeObject("FRIEND_ADDITION_FAILURE");
                            objectSender.flush();
                        }
                    } else if (clientRequest.equals("FRIEND_REMOVAL_REQUEST")) {
                        User proposedRemoval = (User) objectReader.readObject();
                        boolean success = this.accessedServer.removeFriend(this.assocUser, proposedRemoval);

                        if (success) {
                            objectSender.writeObject("FRIEND_REMOVAL_SUCCESS");
                            objectSender.flush();
                        } else {
                            objectSender.writeObject("FRIEND_REMOVAL_FAILURE");
                            objectSender.flush();
                        }
                    } else if (clientRequest.equals("BLOCK_USER_REQUEST")) {
                        User proposedBlock = (User) objectReader.readObject();
                        boolean success = this.accessedServer.blockUser(this.assocUser, proposedBlock);

                        if (success) {
                            objectSender.writeObject("BLOCK_SUCCESS");
                            objectSender.flush();
                        } else {
                            objectSender.writeObject("BLOCK_FAILURE");
                            objectSender.flush();
                        }
                    } else if (clientRequest.equals("FRIEND_REQUEST_QUERY")) {
                        ArrayList<User> friends = accessedServer.getFriendList(this.assocUser);

                        if (!friends.isEmpty()) {
                            objectSender.writeObject("FRIENDS_FOUND");
                            objectSender.flush();

                            objectSender.writeObject(friends);
                            objectSender.flush();
                        } else {
                            objectSender.writeObject("NO_FRIENDS");
                            objectSender.flush();
                        }
                    } else if (clientRequest.equals("CONVOS_REQUEST")) {
                        ArrayList<Conversation> foundConvos = this.accessedServer.convosAvailable(this.assocUser);

                        if (foundConvos.size() == 0) {
                            objectSender.writeObject("CONVOS_FOUND");
                            objectSender.flush();

                            objectSender.writeObject(foundConvos);
                            objectSender.flush();
                        } else {
                            objectSender.writeObject("NO_CONVOS_FOUND");
                            objectSender.flush();
                        }
                    } else if (clientRequest.equals("SEND_MESSAGE_REQUEST")) {
                        Conversation assocConvo = (Conversation) objectReader.readObject();
                        TextMessage proposedMessage = (TextMessage) objectReader.readObject();
                        boolean success = this.accessedServer.sendMessage(assocConvo, proposedMessage);

                        if (success) {
                            objectSender.writeObject("MESSAGE_SENT");
                            objectSender.flush();
                        } else {
                            objectSender.writeObject("MESSAGE_SEND_FAILED");
                            objectSender.flush();
                        }
                    } else if (clientRequest.equals("DELETE_MESSAGE_REQUEST")) {
                        Conversation assocConvo = (Conversation) objectReader.readObject();
                        TextMessage proposedDelete = (TextMessage) objectReader.readObject();
                        boolean success = this.accessedServer.deleteMessage(assocConvo, proposedDelete);

                        if (success) {
                            objectSender.writeObject("DELETE_SUCCESS");
                            objectSender.flush();
                        } else {
                            objectSender.writeObject("DELETE_FAILED");
                            objectSender.flush();
                        }
                    } else if (clientRequest.equals("USER_REQUEST_QUERY")) {
                        String query = (String) objectReader.readObject();
                        ArrayList<User> foundUsers = this.accessedServer.searchForUsers(query);

                        if (foundUsers.size() == 0) {
                            objectSender.writeObject("USERS_NOT_FOUND");
                            objectSender.flush();
                        } else {
                            objectSender.writeObject("USERS_FOUND");
                            objectSender.flush();

                            objectSender.writeObject(foundUsers);
                            objectSender.flush();
                        }
                    } else if (clientRequest.equals("ADD_CONVERSATION_REQUEST")) {
                        User otherUser = (User) objectReader.readObject();
                        ArrayList<User> users = new ArrayList<>();

                        users.add(this.assocUser);
                        users.add(otherUser);

                        String conversationName = (String) objectReader.readObject();

                        if (accessedServer.createConversation(users, conversationName)) {
                            objectSender.writeObject("CONVERSATION_ADDITION_SUCCESS");
                            objectSender.flush();
                        } else {
                            objectSender.writeObject("CONVERSATION_ADDITION_FAILED");
                            objectSender.flush();
                        }
                    } else if (clientRequest.equals("CLIENT_SHUTDOWN")) {
                        break;
                    } else if (clientRequest.equals("PONG")) {
                        System.out.println("PONG");
                    }
                }
            }
        } catch (EOFException e) {
            accessedServer.saveData();
            System.out.println("CLIENT DISCONNECTED");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("THREAD STOPPING");
        }

        try {
            if (objectReader != null) {
                objectReader.close();
                objectSender.close();
            }
            this.clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}