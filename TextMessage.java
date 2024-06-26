import java.io.Serializable;

/**
 * TextMessage.java
 *
 * A class that represents a Message that a User sends
 *
 * @author Gilbert Chang
 *
 * @version March 31, 2024
 */

public class TextMessage extends Message implements Serializable {

    // rawMessage: String field representing
    // the raw string content of a text message.
    private String rawMessage;

    public TextMessage(User sender, String rawMessage) {
        super(sender);
        this.rawMessage = rawMessage;
    }

    public String getMessage() {
        return this.rawMessage;
    }

    // changeMessage: allows users to edit
    // their sent messages. 
    public void changeMessage(String newMessage) {
        this.rawMessage = newMessage;
    }

    // equals: Compares two TextMessage objects and returns
    // true or false depending on if the rawMessage contents
    // are the same, and if the indices are equal.

    public boolean equals(Object comparedMessage) {
        boolean equality = false;
        if (comparedMessage.getClass().getName().equals("TextMessage")) {
            TextMessage newMessage = (TextMessage) comparedMessage;
            if (this.rawMessage.equals(newMessage.getMessage())
                    && this.getIndex() == newMessage.getIndex()) {
                equality = true;
            }
        }
        return equality;
    }

    public String toString() {
        return this.getSender().getUsername()
                + ":["
                + this.getTimeSent().toString()
                + "]: \""
                + this.rawMessage
                + "\"";
    }
}