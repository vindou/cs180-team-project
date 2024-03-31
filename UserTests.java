import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * This class tests the various methods in the User class
 *
 *
 *
 * @author Ellie Williams
 * @version Mar 30th, 2024.
 */

import static org.junit.Assert.assertEquals;

public class UserTests {

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

        //test the addFriend method when no exceptions are thrown

        @Test(timeout = 5000)
        public void testaddFriendSuccess() {

            // Set the input
            User user1 = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("pete", "pete@purdue.edu", "purduepete", "purduemascot4", "08/02/1869");

            // Runs the program with the input values
            user1.addFriend(user2);


            // ensure user2 is in user1's friend list.
            assertTrue(user1.getFriends().contains(user2));
        }

        //tests addFriend when an exception is thrown

        @Test(timeout = 5000)
        public void testAddFriendAlreadyAdded() {
            User user1 = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("pete", "pete@purdue.edu", "purduepete", "purduemascot4", "08/02/1869");

            // add user2 once - should be success
            user1.addFriend(user2);

            //get a counter for user1's friends to ensure it doesn't increase when adding user2 again
            int f = user1.getFriends().size();

            //try to add user2 again - should throw exception
            user1.addFriend(user2);

            //make sure user1 has the same friend list as before the repeated action occurred
            assertEquals(f, user1.getFriends().size());

            //make sure user2 is still in user1's friend list
            assertTrue(user1.getFriends().contains(user2));

        }

        //tests blockUser when no exceptions are thrown
        @Test(timeout = 5000)
        public void testBlockUserSuccess() {

            // Set the input
            User user1 = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("pete", "pete@purdue.edu", "purduepete", "purduemascot4", "08/02/1869");

            // Runs the program with the input values
            user1.blockFriend(user2);


            // Trims the output and verifies it is correct.
            assertTrue(user1.getBlocked().contains(user2));
        }


        // Tests blockUser when an exception is thrown
        @Test(timeout = 5000)
        public void testBlockUserAlreadyBlocked() {
            User user1 = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("pete", "pete@purdue.edu", "purduepete", "purduemascot4", "08/02/1869");


            // block user2 once - should be success
            user1.blockFriend(user2);

            //get a counter for user1's blocked list to ensure it doesn't increase when blocking user2 again
            int b = user1.getBlocked().size();

            //try to block user2 again - should throw exception
            user1.blockFriend(user2);

            //make sure user1 has the same blocked list as before the repeated action occurred
            assertEquals(user1.getBlocked().size(), b);

            //make sure user2 is still in user1's friend list
            assertTrue(user1.getBlocked().contains(user2));

        }

        // Tests removeFriend method when no exceptions are thrown
        @Test(timeout = 5000)
        public void testRemoveFriendSuccess() {

            // Set the input
            User user1 = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("pete", "pete@purdue.edu", "purduepete", "purduemascot4", "08/02/1869");

            // Runs the program with the input values
            user1.removeFriend(user2);

            // Trims the output and verifies it is correct.
            assertTrue(!user1.getFriends().contains(user2));
        }

        // Tests removeFriend when the friend is already removed
        @Test(timeout = 5000)
        public void testRemoveFriendAlreadyRemoved() {
            User user1 = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("pete", "pete@purdue.edu", "purduepete", "purduemascot4", "08/02/1869");


            // block user2 once - should be success
            user1.removeFriend(user2);

            //get a counter for user1's blocked list to ensure it doesn't increase when blocking user2 again
            int g = user1.getFriends().size();

            //try to block user2 again - should throw exception
            user1.removeFriend(user2);

            //make sure user1 has the same blocked list as before the repeated action occurred
            assertEquals(user1.getFriends().size(), g);

            //make sure user2 is still in user1's friend list
            assertTrue(!user1.getFriends().contains(user2));

        }


        // Tests the getFirstAlphabetically method when both names are empty
        @Test(timeout = 5000)
        public void testGetFirstAlphabeticallyBothEmpty() {

            // names for both users
            String name1 = "";
            String name2 = "";

            //returns program output
            String result = User.getFirstAlphabetically(name1, name2);

            //compare expected/actual output
            assertEquals("Both names are equal alphabetically.", result);
        }

        // Tests the compare alphabetically method when name1 is empty
        @Test(timeout = 5000)
        public void testGetFirstAlphabeticallyName1Empty() {

            // names for both users
            String name1 = "";
            String name2 = "Pete";

            //returns program output
            String result = User.getFirstAlphabetically(name1, name2);

            //compare expected/actual output
            assertEquals(name1, result);
        }

