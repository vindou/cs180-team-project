import java.io.Serializable;
import java.util.ArrayList;
/**
 * This class represents a Conversation object for the team project
 *
 *
 *
 * @author Gilbert Chang, Sahil Shetty
 * @version Mar 25th, 2024.
 */
public class Conversation implements Serializable {
    // Conversation name: Allows user to set the name of their
    // group chat. 
    private String conversationName;

    // DM File with usernames in it

    // Unique integer ID for a conversation 
    private int conversationID;

    // Database to write to and refer to
    private ConversationDatabase conversationDatabase;

    // Users Field: A list representing the users
    // involved in the conversation. Allows for
    // group chats to be made. 
    private ArrayList<User> users;

    // msgs: An array list containing Message objects
    // for every message sent in the DM. 
    private ArrayList<Message> msgs;

    // deletedMsgs: An array list containing all the
    // deleted message objects. 
    private ArrayList<Message> deletedMsgs;

    public Conversation() {
        this.conversationName = "";
        this.conversationID = -1;
        this.conversationDatabase = null;
        this.users = null;
        this.msgs = null;
        this.deletedMsgs = null;
    }

    public Conversation(String conversationName
            , ArrayList<User> users
            , ConversationDatabase conversationDatabase) {
        this.conversationName = conversationName;
        this.conversationID = generateID(conversationDatabase);
        this.conversationDatabase = conversationDatabase;
        this.users = users;
        this.msgs = new ArrayList<Message>();
        this.deletedMsgs = new ArrayList<Message>();
    }

    public Conversation(ArrayList<User> users, ConversationDatabase conversationDatabase) {
        this.conversationName = "";
        for (int i = 0; i < users.size(); i++) {
            if (i != users.size() - 1) {
                this.conversationName += users.get(i);
            } else {
                this.conversationName += users.get(i) + ", ";
            }
        }
        this.conversationID = generateID(conversationDatabase);
        this.conversationDatabase = conversationDatabase;
        this.users = users;
        this.msgs = new ArrayList<Message>();
        this.deletedMsgs = new ArrayList<Message>();
    }

    // Returns the User object represented by User 1
    public ArrayList<User> getUsers() {
        return this.users;
    }

    // Returns the user at the index in the list specified
    public User getUserAt(int index) throws ActionNotAllowedException {
        if (index < 0 || index >= this.users.size()) {
            throw new ActionNotAllowedException("This conversation doesn't have this many users involved!");
        } else {
            return this.users.get(index);
        }
    }

    // Returns an ArrayList<String> of all the messages in the DM
    public ArrayList<Message> getMessages() {
        return this.msgs;
    }

    public ArrayList<Message> getDeletedMessages() {
        return this.deletedMsgs;
    }

    public int getID() {
        return this.conversationID;
    }

    public boolean addUser(User newUser) throws ActionNotAllowedException {
        boolean validation = true;
        boolean completion = false;

        for (User currentUser : this.users) {
            if (newUser.equals(currentUser)) {
                validation = false;
                throw new ActionNotAllowedException("User already a part of this conversation!");
            }
        }
        if (validation) {
            this.users.add(newUser);
            completion = true;
        }

        return completion;
    }

    public boolean removeUser(User targetUser) throws ActionNotAllowedException {
        boolean validation = false;
        boolean completion = false;

        for (User currentUser : this.users) {
            if (targetUser.equals(currentUser)) {
                validation = true;
            }
        }
        if (validation) {
            this.users.remove(targetUser);
            completion = true;
        } else {
            completion = false;
            throw new ActionNotAllowedException("User already a part of this conversation!");
        }

        return completion;
    }

    // Adds a newly instantiated message object to the
    // msgs ArrayList of the DirectMessage object
    public void addMessage(Message newMessage) throws ActionNotAllowedException {
        newMessage.setIndex(this.msgs.size());
        this.msgs.add(newMessage);
    }

    // Allows for users to delete messages by removing them
    // from the msgs ArrayList. Checks if the index and
    // message content is the same.
    public void deleteMessage(Message deletedMessage) throws ActionNotAllowedException {
        Message targetMessage = this.msgs.get(deletedMessage.getIndex());
        if (targetMessage.equals(deletedMessage)) {
            this.deletedMsgs.add(targetMessage);
            this.msgs.remove(targetMessage);
        } else {
            throw new ActionNotAllowedException("Message not found");
        }
    }

    public int generateID(ConversationDatabase database) {
        boolean randomIntegerGenerated = false;
        int proposedID = 0;
        do {
            proposedID = (int) (Math.random() * 1000000);

            innerloop:
            for (Object individualConversation : database.getConversationArray()) {
                Conversation tempConvo = (Conversation) individualConversation;
                if (this.conversationID == tempConvo.getID()) {
                    break innerloop;
                }
            }

            randomIntegerGenerated = true;
        } while (!randomIntegerGenerated);

        return proposedID;
    }

    public String toString() {
        return String.valueOf(this.conversationID);
    }

    public String fileNameString() {
        return "Conversation #" + conversationID;
    }
}