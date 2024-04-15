import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class _USERINTERFACE implements Runnable {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public _USERINTERFACE() {
        frame = new JFrame("User Interface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        // Adding your pages to the main panel
        mainPanel.add(createMainPage(), "Main Page");
        mainPanel.add(createRegistrationPanel(), "Register");
        mainPanel.add(createLogInPanel(), "LogIn");

        frame.add(mainPanel);
        frame.setSize(1000,800);
        frame.setVisible(true);
    }

    private JPanel createMainPage() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        gbc.anchor = GridBagConstraints.CENTER; // Center the components horizontally and vertically

        // Add label
        JLabel label = new JLabel("Welcome!");
        panel.add(label, gbc);

        // Add button
        gbc.gridy = 1;
        panel.add(logIn, gbc);

        gbc.gridy = 2;
        panel.add(register, gbc);

        logIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "LogIn");
            }
        });
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Register");
            }
        });

        return panel;
    }

    // FIELDS
    private JButton returnToMainPage = new JButton("Return to Main Page");

    // LOG IN FIELDS
    private JButton logIn = new JButton("Log In");

    // REGISTRATION FIELDS

    // String name, String email, String username, String password, String birthday
    private JButton register = new JButton("Register");
    public JLabel registration = new JLabel("Glad you're joining us!");
    private JTextField registerName = new JTextField(20);
    private JTextField registerEmail = new JTextField(20);
    private JTextField registerUsername = new JTextField(20);
    private JTextField registerPassword = new JTextField(20);
    private JTextField registerBirthday = new JTextField(20);
    private JButton finalRegistration = new JButton("Register!");

    private JPanel createRegistrationPanel() {
        GridBagConstraints global = new GridBagConstraints();
        global.gridx = 0;
        global.gridy = 0;
        global.insets = new Insets(10, 10, 10, 10);
        global.anchor = GridBagConstraints.CENTER;
        JPanel trueContent = new JPanel(new GridBagLayout());
        trueContent.add(returnToMainPage, global);
        global.gridy = 1;
        trueContent.add(registration, global);

        global.gridy = 2;
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Full Name"), gbc);
        gbc.gridx = 1;
        panel.add(registerName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Email Address"), gbc);
        gbc.gridx = 1;
        panel.add(registerEmail, gbc);
        gbc.gridx = 2;
        JLabel emailErrorLabel = new JLabel("Invalid email format");
        emailErrorLabel.setForeground(Color.RED);
        emailErrorLabel.setVisible(false); // Initially hidden
        panel.add(emailErrorLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Username"), gbc);
        gbc.gridx = 1;
        panel.add(registerUsername, gbc);
        gbc.gridx = 2;
        JLabel userNameErrorLabel = new JLabel("Invalid email format");
        userNameErrorLabel.setForeground(Color.RED);
        userNameErrorLabel.setVisible(false); // Initially hidden
        panel.add(userNameErrorLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Password"), gbc);
        gbc.gridx = 1;
        panel.add(registerPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Birthday"), gbc);
        gbc.gridx = 1;
        panel.add(registerBirthday, gbc);
        gbc.gridx = 2;
        JLabel birthdayErrorLabel = new JLabel("The valid birthday format is MM/DD/YYYY");
        birthdayErrorLabel.setForeground(Color.RED);
        birthdayErrorLabel.setVisible(false); // Initially hidden
        panel.add(birthdayErrorLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        finalRegistration.setVisible(false);
        panel.add(finalRegistration, gbc);


        registerEmail.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String email = registerEmail.getText();
                if (!isValidEmail(email)) {
                    emailErrorLabel.setVisible(true);
                } else {
                    emailErrorLabel.setVisible(false);
                }
            }
        });

        registerBirthday.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String birthday = registerBirthday.getText();
                if (!isValidBirthday(birthday)) {
                    birthdayErrorLabel.setVisible(true);
                } else {
                    birthdayErrorLabel.setVisible(false);
                }
            }
        });

        registerUsername.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String username = registerUsername.getText();
                if (!isValidUsername(username, new UserDatabase("userData.txt"))) {
                    userNameErrorLabel.setVisible(true);
                } else {
                    userNameErrorLabel.setVisible(false);
                }
            }
        });

        returnToMainPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Main Page");
            }
        });

        DocumentListener validationListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateData();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateData();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateData();
            }
        };

        registerEmail.getDocument().addDocumentListener(validationListener);
        registerUsername.getDocument().addDocumentListener(validationListener);
        registerBirthday.getDocument().addDocumentListener(validationListener);

        trueContent.add(panel, global);

        return trueContent;
    }

    private void validateData() {
        boolean isValid = true;
        if (!isValidBirthday(registerBirthday.getText())) {
            isValid = false;
        }
        if (!isValidEmail(registerEmail.getText())) {
            isValid = false;
        }

        if (!isValidUsername( registerUsername.getText(), new UserDatabase("userData.txt"))) {
            isValid = false;
        }
        finalRegistration.setEnabled(isValid);
    }

    private JPanel createLogInPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Register Page"));
        return panel;
    }

    public void run() {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new _USERINTERFACE());
    }

    // DATA VALIDATION
    public boolean isValidEmail(String email) {
        if (email.split("@").length != 2) {
            return false;
        } else {
            return true;
        }
    }
    public boolean isValidBirthday(String birthday) {
        if (birthday.split("/").length != 3) {
            return false;
        } else {
            boolean result = true;
            int currentEntry = 0;
            int[] entries = new int[3];
            String[] referenceList = birthday.split("/");

            for (int i = 0; i < referenceList.length; i++) {
                try {
                    int entry = Integer.parseInt(referenceList[i]);
                    if (i == 0 && (entry <= 0 || entry > 12)) {
                        result = false;
                        break;
                    } else if (i == 1 && (entry <= 0 || entry > 30)) {
                        result = false;
                        break;
                    } else if (i == 2 && (entry > 2024 || entry <= 0)) {
                        result = false;
                        break;
                    } else {
                        entries[i] = entry;
                    }
                } catch (NumberFormatException e) {
                    result = false;
                    break;
                }
            }

            return result;
        }
    }
    public boolean isValidUsername(String username, UserDatabase userDatabase) {
        boolean result = true;

        if (userDatabase.getUserArray() != null) {
            for (Object user : userDatabase.getUserArray()) {
                User translatedUser = (User) user;
                if (translatedUser.getUsername() == username) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }
}