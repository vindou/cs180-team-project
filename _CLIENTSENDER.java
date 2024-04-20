import org.w3c.dom.Text;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;

/**
 * _CLIENTSENDER
 *
 * Class that is intended to be instantiated in the _USERINTERFACE class, and used
 * to directly associate actions performed by the user in their personal instance of the
 * user interface with requests to modify data server-side.
 *
 * @author Gilbert Chang
 * @version Apr 16, 2024
 */

public class _CLIENTSENDER implements Runnable {
    private Socket route;
    private ObjectInputStream objectReader;
    private ObjectOutputStream objectSender;
    private _USERINTERFACE userInterface;
    private boolean[] registrationFieldValidity = new boolean[3];
    public _CLIENTSENDER(Socket socket, _USERINTERFACE userInterface) throws IOException {
        this.route = socket;
        this.userInterface = userInterface;
        this.objectSender = new ObjectOutputStream(route.getOutputStream());
        this.objectReader = new ObjectInputStream(route.getInputStream());
    }
    public synchronized boolean logInRequest() throws IOException
            , ClassNotFoundException {
        String username = userInterface.getLogInFieldData()[0];
        String password = userInterface.getLogInFieldData()[1];

        boolean success;
        objectSender.writeObject("LOGIN_REQUEST");
        objectSender.flush();

        objectSender.writeObject(username);
        objectSender.flush();

        objectSender.writeObject(password);
        objectSender.flush();

        String responseState = (String) objectReader.readObject();

        if (responseState.equals("LOGIN_SUCCESS")) {
            success = true;
        } else if (responseState.equals("LOGIN_DENIED")) {
            success = false;
        } else {
            success = false;
        }

        return success;
    }
    public synchronized boolean getLogInSuccess() throws IOException, ClassNotFoundException {
        userInterface.updateLogInFieldData();
        return this.logInRequest();
    }

    public synchronized boolean registrationRequest(User proposedUser) throws IOException
            , ClassNotFoundException {
        boolean success;
        objectSender.writeObject("REGISTRATION_REQUEST");
        objectSender.flush();

        objectSender.writeObject(proposedUser);
        objectSender.flush();

        String responseStatus = (String) objectReader.readObject();

        if (responseStatus.equals("REGISTRATION_SUCCESS")) {
            success = true;
        } else if (responseStatus.equals("REGISTRATION_FAILURE")) {
            success = false;
        } else {
            success = false;
        }

        return success;
    }
    public synchronized void updateEmailValidity() {
        String[] referenceData = userInterface.getRegistrationFieldData();
        String email = referenceData[1];

        if (email.split("@").length != 2) {
            this.registrationFieldValidity[0] = false;
        } else {
            this.registrationFieldValidity[0] = true;
        }
    }
    public synchronized void updateUsernameOriginality() {
        String proposedUsername = userInterface.getRegistrationFieldData()[2];
        try {
            this.registrationFieldValidity[1] = !this.verifyUsernameAvailabilityRequest(proposedUsername);
        } catch (IOException | ClassNotFoundException e) {
            this.registrationFieldValidity[1] = false;
        }
    }
    public synchronized void updateBirthdayValidity() {
        String birthday = userInterface.getRegistrationFieldData()[4];

        if (birthday.split("/").length != 3) {
            this.registrationFieldValidity[2] = false;
        } else {
            boolean result = true;
            int[] entries = new int[3];
            String[] referenceList = birthday.split("/");

            for (int i = 0; i < referenceList.length; i++) {
                try {
                    int entry = Integer.parseInt(referenceList[i]);
                    if (i == 0 && (entry <= 0 || entry > 12)) {
                        result = false;
                        break;
                    } else if (i == 1 && (entry <= 0 || entry > 30)) {
                        result = false;
                        break;
                    } else if (i == 2 && (entry > 2024 || entry <= 0)) {
                        result = false;
                        break;
                    } else {
                        entries[i] = entry;
                    }
                } catch (NumberFormatException e) {
                    result = false;
                    break;
                }
            }
            this.registrationFieldValidity[2] = result;
        }
    }
    public synchronized boolean[] getRegistrationFieldValidity() {
        userInterface.updateRegistrationFieldData();
        this.updateEmailValidity();
        this.updateUsernameOriginality();
        this.updateBirthdayValidity();
        return this.registrationFieldValidity;
    }

    public synchronized boolean verifyUsernameAvailabilityRequest(String proposedUsername) throws IOException
            , ClassNotFoundException {
        boolean nameAvailability;
        objectSender.writeObject("VERIFY_USERNAME_AVAILABILITY");
        objectSender.flush();

        objectSender.writeObject(proposedUsername);
        objectSender.flush();

        String responseStatus = (String) objectReader.readObject();

        if (responseStatus.equals("USERNAME_AVAILABLE")) {
            nameAvailability = true;
        } else if (responseStatus.equals("USERNAME_UNAVAILABLE")) {
            nameAvailability = false;
        } else {
            nameAvailability = false;
        }

        return nameAvailability;
    }
    public synchronized boolean addFriendRequest(User proposedFriend) throws IOException
            , ClassNotFoundException {
        boolean success;
        objectSender.writeObject("FRIEND_ADDITION_REQUEST");
        objectSender.flush();

        objectSender.writeObject(proposedFriend);
        objectSender.flush();

        String responseStatus = (String) objectReader.readObject();

        if (responseStatus.equals("FRIEND_ADDITION_SUCCESS")) {
            success = true;
        } else if (responseStatus.equals("FRIEND_ADDITION_FAILURE")) {
            success = false;
        } else {
            success = false;
        }
        return success;
    }

