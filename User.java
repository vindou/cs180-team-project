import java.io.Serializable;
import java.util.ArrayList;
/**
 * This class represents a User for the team project
 *
 *
 *
 * @author Jack Juncker, Ellie Williams
 * @version Mar 25th, 2024.
 */
public class User implements Serializable, UserInterface {
    private String birthday;
    private String bio;
    private String email;
    private String name;
    private String password;
    private String username;
    private ArrayList<User> friends = new ArrayList<>();
    private ArrayList<User> blocked = new ArrayList<>();

    // Instantiates an User object
    public User(String name, String email, String username, String password, String birthday) {
        try {
            this.name = name;
            this.email = email;
            this.username = username;
            if (username.contains(" "))
                throw new ActionNotAllowedException("Usernames cannot contain spaces.");
            this.password = encrypt(password);
            this.birthday = birthday;
        } catch (ActionNotAllowedException e) {
            e.printStackTrace();
        }
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

    public String getName()
    {
        return this.name;
    } // getName

    public void setName(String name)
    {
        this.name =  name;
    } // setName

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkPassword(String pass) {
        //Check the encrypted passwords against each other
        return (encrypt(pass).equals(this.password));
    } // checkPassword

    public void setUsername(String username)
    {
        this.username = username;
    } // setUsername

    @Override
    public void setFriends(ArrayList<User> friends) {

    }

    @Override
    public void setBlocked(ArrayList<User> blocked) {

    }

    public void setEmail(String email)
    {
        this.email = email;
    } // setEmail

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    } // setBirthday

    public ArrayList<User> getFriends() {
        return this.friends;
    } // getUsername

    public ArrayList<User> getBlocked() {
        return this.blocked;
    } // getUsername

    public void sendTextMessage(Conversation conversation, String message) throws ActionNotAllowedException {
        conversation.addMessage(new TextMessage(this, message));
    }

    public String toString()
    {
        //Format for user data in a class, password can be printed because it is encrypted
        return this.getUsername() + " " + this.getEmail() + " " + this.getBirthday() + this.getPassword();
    } // toString

    //Adds a User to the friends ArrayList<>
    public boolean addFriend(User user) {
        boolean success = false;
        if (!friends.contains(user)) {
            success = true;
            friends.add(user);
        }
        return success;
    } // addFriend

    // adds a User to the blocked Arraylist<>
    public boolean blockFriend(User user) {
        boolean success = false;
        if (!blocked.contains(user)) {
            success = true;
            blocked.add(user);
        }
        return success;
    } // blockFriend

    //removes a User from the friends ArrayList
    public boolean removeFriend(User user) {
        boolean success = false;
        if (friends.contains(user)) {
            friends.remove(user);
            success = true;
        }
        return success;
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
            return (name1.charAt(0) + (getFirstAlphabetically(name1.substring(1), name2.substring(1))));
        } else if (comparisonResult < 0) {
            return name1;
        } else {
            return name2;
        }
    }

    public boolean equals(Object comparedUser) {
        User translated = (User) comparedUser;
        boolean equality = false;
        if (this.username.equals(translated.getUsername())) {
            equality = true;
        }
        return equality;
    }
}