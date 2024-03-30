import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;

public class ConversationDatabase implements Database {

    private ArrayList<Conversation> conversationArray;
    private String filePath;

    public ConversationDatabase(ArrayList<Conversation> conversationArray
                                , String filePath) {
        this.conversationArray = conversationArray;
        this.filePath = filePath; 
    }

    public ArrayList<Conversation> getConversationArray() {
        return this.conversationArray;
    }

    // Returns the conversations that a user is involved in. 
    public ArrayList<Conversation> readDatabase(User user) {
        ArrayList<Conversation> availableConversations = new ArrayList<Conversation>();

        for (Conversation conversation : this.conversationArray) {
            for (User involvedUser : conversation.getUsers()) {
                if (involvedUser.equals(user)) {
                    availableConversations.add(conversation); 
                }
            }
        }
        
        return availableConversations; 
    }
    public boolean writeDatabase() {
       
    }

}
