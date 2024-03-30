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
    } // getUsername

    public String getEmail()
    {
        return this.email;
    } // getEmail

    public String getBirthday()
    {
        return this.birthday;
    } // getBirthday

    public String getPassword()
    {
        return this.password;
    } // getPassword

    public String getBio()
    {
        return this.bio;
    } // getBio

    public void setBio(String bio)
    {
        this.bio =  bio;
    } // setBio

    public boolean checkPassword(String pass) {
        //Check the encrypted passwords against each other
        return (encrypt(pass).equals(this.password));
    } // checkPassword

    public void setUsername(String username)
    {
        this.username = username;
    } // setUsername

    public void setEmail(String email)
    {
        this.email = email;
    } // setEmail
 
    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    } // setBirthday

    public void sendTextMessage(Conversation conversation, String message) {
        conversation.addMessage(new TextMessage(this, message));
    }    

    public String toString()
    {
        //Format for user data in a class, password can be printed because it is encrypted
        return this.getUsername() + " " + this.getEmail() + " " + this.getBirthday() + this.getPassword();
    } // toString

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

    //Adds a User to the friends ArrayList<>
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
    } // addFriend

    // adds a User to the blocked Arraylist<>
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
    } // blockFriend

    //removes a User from the friends ArrayList
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
    } // removeFriend

    // Encrypts the password to an different String by shifting characters by 5
    public static String encrypt(String text) 
    {
        int shiftBy = 5;
        String newPass = "";
        for (char character : text.toCharArray()) {
            char base;
            if (Character.isUpperCase(character)) {
                base = 'A';
            } else {
                base = 'a';
            }
            if (Character.isLetter(character)) {
                char encryptedChar = (char) (((character - base + shiftBy) % 26) + base);
                newPass += encryptedChar;
            } else {
                newPass += character;
            }
        }
        return newPass;
    }

    // helper method to create the file name for messages
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