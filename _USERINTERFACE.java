import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;

public class _USERINTERFACE implements Runnable {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private _CLIENTSENDER clientSender;
    private String[] registrationFieldData = new String[5];
    private String[] logInFieldData = new String[2];
    public _USERINTERFACE() {
        this.frame = new JFrame("User Interface");
        this.mainPanel = new JPanel();
        this.cardLayout = new CardLayout();
        this.clientSender = null;
    }

    public void updateLogInFieldData() {
        this.logInFieldData = new String[]{usernameField.getText()
                , passwordField.getText()};
    }
    public String[] getLogInFieldData() {
        return this.logInFieldData;
    }
    public String[] getRegistrationFieldData() {
        return this.registrationFieldData;
    }
    public void updateRegistrationFieldData() {
        this.registrationFieldData = new String[]{registerName.getText()
                , registerEmail.getText()
                , registerUsername.getText()
                , registerPassword.getText()
                , registerBirthday.getText()};
    }

    public void setClientSender(_CLIENTSENDER clientSender) {
        this.clientSender = clientSender;
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
    private JButton returnToMainPage2 = new JButton("Return to Main Page");

    // LOG IN FIELDS
    private JButton logIn = new JButton("Log In");
    private JLabel loggingIn = new JLabel("Welcome back!");
    private JTextField usernameField = new JTextField(10);
    private JTextField passwordField = new JTextField(10);
    private JButton finalLogIn = new JButton("Log In");

    private JPanel createLogInPanel() {
        GridBagConstraints global = new GridBagConstraints();
        global.gridx = 0;
        global.gridy = 0;
        global.insets = new Insets(10, 10, 10, 10);
        global.anchor = GridBagConstraints.CENTER;
        JPanel trueContent = new JPanel(new GridBagLayout());
        trueContent.add(returnToMainPage2, global);

        global.gridy = 1;
        trueContent.add(loggingIn, global);

        global.gridy = 2;
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel logInPrompts = new JPanel(new GridBagLayout());
        logInPrompts.add(new JLabel("Username"), gbc);

        gbc.gridx = 2;
        logInPrompts.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        logInPrompts.add(new JLabel("Password"), gbc);

        gbc.gridx = 2;
        logInPrompts.add(passwordField, gbc);
        trueContent.add(logInPrompts, global);

        global.gridx = 0;
        global.gridy = 3;
        trueContent.add(finalLogIn, global);

        returnToMainPage2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Main Page");
            }
        });

        finalLogIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (clientSender.getLogInSuccess()) {
                        cardLayout.show(mainPanel, "Conversation Main Page");
                        System.out.println("SUCCESSFUL LOGIN");
                        mainPanel.add(createConversationMainPage(),"Conversation Main Page");
                        cardLayout.show(mainPanel, "Conversation Main Page");
                    } else {
                        finalLogIn.setLabel("Account not found, try again.");
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        return trueContent;
    }

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
        JLabel userNameErrorLabel = new JLabel("Username not available");
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

        registerEmail.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!clientSender.getRegistrationFieldValidity()[0]) {
                    emailErrorLabel.setVisible(true);
                } else {
                    emailErrorLabel.setVisible(false);
                }
            }
        });

        registerBirthday.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (clientSender.getRegistrationFieldValidity()[2]) {
                    birthdayErrorLabel.setVisible(false);
                } else {
                    birthdayErrorLabel.setVisible(true);
                }
            }
        });

        registerUsername.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (clientSender.getRegistrationFieldValidity()[1]) {
                    userNameErrorLabel.setVisible(false);
                } else {
                    userNameErrorLabel.setVisible(true);
                }
            }
        });
        returnToMainPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Main Page");
            }
        });

        trueContent.add(panel, global);

        global.gridy = 3;
        finalRegistration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean validFields = true;
                    if (clientSender.registrationRequest(new User(registerName.getText(),
                            registerEmail.getText(),
                            registerUsername.getText(),
                            registerPassword.getText(),
                            registerBirthday.getText())) && validFields) {
                        System.out.println("SUCCESSFUL REGISTRATION");
                        mainPanel.add(createConversationMainPage(),"Conversation Main Page");
                        cardLayout.show(mainPanel, "Conversation Main Page");
                    } else {
                        finalRegistration.setLabel("Invalid Fields! Try again");
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        trueContent.add(finalRegistration, global);
        return trueContent;
    }

    // LOGGED IN PAGE
    String searchBarField = " Search for users";
    JTextField userSearchBar = new JTextField(searchBarField, 50);
    JButton searchButton = new JButton("Search");

    private JPanel searchBar() {
        GridBagConstraints global = new GridBagConstraints();
        global.gridx = 0;
        global.gridy = 0;
        global.insets = new Insets(30, 10, 10, 10);
        global.anchor = GridBagConstraints.WEST;
        JPanel trueContent = new JPanel(new GridBagLayout());

        userSearchBar.setBorder(new LineBorder(Color.black, 1));
        userSearchBar.setPreferredSize(new Dimension(750, 30));

        trueContent.add(userSearchBar, global);

        global.gridx = 1;
        searchButton.setPreferredSize(new Dimension(115, 40));
        trueContent.add(searchButton, global);

        return trueContent;
    }
    private JPanel searchResultsPageSearchBar() {
        GridBagConstraints global = new GridBagConstraints();
        global.gridx = 0;
        global.gridy = 0;
        global.insets = new Insets(30, 10, 10, 10);
        global.anchor = GridBagConstraints.WEST;
        JPanel trueContent = new JPanel(new GridBagLayout());

        userSearchBar.setBorder(new LineBorder(Color.black, 1));
        userSearchBar.setPreferredSize(new Dimension(750, 30));

        trueContent.add(userSearchBar, global);

        global.gridx = 1;
        searchButton.setPreferredSize(new Dimension(115, 40));
        trueContent.add(searchButton, global);

        return trueContent;
    }
    JButton returnToConversationPage = new JButton("Return to Conversations");

    private JPanel createSearchResultsPage() {
        String query = userSearchBar.getText();
        if (!query.equals(searchBarField)) {
            GridBagConstraints global = new GridBagConstraints();
            global.gridx = 0;
            global.gridy = 0;
            global.insets = new Insets(0, 10, 0, 10);
            global.anchor = GridBagConstraints.WEST;
            JPanel searchResultsPanel = new JPanel(new GridBagLayout());

            searchResultsPanel.add(returnToConversationPage, global);

            global.gridy = 1;
            searchResultsPanel.add(searchResultsPageSearchBar(), global);

            global.gridy = 2;
            searchResultsPanel.add(new JLabel("Users Found"), global);

            try {
                ArrayList<User> searchResults = clientSender.requestUserQuery(query);

                if (searchResults != null) {
                    if (searchResults.size() > 0) {
                        for (User user : searchResults) {
                            global.gridy++;
                            searchResultsPanel.add(userProfileButton(user), global);
                        }
                    } else {
                        global.gridy = 1;
                        searchResultsPanel.add(new JLabel("Sorry! No users found."));
                    }
                } else {
                    global.gridy = 1;
                    searchResultsPanel.add(new JLabel("Sorry! No users found."));
                }

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }

            returnToConversationPage.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainPanel.remove(3);
                    mainPanel.add(createConversationMainPage(),"Conversation Main Page");
                    cardLayout.show(mainPanel, "Conversation Main Page");
                }
            });

            return searchResultsPanel;
        } else {
            return null;
        }
    }
    private JButton conversationButton(Conversation conversation) {
        JButton button = new JButton(conversation.getConversationName());
        button.setPreferredSize(new Dimension(550, 35));

        button.setHorizontalTextPosition(SwingConstants.LEFT);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.add(createTextingPage(conversation), conversation.getConversationName());
                cardLayout.show(mainPanel, conversation.getConversationName());
            }
        });

        return button;
    }
    private JButton textMessageButton(Conversation assocConvo, TextMessage textMessage) {
        String messageString = textMessage.toString();

        JButton button = new JButton(messageString);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showOptionDialog(
                        null,
                        "User Actions",
                        "Actions: ",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new String[]{"Delete Message",
                                "Cancel"},
                        "Cancel");

                if (choice == JOptionPane.YES_OPTION) {
                    try {
                        System.out.println("deleting message, repopulating screen");
                        Conversation newConvo = clientSender.deleteMessageRequest(assocConvo, textMessage);
                        System.out.println("newConvo message size: " + newConvo.getMessages().size());
                        repopulateScreen(newConvo);
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        return button;
    }

    private JPanel createTextingPage(Conversation conversation) {
        GridBagConstraints global = new GridBagConstraints();
        global.gridx = 0;
        global.gridy = 0;
        global.insets = new Insets(0, 10, 15, 10);
        global.anchor = GridBagConstraints.WEST;
        JPanel trueContent = new JPanel(new GridBagLayout());
        JButton returnToConversationPage2 = new JButton("Return to Conversations");
        trueContent.add(returnToConversationPage2, global);

        global.gridy = 1;
        trueContent.add(new JLabel("Messages for "
                + conversation.getConversationName()),
                global);

        ArrayList<Message> messages = conversation.getMessages();

        global.anchor = GridBagConstraints.CENTER;
        if (!messages.isEmpty()) {
            for (Message message : messages) {
                TextMessage text = (TextMessage) message;
                global.gridy++;
                trueContent.add(textMessageButton(conversation, text), global);
            }
        } else {
            global.gridy++;
            trueContent.add(new JLabel("No messages here!"), global);
        }

        global.gridy++;
        JTextField messagingBar = new JTextField("Send a message!", 45);
        trueContent.add(messagingBar, global);

        global.gridx++;
        JButton sendButton = new JButton("Send");
        trueContent.add(sendButton, global);

        returnToConversationPage2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.remove(createConversationMainPage());
                mainPanel.remove(createTextingPage(conversation));
                mainPanel.add(createConversationMainPage(),"Conversation Main Page");
                cardLayout.show(mainPanel, "Conversation Main Page");
            }
        });
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("sending message, repopulating screen");
                    Conversation newConvo = clientSender.sendMessageRequest(conversation, messagingBar.getText());
                    System.out.println("newConvo message size: " + newConvo.getMessages().size());
                    repopulateScreen(newConvo);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        return trueContent;
    }

    private JButton userProfileButton(User user) {
        JButton button = new JButton(user.getUsername());
        button.setPreferredSize(new Dimension(165, 35));

        button.setHorizontalTextPosition(SwingConstants.LEFT);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showOptionDialog(
                        null,
                        "User Actions",
                        "Actions: ",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new String[]{"Add Friend",
                                "Block User",
                                "Start Conversation",
                                "Remove Friend"},
                        "Add Friend");

                if (choice == JOptionPane.YES_OPTION) {
                    try {
                        if (clientSender.addFriendRequest(user)) {
                            JOptionPane.showMessageDialog(null,
                                    "Success",
                                    "User Friended",
                                    JOptionPane.PLAIN_MESSAGE);
                            mainPanel.remove(3);
                            mainPanel.add(createConversationMainPage(),"Conversation Main Page");
                            cardLayout.show(mainPanel, "Conversation Main Page");
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "You cannot friend this user, they may be blocked,\n" +
                                            "or you may already be friends with them.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null,
                                "You cannot friend this user, they may be blocked,\n" +
                                        "or you may already be friends with them.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (choice == JOptionPane.NO_OPTION) {
                    try {
                        if (clientSender.blockUserRequest(user)) {
                            JOptionPane.showMessageDialog(null,
                                    "User Blocked",
                                    "Success",
                                    JOptionPane.PLAIN_MESSAGE);
                            mainPanel.remove(3);
                            mainPanel.add(createConversationMainPage(),"Conversation Main Page");
                            cardLayout.show(mainPanel, "Conversation Main Page");
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "You cannot block this user, they may be blocked already.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null,
                                "Error blocking user, try another time!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (choice == JOptionPane.CANCEL_OPTION) {
                    String conversationName = JOptionPane.showInputDialog(null,
                            "What would you like to name this chat?");
                    try {
                        if (clientSender.addConversationRequest(conversationName, user)) {
                            JOptionPane.showMessageDialog(null,
                                    "Conversation created!",
                                    "Success",
                                    JOptionPane.PLAIN_MESSAGE);
                            mainPanel.remove(3);
                            mainPanel.add(createConversationMainPage(),"Conversation Main Page");
                            cardLayout.show(mainPanel, "Conversation Main Page");
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "You cannot chat with this person.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            mainPanel.remove(3);
                            mainPanel.add(createConversationMainPage(),"Conversation Main Page");
                            cardLayout.show(mainPanel, "Conversation Main Page");
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null,
                                "Error creating conversation, try another time!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        mainPanel.remove(3);
                        mainPanel.add(createConversationMainPage(),"Conversation Main Page");
                        cardLayout.show(mainPanel, "Conversation Main Page");
                    }
                } else if (choice == 3) {
                    try {
                        if (clientSender.removeFriendRequest(user)) {
                            JOptionPane.showMessageDialog(null,
                                    "Success",
                                    "User Unfriended",
                                    JOptionPane.PLAIN_MESSAGE);
                            mainPanel.remove(3);
                            mainPanel.add(createConversationMainPage(),"Conversation Main Page");
                            cardLayout.show(mainPanel, "Conversation Main Page");
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "You cannot unfriend this user.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null,
                                "Error unfriending! Try again later.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        return button;
    }
    private JPanel conversationPanel() {
        GridBagConstraints global = new GridBagConstraints();
        global.gridx = 0;
        global.gridy = 0;
        global.insets = new Insets(0, 10, 15, 10);
        global.anchor = GridBagConstraints.WEST;
        JPanel trueContent = new JPanel(new GridBagLayout());

        trueContent.add(new JLabel("Conversations"), global);

        try {
            ArrayList<Conversation> conversations = clientSender.convosAvailableRequest();
            if (conversations != null) {
                if (conversations.size() > 0) {
                    global.anchor = GridBagConstraints.CENTER;
                    for (Conversation conversation : conversations) {
                        global.gridy++;
                        trueContent.add(conversationButton(conversation)
                                , global);
                    }
                } else {
                    global.anchor = GridBagConstraints.CENTER;
                    global.gridy = 1;
                    trueContent.add(new JLabel("No Conversations Available..."), global);
                }
            } else {
                global.anchor = GridBagConstraints.CENTER;
                global.gridy = 1;
                trueContent.add(new JLabel("No Conversations Available..."), global);
            }
        } catch (IOException | ClassNotFoundException e) {
            global.anchor = GridBagConstraints.CENTER;
            global.gridy = 1;
            trueContent.add(new JLabel("No Conversations Available..."), global);
        }

        return trueContent;
    }
    private JPanel friendsPanel() {
        GridBagConstraints global = new GridBagConstraints();
        global.gridx = 0;
        global.gridy = 0;
        global.insets = new Insets(0, 0, 10, 10);
        global.anchor = GridBagConstraints.WEST;
        JPanel trueContent = new JPanel(new GridBagLayout());

        try {
            ArrayList<User> friends = clientSender.requestFriends();
            if (friends != null && !friends.isEmpty()) {
                trueContent.removeAll(); // Clear the panel before adding buttons
                trueContent.add(new JLabel("Friends"), global); // Add the label
                global.gridy = 1;
                for (User friend : friends) {
                    trueContent.add(userProfileButton(friend), global);
                    global.gridy++; // Move to the next row
                }
            } else {
                // If there are no friends, display a message
                trueContent.removeAll(); // Clear the panel before adding the label
                trueContent.add(new JLabel("No Friends..."), global); // Add the label
            }
        } catch (IOException | ClassNotFoundException e) {
            // Handle exceptions
            trueContent.removeAll(); // Clear the panel before adding the error message
            trueContent.add(new JLabel("Error fetching friends..."), global); // Add the error message
        }
        return trueContent;
    }
    private JPanel createConversationMainPage() {
        GridBagConstraints global = new GridBagConstraints();
        global.gridx = 0;
        global.gridy = 0;
        global.insets = new Insets(1, 10, 1, 10);
        global.anchor = GridBagConstraints.CENTER;
        JPanel trueContent = new JPanel(new GridBagLayout());
        trueContent.add(new JLabel("Welcome to your home page!"), global);

        global.gridy = 1;
        trueContent.add(searchBar(), global);

        global.anchor = GridBagConstraints.CENTER;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel bottomContent = new JPanel(new GridBagLayout());

        bottomContent.add(conversationPanel(), gbc);
        gbc.gridx = 1;
        bottomContent.add(friendsPanel(), gbc);

        global.gridy = 2;
        trueContent.add(bottomContent, global);

        global.anchor = GridBagConstraints.CENTER;
        global.gridy = 3;
        JButton returnToLogIn = new JButton("Log Out");
        trueContent.add(returnToLogIn, global);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.add(createSearchResultsPage(), "Search Results");
                cardLayout.show(mainPanel, "Search Results");
            }
        });

        returnToLogIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameField.setText("");
                passwordField.setText("");
                System.out.println("LOGGED OUT");
                cardLayout.show(mainPanel, "Main Page");
            }
        });

        return trueContent;
    }

    public void repopulateScreen(Conversation conversation) {
        mainPanel.removeAll();
        mainPanel.add(createMainPage(), "Main Page");
        mainPanel.add(createRegistrationPanel(), "Register");
        mainPanel.add(createLogInPanel(), "LogIn");
        mainPanel.add(createConversationMainPage(),"Conversation Main Page");

        mainPanel.add(createTextingPage(conversation), conversation.getConversationName());
        cardLayout.show(mainPanel, conversation.getConversationName());
    }

    @Override
    public void run() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel.setLayout(cardLayout);

        // Adding your pages to the main panel
        mainPanel.add(createMainPage(), "Main Page");
        mainPanel.add(createRegistrationPanel(), "Register");
        mainPanel.add(createLogInPanel(), "LogIn");

        frame.add(mainPanel);
        frame.setSize(1000,800);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new _USERINTERFACE());
    }
}