    public synchronized boolean removeFriendRequest(User removedFriend) throws IOException
            , ClassNotFoundException {
        boolean success;
        objectSender.writeObject("FRIEND_REMOVAL_REQUEST");
        objectSender.flush();

        objectSender.writeObject(removedFriend);
        objectSender.flush();

        String responseStatus = (String) objectReader.readObject();

        if (responseStatus.equals("FRIEND_REMOVAL_SUCCESS")) {
            success = true;
        } else if (responseStatus.equals("FRIEND_REMOVAL_FAILURE")) {
            success = false;
        } else {
            success = false;
        }
        return success;
    }
    public synchronized boolean blockUserRequest(User proposedBlock) throws IOException
            , ClassNotFoundException {
        boolean success;
        objectSender.writeObject("BLOCK_USER_REQUEST");
        objectSender.flush();

        objectSender.writeObject(proposedBlock);
        objectSender.flush();

        String responseStatus = (String) objectReader.readObject();

        if (responseStatus.equals("BLOCK_SUCCESS")) {
            success = true;
        } else if (responseStatus.equals("BLOCK_FAILURE")) {
            success = false;
        } else {
            success = false;
        }
        return success;
    }
    public synchronized boolean addConversationRequest(String conversationName, User otherUser)
            throws IOException
            , ClassNotFoundException {
        boolean success;
        objectSender.writeObject("ADD_CONVERSATION_REQUEST");
        objectSender.flush();

        objectSender.writeObject(otherUser);
        objectSender.flush();

        objectSender.writeObject(conversationName);
        objectSender.flush();

        String response = (String) objectReader.readObject();

        if (response.equals("CONVERSATION_ADDITION_SUCCESS")) {
            success = true;
        } else {
            success = false;
        }

        return success;
    }
    public synchronized ArrayList<Conversation> convosAvailableRequest() throws IOException
            , ClassNotFoundException {
        objectSender.writeObject("CONVOS_REQUEST");
        objectSender.flush();

        String responseStatus = (String) objectReader.readObject();
        System.out.println(responseStatus);

        if (responseStatus.equals("CONVOS_FOUND")) {
            int convosAvailable = (int) objectReader.readObject();
            System.out.println(convosAvailable);
            ArrayList<Conversation> availableConvos = new ArrayList<>();

            for (int i = 0; i < convosAvailable; i++) {
                System.out.println("appending convo");
                Conversation convo = (Conversation) objectReader.readObject();
                availableConvos.add(convo);
            }

            return availableConvos;
        } else if (responseStatus.equals("NO_CONVOS_FOUND")) {
            System.out.println("no convos found");
            return null;
        } else {
            return null;
        }
    }
    public synchronized Conversation sendMessageRequest(Conversation assocConvo, String message) throws IOException
            , ClassNotFoundException {
        System.out.println("sending request");
        objectSender.writeObject("SEND_MESSAGE_REQUEST");
        objectSender.flush();

        // send conversation
        System.out.println("writing convo");
        objectSender.writeObject(assocConvo);
        objectSender.flush();

        // send message
        System.out.println("writing message");
        objectSender.writeObject(message);
        objectSender.flush();

        String responseStatus = (String) objectReader.readObject();

        if (responseStatus.equals("MESSAGE_SENT")) {
            System.out.println("message sent");

            Conversation newConvo = (Conversation) objectReader.readObject();
            return newConvo;
        } else if (responseStatus.equals("MESSAGE_SEND_FAILED")) {
            System.out.println("message not sent");
            return null;
        } else {
            return null;
        }
    }
    public synchronized boolean deleteMessageRequest(Conversation assocConvo, TextMessage proposedDelete) throws IOException
            , ClassNotFoundException {
        boolean success;
        objectSender.writeObject("DELETE_MESSAGE_REQUEST");
        objectSender.flush();

        objectSender.writeObject(assocConvo);
        objectSender.flush();

        objectSender.writeObject(proposedDelete);
        objectSender.flush();

        String responseStatus = (String) objectReader.readObject();

        if (responseStatus.equals("DELETE_SUCCESS")) {
            success = true;
        } else if (responseStatus.equals("DELETE_FAILED")) {
            success = false;
        } else {
            success = false;
        }

        return success;
    }
    public synchronized ArrayList<User> requestUserQuery(String query) throws IOException
            , ClassNotFoundException {
        objectSender.writeObject("USER_REQUEST_QUERY");
        objectSender.flush();

        objectSender.writeObject(query);
        objectSender.flush();

        Object responseStatus = objectReader.readObject();

        if (responseStatus.equals("USERS_FOUND")) {
            try {
                return (ArrayList<User>) objectReader.readObject();
            } catch (ClassNotFoundException e) {
                return null;
            }
        } else if (responseStatus.equals("USERS_NOT_FOUND")) {
            return null;
        } else {
            return null;
        }
    }

    public synchronized ArrayList<User> requestFriends() throws IOException
            , ClassNotFoundException {
        objectSender.writeObject("FRIEND_REQUEST_QUERY");
        objectSender.flush();

        String responseStatus = (String) objectReader.readObject();

        if (responseStatus.equals("FRIENDS_FOUND")) {
            int size = (int) objectReader.readObject();
            ArrayList<User> resultingArray = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                User indFriend = (User) objectReader.readObject();
                resultingArray.add(indFriend);
            }

            return resultingArray;
        } else if (responseStatus.equals("NO_FRIENDS")) {
            return null;
        } else {
            return null;
        }
    }

    @Override
    public void run() {
        try {
            String server = (String) objectReader.readObject();

            if (server.equals("PING")) {
                System.out.println("PING");
                objectSender.writeObject("PONG");
                objectSender.flush();
                System.out.println("PONG");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
