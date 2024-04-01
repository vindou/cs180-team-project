import org.junit.Test;
import org.junit.After;
import java.lang.reflect.Field;
import org.junit.Assert;
import org.junit.Before;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.Timeout;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * A framework to run public test cases.
 *
 * @author Sahil Shetty
 * @version April 1, 2024
 */
@RunWith(Enclosed.class)
public class UserDatabaseTest {
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
     * A set of public test cases.
     *
     * <p>Purdue University -- CS18000 -- Summer 2022</p>
     *
     * @author Purdue CS
     * @version June 13, 2022
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

        // Each of the correct outputs

        /* 
        @Test(timeout = 1000)
        public void testExpectedOne() {

            // Set the input
            String input = "0" + System.lineSeparator();

            // Pair the input with the expected result
            String expected = "";

            // Runs the program with the input values
            receiveInput(input);
            // UserDatabase.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals("Make sure you follow the flowchart and use the given strings for the result!",
                    expected.trim(), output.trim());
        }

        @Test(timeout = 1000)
        public void testExpectedTwo() {

            // Set the input
            String input = "1" + System.lineSeparator() +
                    "0.12" + System.lineSeparator() +
                    "1000.00" + System.lineSeparator() +
                    "320.00" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "1" + System.lineSeparator() +
                    "0" + System.lineSeparator();

            // Pair the input with the expected result
            String expected = "";

            // Runs the program with the input values
            receiveInput(input);
            // UserDatabase.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals("Make sure you follow the flowchart and use the given strings for the result!",
                    expected.trim(), output.trim());
        }
        */

        // testing the application of the writeDatabase method
        @Test(timeout = 5000)
        public void writeDatabaseTest() {

            // Set the input
            User user1 = new User("ellie", "ellie@purdue.edu", "ellieW", "purdue1231", "03/16/2004");
            User user2 = new User("jack", "jack@purdue.edu", "jackJ", "purdue1232", "03/17/2004");
            User user3 = new User("gilbert", "gilbert@purdue.edu", "gilbertC", "purdue1233", "03/18/2004");
            User user4 = new User("sahil", "sahil@purdue.edu", "SahilS", "purdue1234", "03/19/2004");

            ArrayList<User> userArray = new ArrayList<>();
            userArray.add(user1);
            userArray.add(user2);
            userArray.add(user3);
            userArray.add(user4);

            
            String filename = "userData.txt";
            UserDatabase userData = new UserDatabase(userArray, filename);
            userData.writeDatabase();
        }

        // testing the application of the readDatabase method
        @Test(timeout = 5000)
        public void readDatabaseTest() {
            User user1 = new User("ellie", "ellie@purdue.edu", "ellieW", "purdue1231", "03/16/2004");
            User user2 = new User("jack", "jack@purdue.edu", "jackJ", "purdue1232", "03/17/2004");
            User user3 = new User("gilbert", "gilbert@purdue.edu", "gilbertC", "purdue1233", "03/18/2004");
            User user4 = new User("sahil", "sahil@purdue.edu", "SahilS", "purdue1234", "03/19/2004");

            ArrayList<User> test = new ArrayList<User>();
            test.add(user1);
            test.add(user2);
            test.add(user3);
            test.add(user4);
            
            String filename = "userData.txt";
            UserDatabase userData = new UserDatabase(test, filename);

            ArrayList<Object> userArray = userData.readDatabase();
            for (Object user: userArray) {
                System.out.println(user);
            }
        }

        // testing the application of the retrieveUserData method
        @Test(timeout = 5000)
        public void retrieveUserDataTest() {
            // Set the input
            User user1 = new User("ellie", "ellie@purdue.edu", "ellieW", "purdue1231", "03/16/2004");
            User user2 = new User("jack", "jack@purdue.edu", "jackJ", "purdue1232", "03/17/2004");
            User user3 = new User("gilbert", "gilbert@purdue.edu", "gilbertC", "purdue1233", "03/18/2004");
            User user4 = new User("sahil", "sahil@purdue.edu", "SahilS", "purdue1234", "03/19/2004");

            ArrayList<User> userArray = new ArrayList<>();
            userArray.add(user1);
            userArray.add(user2);
            userArray.add(user3);
            userArray.add(user4);

            String filename = "userData.txt";
            UserDatabase userData = new UserDatabase(userArray, filename);

            try {
                User user = userData.retrieveUserData("jackJ");
                assertTrue(user.equals(user2));
            } catch (ActionNotAllowedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
