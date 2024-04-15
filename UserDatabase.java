import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class UserDatabase implements Database {
    // An ArrayList containing every user on the platform.
    private ArrayList<Object> userArray;
    // The file name that the data should be written to.
    private String fileName;
    private File f;
    public UserDatabase(ArrayList<Object> userArray, String fileName) {
        this.userArray = userArray;
        this.fileName = fileName;
        this.f = new File(fileName);
    }
    public UserDatabase(String fileName) {
        this.fileName = fileName;
        this.f = new File(fileName);
        this.userArray = readDatabase();
    }
    // Create multiple methods to allow users to
    // search for other users in a database.
    // Can search by name and username.
    // Names have at least one space, so you can differentiate
    // the search based on this.
    public ArrayList<Object> readDatabase() {
        ArrayList<Object> userArrayList = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);

            User userFound = (User) ois.readObject();
            while (true) {
                userArrayList.add(userFound);
                userFound = (User) ois.readObject();
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
            for (Object user: userArray) {
                User translatedUser = (User) user;
                oos.writeObject(translatedUser);
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

    public ArrayList<Object> getUserArray() {
        return userArray;
    }

    public void setUserArray(ArrayList<Object> proposedArray) {
        this.userArray = proposedArray;
    }

    public ArrayList<User> searchForUsers(String query) {
        ArrayList<User> result = new ArrayList<>();
        for (Object objects : userArray) {
            User castUser = (User) objects;
            if (castUser.getUsername().contains(query)) {
                result.add(castUser);
            }
        }

        return result;
    }
}
