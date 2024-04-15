import java.util.ArrayList;
/**
 * This interface lays out a lot of the methods as a base for the User.java class
 * More methods may need to be added, but these will be included
 *
 *
 *
 * @author Jack Juncker, Ellie Williams
 * @version Mar 25th, 2024.
 */
public interface UserInterface {
    // get_____ methods
    public String getBirthday();
    public String getBio();
    public String getEmail();
    public String getName();
    public String getPassword();
    public String getUsername();
    public ArrayList<User> getFriends();
    public ArrayList<User> getBlocked();

    // set______ methods
    public void setBirthday(String birthday);
    public void setBio(String bio);
    public void setEmail(String email);
    public void setName(String name);
    public void setPassword(String password);
    public void setUsername(String username);
    public void setFriends(ArrayList<User> friends);
    public void setBlocked(ArrayList<User> blocked);

    // friend related methods
    public boolean addFriend(User user);
    public boolean blockFriend(User user);
    public boolean removeFriend(User user);

    // helper methods may be needed later
}