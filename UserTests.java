import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class tests the various methods in the User class
 *
 *
 *
 * @author Ellie Williams
 * @version Mar 30th, 2024.
 */

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * A framework to run public test cases.
 *
 * <p>Purdue University -- CS18000 -- Spring 2024</p>
 *
 * @author Purdue CS
 * @version Jan 13, 2024
 */
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

    /**
     * A framework to run public test cases.
     *
     * <p>Purdue University -- CS18000 -- Spring 2024</p>
     *
     * @author Purdue CS
     * @version Jan 13, 2024
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

        //TEST 1

        @Test(timeout = 1000)
        public void testaddFriendSuccess() {

            // Set the input
            User user1 = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("pete", "pete@purdue.edu", "purduepete", "purduemascot4", "08/02/1869");

            // Runs the program with the input values
            user1.addFriend(user2);



            // ensure user2 is in user1's friend list.
            assertTrue(user1.getFriends().contains(user2));
        }

        //TEST 2

        @Test(timeout = 1000)
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
            assertEquals(user1.getFriends().size() == f);

            //make sure user2 is still in user1's friend list
            assertTrue(user1.getFriends().contains(user2));

        }

        @Test(timeout = 1000)
        public void testBlockUserSuccess() {

            // Set the input
            User user1 = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("pete", "pete@purdue.edu", "purduepete", "purduemascot4", "08/02/1869");

            // Runs the program with the input values
            user1.blockFriend(user2);



            // Trims the output and verifies it is correct.
            assertTrue(user1.getBlocked().contains(user2));
        }


        @Test(timeout = 1000)
        public void testBlockFriendAlreadyBlocked() {
            User user1 = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("pete", "pete@purdue.edu", "purduepete", "purduemascot4", "08/02/1869");



            // block user2 once - should be success
            user1.blockFriend(user2);

            //get a counter for user1's blocked list to ensure it doesn't increase when blocking user2 again
            int b = user1.getBlocked().size();

            //try to block user2 again - should throw exception
            user1.blockFriend(user2);

            //make sure user1 has the same blocked list as before the repeated action occurred
            assertEquals(user1.getBlocked().size() == b);

            //make sure user2 is still in user1's friend list
            assertTrue(user1.getBlocked().contains(user2));

        }

        @Test(timeout = 1000)
        public void testRemoveFriendSuccess() {

            // Set the input
            User user1 = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("pete", "pete@purdue.edu", "purduepete", "purduemascot4", "08/02/1869");

            // Runs the program with the input values
            user1.removeFriend(user2);

            // Trims the output and verifies it is correct.
            assertTrue(!user1.getFriends().contains(user2));
        }

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
            assertEquals(user1.getFriends().size() == g);

            //make sure user2 is still in user1's friend list
            assertTrue(!user1.getFriends().contains(user2));

        }

        //TEST 3

        @Test(timeout = 1000)
        public void testGetFirstAlphabeticallyBothEmpty() {

            // names for both users
            String name1 = "";
            String name2 = "";

            //returns program output
            String result = User.getFirstAlphabetically(name1, name2);

            //compare expected/actual output
            assertEquals("Both names are equal alphabetically.", result);
        }

        @Test(timeout = 1000)
        public void testGetFirstAlphabeticallyName1Empty() {

            // names for both users
            String name1 = "";
            String name2 = "Pete";

            //returns program output
            String result = User.getFirstAlphabetically(name1, name2);

            //compare expected/actual output
            assertEquals(name1, result);
        }

        @Test(timeout = 1000)
        public void testGetFirstAlphabeticallyName2Empty() {

            // names for both users
            String name1 = "Ellie";
            String name2 = "";

            //returns program output
            String result = User.getFirstAlphabetically(name1, name2);

            //compare expected/actual output
            assertEquals(name2, result);
        }

        @Test(timeout = 1000)
        public void testGetFirstAlphabeticallyBothNotEmpty() {

            // names for both users
            String name1 = "Ellie";
            String name2 = "Pete";

            //returns program output
            String result = User.getFirstAlphabetically(name1, name2);

            //compare expected/actual output
            assertEquals(name1, result);
        }

        @Test(timeout = 1000)
        public void testGetFirstAlphabeticallyBothStartWithSameLetter() {

            // names for both users
            String name1 = "John";
            String name2 = "Jack";

            //returns program output
            String result = User.getFirstAlphabetically(name1, name2);

            //compare expected/actual output
            assertEquals(name2, result);
        }

        public void testSendMessage() {
            try {
                //initialize users
                User user1 = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
                User user2 = new User("pete", "pete@purdue.edu", "purduepete", "purduemascot4", "08/02/1869");

                //initialize message and send it
                String message = "test message";
                user1.sendMessage(user2, message);

                //create file and ensure it exists
                File messages = new File("testmessages.txt");
                assertTrue(messages.exists());

                //read the file and check that the message sent is the same as what is received
                FileReader fr = new FileReader(messages);
                BufferedReader bfr = new BufferedReader(fr);
                String messageOutput = bfr.readLine();
                bfr.close();

                assertEquals(message, messageOutput);

            }
            finally {
            
            }
        }


        // TEST 4
        


    }
}