        // Tests the compare alphabetically method when name2 is empty
        @Test(timeout = 5000)
        public void testGetFirstAlphabeticallyName2Empty() {

            // names for both users
            String name1 = "Ellie";
            String name2 = "";

            //returns program output
            String result = User.getFirstAlphabetically(name1, name2);

            //compare expected/actual output
            assertEquals(name2, result);
        }

        // Tests the compare alphabetically method when both names aren't empty
        @Test(timeout = 5000)
        public void testGetFirstAlphabeticallyBothNotEmpty() {

            // names for both users
            String name1 = "Ellie";
            String name2 = "Pete";

            //returns program output
            String result = User.getFirstAlphabetically(name1, name2);

            //compare expected/actual output
            assertEquals(name1, result);
        }

        // Tests the compare alphabetically method when both names start with the same letter
        @Test(timeout = 5000)
        public void testGetFirstAlphabeticallyBothStartWithSameLetter() {

            // names for both users
            String name1 = "John";
            String name2 = "Jack";

            //returns program output
            String result = User.getFirstAlphabetically(name1, name2);

            //compare expected/actual output
            assertEquals(name2, result);
        }

        // tests the encryptPassword method
        @Test(timeout = 5000)
        public void testEncryptPassword() {

            // names for both users
            String password = "password";


            //returns program output
            String result = User.encrypt(password);

            //compare expected/actual output
            assertEquals(result, "ufxxbtwi");
        }

        // MESSAGE SUPERCLASS TEST CASES
        @Test(timeout = 2000)
        public void testMessageSuperClassConstructor() {
            User testUser = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            Message testMessage = new Message(testUser);

            User comparedUser = testMessage.getSender();
            int comparedIndex = testMessage.getIndex();

            assertEquals(comparedUser, testUser);
            assertEquals(0, comparedIndex);
        }

        @Test(timeout = 2000)
        public void testMessageIndexSetter() {
            User testUser = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            Message testMessage = new Message(testUser);

            int testIndex = 4;
            try{
                testMessage.setIndex(testIndex);
            } catch (ActionNotAllowedException ignored) {}
            int comparedIndex = testMessage.getIndex();

            assertEquals(testIndex, comparedIndex);
        }

        // TEXTMESSAGE SUBCLASS TESTS

        @Test(timeout = 2000)
        public void testTextMessageConstructor() {
            User testUser = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            TextMessage testMessage = new TextMessage(testUser, "hi");

            assertEquals(testMessage.getMessage(), "hi");
        }

        @Test(timeout = 2000)
        public void testTextMessageChangeMessageMethod() {
            User testUser = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            TextMessage testMessage = new TextMessage(testUser, "hi");
            testMessage.changeMessage("ho");
            assertEquals(testMessage.getMessage(), "ho");
        }

        @Test(timeout = 2000)
        public void testTextMessageEqualityMethod() {
            User testUser = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User testUser2 = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            TextMessage testMessage = new TextMessage(testUser, "hi");
            TextMessage testMessage2 = new TextMessage(testUser, "hi");

            assertTrue(testMessage.equals(testMessage2));
        }

        @Test(timeout = 2000)
        public void testTextMessageToStringMethod() {
            User testUser = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            TextMessage testMessage = new TextMessage(testUser, "hi");

            String textMessageString = testMessage.toString();
            String expectedString = "elliewilliams" + ":[" + testMessage.getTimeSent() + "]: \""
                    + "hi"
                    + "\"";

            assertEquals(textMessageString, expectedString);
        }

        // CONVERSATION AND CONVERSATION DATABASE CLASS TESTS

        @Test(timeout = 2000)
        public void testConversationAndDatabaseConstructor() {
            User user1 = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("pete", "pete@purdue.edu", "purduepete", "purduemascot4", "08/02/1869");

            ArrayList<User> testArray = new ArrayList<User>();
            testArray.add(user1);
            testArray.add(user2);

            ConversationDatabase cd = new ConversationDatabase("output.txt");
            Conversation testConversation = new Conversation("cs 180 group", testArray, cd);

            assertNull(cd.getConversationArray());
            assertEquals("output.txt", cd.getFilePath());
            assertTrue(testConversation.getID() > 0);
            assertEquals(2, testConversation.getUsers().size());
            assertEquals(0, testConversation.getMessages().size());
        }

        @Test(timeout = 2000)
        public void testAvailableConversationFinder() {
            ConversationDatabase cd = new ConversationDatabase("output.txt");
            User user1 = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");

            assertEquals(0, cd.findAvailableConversations(user1).size());
        }

        @Test(timeout = 2000)
        public void testUserManipulationMethods() {

        }


    }

}
