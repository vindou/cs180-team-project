public class TextMessage extends Message {
    
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
    public boolean equals(TextMessage comparedMessage) {
        boolean equality = false;
        if (this.rawMessage.equals(comparedMessage.getMessage())
            && this.getIndex() == comparedMessage.getIndex()) {
            equality = true;
        }
        return equality;
    }
}
