import java.time.LocalTime;
/**
 * This class represents a Message object in our team project
 * holding data about the message that is being sent
 *
 *
 *
 * @author Jack Juncker, Ellie Williams
 * @version Mar 25th, 2024.
 */
public class Message {
    // timeSent: A LocalTime field representing
    // the exact time in EST that the message was
    // sent (intialized). 
    private LocalTime timeSent;

    // sender: A User field that represents the
    // sender of the field. 
    private User sender;

    // messageIndex: An integer that describes the
    // position of the message in a DirectMessage
    // message ArrayList. 
    private int messageIndex;

    public Message(User sender) {
        this.sender = sender;
        // sets the timeSent of the message to exactly when
        // the program intializes the Message object
        this.timeSent = LocalTime.now();

        this.messageIndex = 0; 
    }

    public User getSender() {
        return this.sender;
    }

    public LocalTime getTimeSent() {
        return this.timeSent;
    }

    public int getIndex() {
        return this.messageIndex;
    }

    // setIndex: allows a DirectMessage method
    // to direct set the index of a message
    // that's newly added to it's ArrayList to the
    // size of the ArrayList. 
    public void setIndex(int newIndex) throws ActionNotAllowedException{
        if (newIndex < 0) {
            throw new ActionNotAllowedException("Index out of bounds!");
        } else {
            this.messageIndex = newIndex;
        }
    }
}