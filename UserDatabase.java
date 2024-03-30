import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class UserDatabase implements Database {

    // An ArrayList containing every user on the platform.
    private ArrayList<User> userArray;

    // The file name that the data should be written to.
    private String fileName;

    public UserDatabase(ArrayList<User> userArray, String fileName) {
        this.userArray = userArray;
        this.fileName = fileName;
    }

    // Create multiple methods to allow users to
    // search for other users in a database.
    // Can search by name and username.
    // Names have at least one space, so you can differentiate
    // the search based on this.
    public ArrayList<String> readDatabase() {

        try {

            File f = new File(fileName);
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);

            ArrayList<String> userArrayList = new ArrayList<>();

            String line = bfr.readLine();
            while (line != null) {
                userArrayList.add(line);
                line = bfr.readLine();
            }

            return userArrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Writes all user data to one file, given the fileName.
    // Make sure to get all the fields on one line.
    public boolean writeDatabase() {

    }

    // Checks if the user is equivalent to any that's in the userArray
    // if not, throw an ActionNotAllowedException that says
    // account doesn't currently exist.
    public boolean writeUserData(User user, String fileName) {
        boolean completion = true;

        return completion;
    }

    // Finds the file with the same name as the string fileName and
    // returns a User object with the data in that file.
    public User retrieveUserData(String fileName) {
        return new User();
    }
}