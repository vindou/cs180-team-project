import java.util.ArrayList;
import javax.swing.*;
/**
 * This class just contains the UI stuff represents a User for the team project
 * 
 * 
 *
 * @author Jack Juncker, Ellie Williams, Gilbert Chang, Sahil Shetty
 * @version Mar 25th, 2024.
 */
public class UIStuff extends User {

    private static ArrayList<User> users = new ArrayList<>();
    
    // Instantiates an User object
    public UIStuff()
    {
        super();
    }

    public static String welcomeScreen() {
        String name; 
        do {
            // Define options
            Object[] options1 = {"Log In", "Create New Account", "Exit"};

            // Show dialog
            int reply = JOptionPane.showConfirmDialog(null, "What would you like to do?", "Welcome!", 
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options1, null);

            // Check user reply
            if (reply == JOptionPane.YES_OPTION) {
                //User Logs In
            } else if (reply == JOptionPane.NO_OPTION) {
                //User Creates New ACcount
            } else if (reply == JOptionPane.CANCEL_OPTION) {
                //User Exits
            } else if (reply == JOptionPane.CLOSED_OPTION) {
                //User closes window
            }
        
        } while ((name == null) || (name.isEmpty())); 
        
        return name;
    } //showNameInputDialog

    public static boolean login() {
        UIStuff uiStuff = new UIStuff();
        User account = new User();
        String username = uiStuff.getUsername();
        for (User user : users) {
            if (user.getUsername().equals(username));
                account = user;
        }
        
        String pass; 
        do {
            pass = JOptionPane.showInputDialog(null, "What is your password?",
                "Login", JOptionPane.QUESTION_MESSAGE);
            if (!(account.getPassword().equals(encrypt(pass)))) {
                JOptionPane.showMessageDialog(null, "Incorrect Password! Please try again.", "Incorrect Password!", 
                    JOptionPane.ERROR_MESSAGE);
                int options = JOptionPane.showConfirmDialog(null, "Would you like to exit?", "Exit", 
                    JOptionPane.YES_NO_OPTION);
                if (options == JOptionPane.YES_OPTION)
                    return false;
            }
            else if ((pass == null) || (pass.isEmpty())) {
                JOptionPane.showMessageDialog(null, "password cannot be empty!", "Error!", JOptionPane.ERROR_MESSAGE);
            } //end if
            else 
                break;
        
        } while (true); 
        return true;
    } //showNameInputDialog

    public String getUsername() {
        String username; 
        do {
            username = JOptionPane.showInputDialog(null, "What is your username?", "Login", 
                JOptionPane.QUESTION_MESSAGE);
            if ((username == null) || (username.isEmpty())) {
                JOptionPane.showMessageDialog(null, "username cannot be empty!", "Error!", JOptionPane.ERROR_MESSAGE);
    
            } //end if
        
        } while ((username == null) || (username.isEmpty())); 
        
        return username;
    } //getUsername

    public static boolean isDuplicateData(String string)
    {
        boolean checker = true;
        for (User user : users) {
            if (user.getUsername().equals(string));
                checker = false;
            if (user.getEmail().equals(string));
        }
        return checker;

    }

    public static void createAccount() {
        String username; 
        do {
            username = JOptionPane.showInputDialog(null, "Please enter a username", "Create Account", 
                JOptionPane.QUESTION_MESSAGE);
            if ((username == null) || (username.isEmpty())) {
                JOptionPane.showMessageDialog(null, "username cannot be empty!", "Error!", JOptionPane.ERROR_MESSAGE);
    
            } //end if
            if (isDuplicateData(username)) {
                JOptionPane.showMessageDialog(null, "This username is taken", "Error!", JOptionPane.ERROR_MESSAGE);
            }
            if (username.contains(" "))
                JOptionPane.showMessageDialog(null, "Username cannot contain spaces", "Error!", JOptionPane.ERROR_MESSAGE);
        } while ((username == null) || (username.isEmpty()) || isDuplicateData(username) || (username.contains(" ")));

        String name; 
        do {
            name = JOptionPane.showInputDialog(null, "Please enter your name", "Create Account", 
                JOptionPane.QUESTION_MESSAGE);
            if ((name == null) || (name.isEmpty())) {
                JOptionPane.showMessageDialog(null, "this field cannot be empty!", "Error!", JOptionPane.ERROR_MESSAGE);
    
            } 
        } while ((name == null) || (name.isEmpty()));

        String email; 
        do {
            email = JOptionPane.showInputDialog(null, "Please enter your email", "Create Account", 
                JOptionPane.QUESTION_MESSAGE);
            if ((email == null) || (email.isEmpty())) {
                JOptionPane.showMessageDialog(null, "this field cannot be empty!", "Error!", JOptionPane.ERROR_MESSAGE);
    
            } //end if
            if (isDuplicateData(email)) {
                JOptionPane.showMessageDialog(null, "This email is already being used", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        
        } while ((email == null) || (email.isEmpty()) || isDuplicateData(email));

        String birthday; 
        do {
            birthday = JOptionPane.showInputDialog(null, "Please enter your birthday", "Create Account", 
                JOptionPane.QUESTION_MESSAGE);
            if ((birthday == null) || (name.isEmpty())) {
                JOptionPane.showMessageDialog(null, "this field cannot be empty!", "Error!", JOptionPane.ERROR_MESSAGE);
            } 
        } while ((birthday == null) || (birthday.isEmpty()));

        String bio = "";
        int reply = JOptionPane.showConfirmDialog(null, "Would you like to add a bio now?", "Create account", 
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
    
        if (reply == JOptionPane.YES_OPTION) {
            bio = JOptionPane.showInputDialog(null, "Please enter bio", "Create Account", 
                JOptionPane.QUESTION_MESSAGE);
        } else if (reply == JOptionPane.NO_OPTION) {
            bio = null;
        }


        String password; 
        String passwordCheck;
        do {
            password = JOptionPane.showInputDialog(null, "Please enter a password", "Create Account", 
                JOptionPane.QUESTION_MESSAGE);
            if ((password == null) || (password.isEmpty())) {
                JOptionPane.showMessageDialog(null, "this field cannot be empty!", "Error!", JOptionPane.ERROR_MESSAGE);
            } 
            passwordCheck = JOptionPane.showInputDialog(null, "Please reenter password", "Create Account", 
                JOptionPane.QUESTION_MESSAGE);
            if (!(password.equals(passwordCheck))) {
                JOptionPane.showMessageDialog(null, "The password did not match!", "Error!", JOptionPane.ERROR_MESSAGE);
            } 
        } while ((password == null) || (password.isEmpty()) || (!(password.equals(passwordCheck))));
        password = encrypt(password);

        User newUser;
        try {
            newUser = new User(name, email, username, password, birthday);
            users.add(newUser);
        } catch (ActionNotAllowedException e) {
            e.printStackTrace();
        }
        
    } //newUser

    public User searchUser(String search)
    {
        User account = new User();
        for (User user : users) {
            if (user.getUsername().equals(search));
                account = user;
        }
        return account;
    } //searchUser

}

