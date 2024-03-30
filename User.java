import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * This class represents a User for the team project
 *
 * 
 *
 * @author Jack Juncker, Ellie Williams
 * @version Mar 25th, 2024.
 */
public class User 
{
    private String birthday;
    private String bio;
    private String email;
    private String name;
    private String password;
    //make sure no spaces in username
    //Add usernumber to make database easier
    private String username;
    private ArrayList<User> friends = new ArrayList<>();
    private ArrayList<User> blocked = new ArrayList<>();
    
    // Instantiates an User object
    public User(String name, String email, String username, String password, String birthday) throws ActionNotAllowedException 
    {
        this.name = name;
        this.email = email;
        this.username = username;
        if (username.contains(" "))
            throw new ActionNotAllowedException("Usernames cannot contain spaces.");
        this.password = encrypt(password);
        this.birthday = birthday;
    }

    public User()
    {
        this.name = "";
        this.email = "";
        this.username = "";
        this.password = encrypt("");
        this.birthday = "";
    }

    public String getUsername() {
        return this.username;
    } //getUsername

    public String getEmail()
    {
        return this.email;
    }

    public String getBirthday()
    {
        return this.birthday;
    }

    public String getPassword()
    {
        return this.password;
    }

    public String getBio()
    {
        return this.bio;
    }

    public void setBio(String bio)
    {
        this.bio =  bio;
    }

    public boolean checkPassword(String pass) {
        return (encrypt(pass).equals(this.password));
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    public void sendTextMessage(Conversation conversation, String message) {
        conversation.addMessage(new TextMessage(this, message));
    }

    public String toString()
    {
        return this.getUsername() + " " + this.getEmail() + " " + this.getBirthday() + this.getPassword();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public ArrayList<User> addFriend(User user) {
        try {
            for (int i = 0; i < friends.size(); i++) {
                if (user.equals(friends.get(i))) {
                    throw new ActionNotAllowedException("Error: User already added");
                } else {
                    friends.add(user);
                }
            }
        } catch (ActionNotAllowedException e) {
            e.printStackTrace();
        }
        return friends;
    }

    public ArrayList<User> blockFriend(User user) {
        try {
            for (int i = 0; i < blocked.size(); i++) {
                if (user.equals(blocked.get(i))) {
                    throw new ActionNotAllowedException("Error: User is already blocked");
                } else {
                    blocked.add(user);
                }
            }
        } catch (ActionNotAllowedException e) {
            e.printStackTrace();
        }

        return blocked;
    }

    public ArrayList<User> removeFriend(User user) {
        try {
            for (int i = 0; i < friends.size(); i++) {
                if (user.equals(friends.get(i))) {
                    friends.remove(user);
                } else {
                    throw new ActionNotAllowedException("Error - User is not in friends list");
                }
            }
        } catch (ActionNotAllowedException e) {
            e.printStackTrace();
        }
            return friends;
    }

    public static String encrypt(String password)
    {
        try {
            // Create a MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance(password);
            
            // Update the digest with the input bytes
            byte[] hashBytes = digest.digest(password.getBytes());
            
            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            // Print the hexadecimal hash
            return (hexString.toString());
            
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Encryption algorithm not available.");
            e.printStackTrace();
        }
        return password;
    }

    public static String getFirstAlphabetically(String name1, String name2) {
        if (name1.isEmpty() && name2.isEmpty()) {
            return "Both names are equal alphabetically.";
        } else if (name1.isEmpty()) {
            return name1;
        } else if (name2.isEmpty()) {
            return name2;
        }

        // Comparing the starting characters of name1 and name2
        int comparisonResult = Character.compare(name1.charAt(0), name2.charAt(0));

        // If they are the same, compare the rest of the strings
        if (comparisonResult == 0) {
            return getFirstAlphabetically(name1.substring(1), name2.substring(1));
        } else if (comparisonResult < 0) {
            return name1;
        } else {
            return name2;
        }
    }

    //userSearch

    //userViewer
}