import java.util.ArrayList;

public class Conversation {
    
    // Users Field: A list representing the users
    // involved in the conversation. Allows for
    // group chats to be made. 
    private ArrayList<User> users;

    // msgs: An array list containing strings for every message
    // sent in the DM. 
    private ArrayList<Message> msgs;

    private ArrayList<Message> deletedMsgs;

    public Conversation (ArrayList<User> users) {
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

}
