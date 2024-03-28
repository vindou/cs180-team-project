import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.*;
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
    private String password;
    //make sure no spaces in username
    //Add usernumber to make database easier
    private String username;
    private ArrayList<User> friends = new ArrayList<>();
    private ArrayList<User> blocked = new ArrayList<>();
    
    // Instantiates an User object
    public User(String email, String username, String password, String birthday) 
    {
        this.email = email;
        this.username = username;
        this.password = password;
        this.birthday = birthday;
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

    public boolean checkPassword(String pass) {
        return (pass.equals(this.password));
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

    public void sendMessage(String message)
    {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"));
            bw.append(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    //userSearch

    //userViewer
