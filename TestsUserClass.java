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

import static org.junit.Assert.*;

/**
 *
 * TestsUserClass
 *
 * This program tests the functionality of the User class in our project.
 *
 * @author Ellie Williams
 *
 * @version 04/01/24
 *
 */

@RunWith(Enclosed.class)
public class TestsUserClass {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestsUserClass.TestCase.class);
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
    /**
     *
     * TestsUserClass
     *
     * This program tests the functionality of the User class in our project.
     *
     * @author Ellie Williams
     *
     * @version 04/01/24
     *
     */
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
            assertFalse(user1.addFriend(user2));
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
            assertFalse(user1.blockFriend(user2));
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
            assertFalse(user1.removeFriend(user2));

            // Trims the output and verifies it is correct.
            assertTrue(!user1.getFriends().contains(user2));
        }

        // Tests removeFriend when the friend is already removed
        @Test(timeout = 5000)
        public void testRemoveFriendAlreadyRemoved() throws ActionNotAllowedException {
            User user1 = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("pete", "pete@purdue.edu", "purduepete", "purduemascot4", "08/02/1869");

            user1.addFriend(user2);
            // block user2 once - should be success
            user1.removeFriend(user2);

            //get a counter for user1's blocked list to ensure it doesn't increase when blocking user2 again
            int g = user1.getFriends().size();

            //try to block user2 again - should throw exception
            assertFalse(user1.removeFriend(user2));
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

        // tests checkPassword() method
        @Test(timeout = 5000)
        public void checkPasswordTest() {

            // Set the input
            User user = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");

            // Runs the program with the input values
            boolean success = user.checkPassword("purdue123");


            // ensure the method succeeds.
            assertTrue(success);
            System.out.println("checkPassword() success");
        }

        // tests setBio() method
        @Test(timeout = 5000)
        public void setBioTest() {

            // Set the input
            User user = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");

            String newBio = "I am a student at Purdue!";

            // Runs the program with the input values
            user.setBio(newBio);


            // ensure the method succeeds.
            assertEquals(user.getBio(), newBio);
            System.out.println("setBio() success");
        }

        // tests setBio() method
        @Test(timeout = 5000)
        public void getFriendsTest() {

            // Set the input
            User user1 = new User("ellie", "ellie@purdue.edu", "ellieW", "purdue1231", "03/16/2004");
            User user2 = new User("jack", "jack@purdue.edu", "jackJ", "purdue1232", "03/17/2004");
            User user3 = new User("gilbert", "gilbert@purdue.edu", "gilbertC", "purdue1233", "03/18/2004");
            User user4 = new User("sahil", "sahil@purdue.edu", "SahilS", "purdue1234", "03/19/2004");

            user1.addFriend(user2);
            user1.addFriend(user3);
            user1.addFriend(user4);

            ArrayList<User> test = new ArrayList<>();
            test.add(user2);
            test.add(user3);
            test.add(user4);

            // Runs the program with the input values
            ArrayList<User> friends = user1.getFriends();
            // ensure the method succeeds.
            assertEquals(friends, test);
        }
    }
}