import java.util.ArrayList;

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
    public ArrayList<User> addFriend(User user);
    public ArrayList<User> blockFriend(User user);
    public ArrayList<User> removeFriend(User user);

    // helper methods may be needed later
}
