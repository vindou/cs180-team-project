import java.util.ArrayList;

public class DirMsg {
    
    private User user1;
    private User user2;
    private ArrayList<String> msgs; 

    public DirMsg (User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    // Returns the users
    public User getUser1() {
        return this.user1;
    }

    public User getUser2() {
        return this.user2; 
    }

    // Returns an ArrayList<String> of all the messages in the DM
    public ArrayList<String> getMessages() {
        return this.msgs; 
    }

}
