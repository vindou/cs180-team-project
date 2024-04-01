import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * This class represents a User for the team project
 *
 *
 *
 * @author Sahil Shetty, Gilbert Chang, Jack Juncker
 * @version Mar 25th, 2024.
 */
public class UserDatabase implements Database {

    // An ArrayList containing every user on the platform.
    private ArrayList<User> userArray;

    private File f;

    public UserDatabase(ArrayList<User> userArray, String fileName) {
        this.userArray = userArray;
        this.f = new File(fileName);
    }

    // Create multiple methods to allow users to
    // search for other users in a database.
    // Can search by name and username.
    // Names have at least one space, so you can differentiate
    // the search based on this.
    public ArrayList<Object> readDatabase() {
        ArrayList<Object> userArrayList = new ArrayList<>();
        FileInputStream fis;
        ObjectInputStream ois;
        try {

            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);

            User line = (User) ois.readObject();
            while (true) {
                userArrayList.add(line);
                line = (User) ois.readObject();
            }
        } catch (EOFException e) {
            return userArrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Writes all user data to one file, given the fileName.
    // Make sure to get all the fields on one line.
    public boolean writeDatabase(){
        try {

            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for (User user: userArray) {
                oos.writeObject(user);
            }

            oos.flush();
            fos.close();
            oos.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    // Finds the file with the same name as the string fileName and
    // returns a User object with the data in that file.
    public User retrieveUserData(String username) throws ActionNotAllowedException {
        try {

            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);

            while (true) {
                User user = (User) ois.readObject();
                if (user.getUsername().equals(username)) {
                    return user;
                }
            }
        } catch (EOFException e) {
            throw new ActionNotAllowedException("Username not in database");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}