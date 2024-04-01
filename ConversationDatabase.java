import java.io.*;
import java.util.ArrayList;
/**
 * This class represents a database for Conversation objects for our team project
 *
 *
 *
 * @author Gilbert Chang, Sahil Shetty
 * @version Mar 25th, 2024.
 */
public class ConversationDatabase implements Database {
    // conversationArray: An ArrayList that stores every single
    // conversation that has been created on the social media
    // platform.
    private ArrayList<Object> conversationArray;

    // filePath: A String variable describing where the
    // Conversation data should be written to.
    private String filePath;


    // Main Constructor
    public ConversationDatabase(String filePath) {
        this.filePath = filePath;
        this.conversationArray = readDatabase();
    }

    public void updateConversationArray() {
        this.conversationArray = readDatabase();
    }

    // Returns an Array of all Conversations that have been
    // ever made on the app.
    public ArrayList<Object> getConversationArray() {
        return this.conversationArray;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public ArrayList<Object> readDatabase() {
        ArrayList<Object> conversationsFound = new ArrayList<Object>();
        File f = new File(this.filePath);
        try {
            // open file
            // use ois to access each object
            // add it to the array
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Conversation conversation = (Conversation) ois.readObject();

            while (true) {
                conversationsFound.add(conversation);
                conversation = (Conversation) ois.readObject();
            }
        } catch (EOFException e) {
            return conversationsFound;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return new ArrayList<Object>();
        }
    }

    // Returns the conversations that a user is involved in.
    public ArrayList<Conversation> findAvailableConversations(User user) {
        ArrayList<Conversation> availableConversations = new ArrayList<Conversation>();

        for (Object conversationObj : this.conversationArray) {
            Conversation conversation = (Conversation) conversationObj;
            for (User involvedUser : conversation.getUsers()) {
                if (involvedUser.equals(user)) {
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
           ObjectOutputStream oos = new ObjectOutputStream(fos);

           for (Object conversationObj : conversationArray) {
               Conversation conversation = (Conversation) conversationObj;
               oos.writeObject(conversation);
           }

           fos.close();
           oos.close();
       } catch (Exception e) {
           completion = false;
       }

       return completion;
    }

    public boolean writeMessageLogs(Conversation conversation) {
        boolean completion = true;
        File f = new File(conversation.getID() + ".txt");
        try {
            FileOutputStream fos = new FileOutputStream(f, false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(conversation);
            fos.close();
            oos.close();
        } catch (Exception e) {
            completion = false;
        }
        return completion;
    }
}
