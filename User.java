import java.util.ArrayList;

/**
 * This class represents a User for the team project
 *  hi
 * 
 *
 * @author Jack Juncker
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
    public User() 
    {
        this.email = setEmail();
        this.username = setUsername();
        this.password = setPassword();
        this.age = setAge();
    }

    public static String showNameInputDialog() {
        String name; 
        do {
            Object[] options1 = {"Log In", "Create an Account", "Quit"};
            int reply = JOptionPane.showConfirmDialog(null, "What would you like to do?",
                                          "Welcome!", JOptionPane.YES_NO_CANCEL_OPTION, null, options1, null);

            if (reply == JOptionPane.YES_OPTION) {
                login();
            } //end if
            else if (reply == JOptionPane.NO_OPTION) {
                createAccount();
            } //end if
            else 
                return null;
        
        } while ((name == null) || (name.isEmpty())); 
        
        return name;
    } //showNameInputDialog

    public static String login() {
        this.username = getUsername();
        if (!(checkPassword()))
        {
            return null;
        }

    } //showNameInputDialog

    public static String getUsername() {
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

    public static boolean checkPassword() {
        String pass; 
        do {
            pass = JOptionPane.showInputDialog(null, "What is your password?",
                "Login", JOptionPane.QUESTION_MESSAGE);
            if (!(pass.equals(this.password))) {
                JOptionPane.showMessageDialog(null, "Incorrect Password! Please try again.", "Incorrect Password!", 
                    JOptionPane.ERROR_MESSAGE);
                int options = JOptionPane.showConfirmDialog(null, "Would you like to exit?", "Exit", 
                    JOptionPane.YES_NO_OPTION);
                if (options == JOptionPane.YES_OPTION)
                    return false;
            else if ((pass == null) || (pass.isEmpty())) {
                JOptionPane.showMessageDialog(null, "password cannot be empty!", "Error!", JOptionPane.ERROR_MESSAGE);
            } //end if
        
        } while ((pass == null) || (pass.isEmpty()) || (!pass.equals(this.password))); 
        
        return true;
    } //getPassword

    public static void createAccount() {
        User newUser = new User();
    } //getPassword



}