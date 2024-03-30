import java.io.*;
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

    public ArrayList<Object> readConversations(String fileName) {
        
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
       File f = new File(this.filePath);
       try {
           FileOutputStream fos = new FileOutputStream(f, false);
           PrintWriter pw = new PrintWriter(fos);

           for (Conversation individualConversation : this.conversationArray) {
               pw.println(individualConversation.toString());
           }
           pw.close();
       } catch (FileNotFoundException e) {
           completion = false;
       }

       return completion;
    }

    public boolean writeMessageLogs(Conversation conversation) {
        boolean completion = true;
        File f = new File(conversation.getID() + ".txt");
        try {
            FileOutputStream fos = new FileOutputStream(f, false);
            PrintWriter pw = new PrintWriter(fos);

            for (Message message : conversation.getMessages()) {
                pw.println(message.toString());
            }
            pw.close();
        } catch (FileNotFoundException e) {
            completion = false;
        }

        return completion;
    }

    // Retrieves and returns the data for a conversation given a
    // file name.
    public Conversation retrieveData(String conversationFileName) {
        return new Conversation();
    }

}
