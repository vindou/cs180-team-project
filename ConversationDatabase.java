import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;

public class ConversationDatabase implements Database {
    // conversationArray: An ArrayList that stores every single
    // conversation that has been created on the social media
    // platform.
    private ArrayList<Conversation> conversationArray;

    // filePath: A String variable describing where the
    // Conversation data should be written to.
    private String filePath;

    // Main Constructor
    public ConversationDatabase(ArrayList<Conversation> conversationArray
                                , String filePath) {
        this.conversationArray = conversationArray;
        this.filePath = filePath; 
    }

    // Returns an Array of all Conversations that have been
    // ever made on the app.
    public ArrayList<Conversation> getConversationArray() {
        return this.conversationArray;
    }

    // Returns the conversations that a user is involved in. 
    public ArrayList<Object> readDatabase(Object o) {
        ArrayList<Object> availableConversations = new ArrayList<Object>();

        for (Conversation conversation : this.conversationArray) {
            for (User involvedUser : conversation.getUsers()) {
                if (involvedUser.equals(o)) {
                    availableConversations.add(conversation); 
                }
            }
        }
        
        return availableConversations; 
    }

    // Writes all the conversation file names to a single file
    // returns true or false depending on completion.
    public boolean writeDatabase() {
       boolean completion = true;

       return completion;
    }

    // Retrieves and returns the data for a conversation given a
    // file name.
    public Conversation retrieveData(String conversationFileName) {
        return new Conversation();
    }

}
