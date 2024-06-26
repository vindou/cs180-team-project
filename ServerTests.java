import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.experimental.runners.Enclosed;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * ServerTests
 *
 * A program that tests the functionality of the processing that happens on the Server side of the database.
 *
 * @author Ellie Williams
 *
 * @version 04/13/2024
 */

@RunWith(Enclosed.class)
public class ServerTests {
    //Main Method to run test cases below.
    private static final String WELCOME_MESSAGE = "What would you like to do:\n1) Log In\n2) Create New Account";
    private static final String USERNAME_PROMPT = "Please enter your username: ";
    private static final String PASS_PROMPT = "Please enter your password: ";
    private static final String SUCCESS_MSG = "Success!";
    private static final String INCORRECT_MSG = "Incorrect password, would you like to try again? (yes / no)";
    private static final String GOODBYE_MESSAGE = "Goodbye!";
    private static final String TAKEN_MSG = "Sorry, that username is taken.";
    private static final String CREATE_USERNAME = "Please enter a username: ";
    private static final String CREATE_NAME = "Please enter your name: ";
    private static final String CREATE_EMAIL = "Please enter your email: ";
    private static final String CREATE_PASS = "Please enter a password: ";
    private static final String CREATE_BDAY = "Please enter your birthday: ";
    private static final String CONVO_MENU = "What would you like to do: \n1) View Conversations\n2) Start New Conversation\n3) Search Users\n4) See My Account\n5) Quit";
    private static final String CONVO_CHOICE = "What conversation number would you like to open?\n";
    private static final String WHAT_TO_SAY = "What would you like to say?/n";
    private static final String SEND_ANOTHER = "Would you like to send another message? ('yes' / 'no')\n";
    private static final String WHICH_USER = "What user would you like to start a new conversation with?\n";
    private static final String USERSEARCH_MENU = "Would you like to:\n1) Add as Friend\n2) Block User\n";
    private static final String EDITUSER_INITIAL = "Username: elliewilliams\nName: Ellie\n" +
            "Bio: Student at Purdue University\nEmail: will2613@purdue.edu";
    private static final String EDIT_MENU = "What would you like to edit:\n1) Change Username\n2) Change Name\n3) Update Bio\n4) Change Email";

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        System.out.printf("Test Count: %d.\n", result.getRunCount());
        if (result.wasSuccessful()) {
            System.out.printf("Excellent - all local tests ran successfully.\n");
        } else {
            System.out.printf("Tests failed: %d.\n", result.getFailureCount());
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    public static class TestCase {
        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;
        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayInputStream testIn;
        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayOutputStream testOut;

        @Before
        public void outputStart() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
        }

        @After
        public void restoreInputAndOutput() {
            System.setIn(originalSysin);
            System.setOut(originalOutput);
        }

        private String getOutput() {
            return testOut.toString();
        }

        @SuppressWarnings("SameParameterValue")
        private void receiveInput(String str) {
            testIn = new ByteArrayInputStream(str.getBytes());
            System.setIn(testIn);
        }

        //TEST 1 - Logging In

        @Test(timeout = 1000)
        public void logInCorrectPassword() {
            // existing users in the database
            User user1 = new User("Ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");

            // Set the input
            String input = "1" + System.lineSeparator() + "elliewilliams" + System.lineSeparator() + "purdue123" +
                    System.lineSeparator();

            // Pair the input with the expected result
            String expected = WELCOME_MESSAGE + System.lineSeparator() + USERNAME_PROMPT + System.lineSeparator() + PASS_PROMPT + SUCCESS_MSG;


            // Get the captured output from the outputStream
            String capturedOutput = getOutput();


            // Trims the output and verifies it is correct.
            capturedOutput = capturedOutput.replace("\r\n", "\n");
            expected = expected.replace("\r\n", "\n");
            assertEquals(expected.trim(), capturedOutput.trim());
        }


        //TEST 2 - Logging In wrong password

        @Test(timeout = 1000)
        public void logInWrongPassword() {
            //existing users in the database
            User user1 = new User("Ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");

            // Set the input
            String input = "1" + System.lineSeparator() + "elliewilliams" + System.lineSeparator() + "purdue122" +
                    System.lineSeparator() + "yes" + System.lineSeparator() + "purdue123";

            // Pair the input with the expected result
            String expected = WELCOME_MESSAGE + System.lineSeparator()
                    + USERNAME_PROMPT + System.lineSeparator() + PASS_PROMPT + System.lineSeparator() +
                    INCORRECT_MSG + System.lineSeparator() + PASS_PROMPT + System.lineSeparator() + SUCCESS_MSG;

            // Runs the program with the input values
            receiveInput(input);


            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            expected = expected.replace("\r\n", "\n");
            assertEquals(expected.trim(), output.trim());
        }

        //TEST 3 - Logging In wrong password, then just give up

        @Test(timeout = 1000)
        public void logInWrongPasswordThenQuit() {

            //"existing users" in the database
            User user1 = new User("Ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");

            // Set the input
            String input = "1" + System.lineSeparator() + "elliewilliams" + System.lineSeparator() + "purdue122" +
                    System.lineSeparator() + "no";

            // Pair the input with the expected result
            String expected = WELCOME_MESSAGE + System.lineSeparator()
                    + USERNAME_PROMPT + System.lineSeparator() + PASS_PROMPT + System.lineSeparator()
                    + INCORRECT_MSG + System.lineSeparator() + GOODBYE_MESSAGE;

            // Runs the program with the input values
            receiveInput(input);


            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            expected = expected.replace("\r\n", "\n");
            assertEquals(expected.trim(), output.trim());
        }

        //TEST 4 - Create a new User when desired username is available

        @Test(timeout = 1000)
        public void createNewUserNoIssues() {
            // existing users in the server
            User user1 = new User("Ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("Pete", "pete@purdue.edu", "therealpurduepete", "pete456", "08/02/1869");

            // Set the input
            String input = "2" + System.lineSeparator() + "jjuncker" + System.lineSeparator() + "Jack"
                    + System.lineSeparator() + "ilovecs" + System.lineSeparator() + "04/13/2024";

            // Pair the input with the expected result
            String expected = WELCOME_MESSAGE + System.lineSeparator()
                    + CREATE_USERNAME + System.lineSeparator() + CREATE_NAME + System.lineSeparator()
                    + CREATE_EMAIL + System.lineSeparator() + CREATE_PASS + System.lineSeparator() + CREATE_BDAY;

            // Runs the program with the input values
            receiveInput(input);


            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            expected = expected.replace("\r\n", "\n");
            assertEquals(expected.trim(), output.trim());
        }

        //TEST 5 - Create a new User when desired username is not available

        @Test(timeout = 1000)
        public void createNewUserNotAvailable() {
            // existing users in the server
            User user1 = new User("Ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("Pete", "pete@purdue.edu", "therealpurduepete", "pete456", "08/02/1869");

            // Set the input
            String input = "2" + System.lineSeparator() + "elliewilliams" + System.lineSeparator() + "elliewilliams3" + System.lineSeparator() + "Ellie"
                    + System.lineSeparator() + "purdue123" + System.lineSeparator() + "03/16/2004";

            // Pair the input with the expected result
            String expected = WELCOME_MESSAGE + System.lineSeparator()
                    + CREATE_USERNAME + System.lineSeparator() + TAKEN_MSG + System.lineSeparator() + CREATE_USERNAME + System.lineSeparator() + CREATE_NAME + System.lineSeparator()
                    + CREATE_EMAIL + System.lineSeparator() + CREATE_PASS + System.lineSeparator() + CREATE_BDAY;

            // Runs the program with the input values
            receiveInput(input);


            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            expected = expected.replace("\r\n", "\n");
            assertEquals(expected.trim(), output.trim());
        }


        //TEST 6 - text in existing conversation

        @Test(timeout = 1000)
        public void existingConversationTest() {
            // existing users in the server
            User user1 = new User("Ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("Pete", "pete@purdue.edu", "therealpurduepete", "pete456", "08/02/1869");
            User user3 = new User("Michael", "michaelscott@dundermifflin.org", "michaelgscott", "scott127", "05/08/1985");

            ArrayList<User> usersInConvo1 = new ArrayList<>();
            usersInConvo1.add(user1);
            usersInConvo1.add(user2);
            ConversationDatabase cdb = new ConversationDatabase("existingConversationTest.txt");

            Conversation conversation1 = new Conversation("Conversation1", usersInConvo1, cdb);

            ArrayList<User> usersInConvo2 = new ArrayList<>();
            usersInConvo1.add(user2);
            usersInConvo1.add(user3);

            Conversation conversation2 = new Conversation("Conversation2", usersInConvo2, cdb);
            usersInConvo2.add(user2);
            usersInConvo2.add(user3);

            ArrayList<Conversation> convosForUser = new ArrayList<Conversation>();
            convosForUser.add(conversation1);

            // Set the input
            String input = "1" + System.lineSeparator() + "1" + System.lineSeparator() + "Hello"
                    + System.lineSeparator() + "yes" + System.lineSeparator() + "ilovecs" + System.lineSeparator() + "no";

            // Pair the input with the expected result
            String expected = CONVO_MENU + System.lineSeparator()
                    + CONVO_CHOICE + System.lineSeparator()
                    + "1. Conversation with Purdue Pete" + System.lineSeparator() + WHAT_TO_SAY + System.lineSeparator()
                    + SEND_ANOTHER + System.lineSeparator() + WHAT_TO_SAY;

            // Runs the program with the input values
            receiveInput(input);


            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            expected = expected.replace("\r\n", "\n");
            assertEquals(expected.trim(), output.trim());
        }

        //Test 7 - create new conversation

        @Test(timeout = 1000)
        public void newConversationTest() {
            // existing users in the server
            User user1 = new User("Ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("Pete", "pete@purdue.edu", "therealpurduepete", "pete456", "08/02/1869");
            User user3 = new User("Michael", "michaelscott@dundermifflin.org", "michaelgscott", "scott127", "05/08/1985");

            // Set the input
            String input = "2" + System.lineSeparator() + "Pete"
                    + System.lineSeparator() + "Hi Pete";

            // Pair the input with the expected result
            String expected = CONVO_MENU + System.lineSeparator()
                    + WHICH_USER + System.lineSeparator() + WHAT_TO_SAY;

            // Runs the program with the input values
            receiveInput(input);


            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            expected = expected.replace("\r\n", "\n");
            assertEquals(expected.trim(), output.trim());
        }
        
        //Test 8 - search user and add them as a friend

        @Test(timeout = 1000)
        public void searchUserAddFriendTest() {
            // existing users in the server
            User user1 = new User("Ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("Pete", "pete@purdue.edu", "therealpurduepete", "pete456", "08/02/1869");
            User user3 = new User("Michael", "michaelscott@dundermifflin.org", "michaelgscott", "scott127", "05/08/1985");

            // Set the input
            String input = "3" + System.lineSeparator() + "therealpurduepete"
                    + System.lineSeparator() + "1";

            // Pair the input with the expected result
            String expected = CONVO_MENU + System.lineSeparator()
                    + "Enter the username to search: " + System.lineSeparator() + "User found:\n" +
                    "Username: therealpurduepete\nName: Pete\nBio: Mascot at Purdue University" + USERSEARCH_MENU + System.lineSeparator() + "User added as friend.";
                    

            // Runs the program with the input values
            receiveInput(input);


            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            expected = expected.replace("\r\n", "\n");
            assertEquals(expected.trim(), output.trim());
        }
        
        //Test 9 - search User and block them

        @Test(timeout = 1000)
        public void searchUserBlockTest() {
            // existing users in the server
            User user1 = new User("Ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("Pete", "pete@purdue.edu", "therealpurduepete", "pete456", "08/02/1869");
            User user3 = new User("Michael", "michaelscott@dundermifflin.org", "michaelgscott", "scott127", "05/08/1985");

            // Set the input
            String input = "3" + System.lineSeparator() + "therealpurduepete"
                    + System.lineSeparator() + "2";

            // Pair the input with the expected result
            String expected = CONVO_MENU + System.lineSeparator()
                    + "Enter the username to search: " + System.lineSeparator() + "User found:\n" +
                    "Username: therealpurduepete\nName: Pete\nBio: Mascot at Purdue University" + USERSEARCH_MENU + System.lineSeparator() + "User blocked.";


            // Runs the program with the input values
            receiveInput(input);


            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            expected = expected.replace("\r\n", "\n");
            assertEquals(expected.trim(), output.trim());
        }

        //Test 10 - search User and do something not in the menu

        @Test(timeout = 1000)
        public void searchUserInvalidTest() {
            // existing users in the server
            User user1 = new User("Ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("Pete", "pete@purdue.edu", "therealpurduepete", "pete456", "08/02/1869");
            User user3 = new User("Michael", "michaelscott@dundermifflin.org", "michaelgscott", "scott127", "05/08/1985");

            // Set the input
            String input = "3" + System.lineSeparator() + "therealpurduepete"
                    + System.lineSeparator() + "3";

            // Pair the input with the expected result
            String expected = CONVO_MENU + System.lineSeparator()
                    + "Enter the username to search: " + System.lineSeparator() + "User found:\n" +
                    "Username: therealpurduepete\nName: Pete\nBio: Mascot at Purdue University" + USERSEARCH_MENU + System.lineSeparator() + "Invalid choice.";


            // Runs the program with the input values
            receiveInput(input);


            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            expected = expected.replace("\r\n", "\n");
            assertEquals(expected.trim(), output.trim());
        }

        //Test 11 - search User but the User does not exist in the database

        @Test(timeout = 1000)
        public void searchUserNotFoundTest() {
            // existing users in the server
            User user1 = new User("Ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("Pete", "pete@purdue.edu", "therealpurduepete", "pete456", "08/02/1869");
            User user3 = new User("Michael", "michaelscott@dundermifflin.org", "michaelgscott", "scott127", "05/08/1985");

            // Set the input
            String input = "3" + System.lineSeparator() + "gilbertchang";

            // Pair the input with the expected result
            String expected = CONVO_MENU + System.lineSeparator()
                    + "Enter the username to search: " + System.lineSeparator() + "User not found.";

            // Runs the program with the input values
            receiveInput(input);


            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            expected = expected.replace("\r\n", "\n");
            assertEquals(expected.trim(), output.trim());
        }

        //Test 12 - Edit username with no issues

        @Test(timeout = 1000)
        public void editUsernameUniqueTest() {
            // existing users in the server
            User user1 = new User("Ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("Pete", "pete@purdue.edu", "therealpurduepete", "pete456", "08/02/1869");
            User user3 = new User("Michael", "michaelscott@dundermifflin.org", "michaelgscott", "scott127", "05/08/1985");

            // Set the input
            String input = "4" + System.lineSeparator() + "1"
                    + System.lineSeparator() + "elliewi11iams";

            // Pair the input with the expected result
            String expected = CONVO_MENU + System.lineSeparator()
                    + EDITUSER_INITIAL + EDIT_MENU + System.lineSeparator() + 
                    "Enter new username:\n" + System.lineSeparator() + "Username updated successfully.";


            // Runs the program with the input values
            receiveInput(input);


            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            expected = expected.replace("\r\n", "\n");
            assertEquals(expected.trim(), output.trim());
        }

        //Test 12 - Edit username with no issues

        @Test(timeout = 1000)
        public void editUsernameTakenTest() {
            // existing users in the server
            User user1 = new User("Ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("Pete", "pete@purdue.edu", "therealpurduepete", "pete456", "08/02/1869");
            User user3 = new User("Michael", "michaelscott@dundermifflin.org", "michaelgscott", "scott127", "05/08/1985");

            // Set the input
            String input = "4" + System.lineSeparator() + "1"
                    + System.lineSeparator() + "elliewilliams";

            // Pair the input with the expected result
            String expected = CONVO_MENU + System.lineSeparator()
                    + EDITUSER_INITIAL + EDIT_MENU + System.lineSeparator() +
                    "Enter new username:\n" + System.lineSeparator() + "Sorry, that username is already taken.";


            // Runs the program with the input values
            receiveInput(input);


            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            expected = expected.replace("\r\n", "\n");
            assertEquals(expected.trim(), output.trim());
        }

        //Test 13 - Change name

        @Test(timeout = 1000)
        public void editNameTest() {
            // existing users in the server
            User user1 = new User("Ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("Pete", "pete@purdue.edu", "therealpurduepete", "pete456", "08/02/1869");
            User user3 = new User("Michael", "michaelscott@dundermifflin.org", "michaelgscott", "scott127", "05/08/1985");

            // Set the input
            String input = "4" + System.lineSeparator() + "2"
                    + System.lineSeparator() + "Elizabeth";

            // Pair the input with the expected result
            String expected = CONVO_MENU + System.lineSeparator()
                    + EDITUSER_INITIAL + EDIT_MENU + System.lineSeparator() +
                    "Enter new name:\n" + System.lineSeparator() + "Name updated successfully.";


            // Runs the program with the input values
            receiveInput(input);


            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            expected = expected.replace("\r\n", "\n");
            assertEquals(expected.trim(), output.trim());
        }

        //Test 14 - Update bio

        @Test(timeout = 1000)
        public void updateBioTest() {
            // existing users in the server
            User user1 = new User("Ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("Pete", "pete@purdue.edu", "therealpurduepete", "pete456", "08/02/1869");
            User user3 = new User("Michael", "michaelscott@dundermifflin.org", "michaelgscott", "scott127", "05/08/1985");

            // Set the input
            String input = "4" + System.lineSeparator() + "3"
                    + System.lineSeparator() + "I study statistics at Purdue.";

            // Pair the input with the expected result
            String expected = CONVO_MENU + System.lineSeparator()
                    + EDITUSER_INITIAL + EDIT_MENU + System.lineSeparator() +
                    "Enter new bio:\n" + System.lineSeparator() + "Bio updated successfully.";


            // Runs the program with the input values
            receiveInput(input);


            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            expected = expected.replace("\r\n", "\n");
            assertEquals(expected.trim(), output.trim());
        }

        //Test 15 - change email

        @Test(timeout = 1000)
        public void changeEmail() {
            // existing users in the server
            User user1 = new User("Ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("Pete", "pete@purdue.edu", "therealpurduepete", "pete456", "08/02/1869");
            User user3 = new User("Michael", "michaelscott@dundermifflin.org", "michaelgscott", "scott127", "05/08/1985");

            // Set the input
            String input = "4" + System.lineSeparator() + "4"
                    + System.lineSeparator() + "elliewilliams316@gmail.com";

            // Pair the input with the expected result
            String expected = CONVO_MENU + System.lineSeparator()
                    + EDITUSER_INITIAL + EDIT_MENU + System.lineSeparator() +
                    "Enter new email:\n" + System.lineSeparator() + "Email updated successfully.";


            // Runs the program with the input values
            receiveInput(input);


            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            expected = expected.replace("\r\n", "\n");
            assertEquals(expected.trim(), output.trim());
        }

        //Test 16 - invalid EditMenu choice

        @Test(timeout = 1000)
        public void invalidEditChoiceTest() {
            // existing users in the server
            User user1 = new User("Ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("Pete", "pete@purdue.edu", "therealpurduepete", "pete456", "08/02/1869");
            User user3 = new User("Michael", "michaelscott@dundermifflin.org", "michaelgscott", "scott127", "05/08/1985");

            // Set the input
            String input = "4" + System.lineSeparator() + "5";

            // Pair the input with the expected result
            String expected = CONVO_MENU + System.lineSeparator()
                    + EDITUSER_INITIAL + EDIT_MENU + System.lineSeparator() +
                    "Invalid choice.";


            // Runs the program with the input values
            receiveInput(input);


            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            expected = expected.replace("\r\n", "\n");
            assertEquals(expected.trim(), output.trim());
        }


        }
    }
