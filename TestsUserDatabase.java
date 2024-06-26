import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.experimental.runners.Enclosed;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * A framework to run public test cases.
 *
 * @author Sahil Shetty, Jack Juncker
 * @version April 1, 2024
 */

@RunWith(Enclosed.class)
public class TestsUserDatabase {
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

        // testing the application of the writeDatabase method
        @Test(timeout = 5000)
        public void writeDatabaseTestOne() {
            // Set the input
            User user1 = new User("ellie", "ellie@purdue.edu", "ellieW", "purdue1231", "03/16/2004");
            User user2 = new User("jack", "jack@purdue.edu", "jackJ", "purdue1232", "03/17/2004");
            User user3 = new User("gilbert", "gilbert@purdue.edu", "gilbertC", "purdue1233", "03/18/2004");
            User user4 = new User("sahil", "sahil@purdue.edu", "SahilS", "purdue1234", "03/19/2004");

            ArrayList<Object> userArray = new ArrayList<>();
            userArray.add(user1);
            userArray.add(user2);
            userArray.add(user3);
            userArray.add(user4);

            String filename = "userData.txt";
            UserDatabase userDatabase1 = new UserDatabase(userArray, filename);
            assertTrue(userDatabase1.writeDatabase());
        }
        // testing the application of the readDatabase method
        @Test(timeout = 5000)
        public void readDatabaseTest() {
            User user1 = new User("ellie", "ellie@purdue.edu", "ellieW", "purdue1231", "03/16/2004");
            User user2 = new User("jack", "jack@purdue.edu", "jackJ", "purdue1232", "03/17/2004");
            User user3 = new User("gilbert", "gilbert@purdue.edu", "gilbertC", "purdue1233", "03/18/2004");
            User user4 = new User("sahil", "sahil@purdue.edu", "SahilS", "purdue1234", "03/19/2004");

            ArrayList<Object> test = new ArrayList<>();
            test.add(user1);
            test.add(user2);
            test.add(user3);
            test.add(user4);

            String filename = "userData.txt";
            UserDatabase userData = new UserDatabase(test, filename);
            userData.writeDatabase();

            ArrayList<Object> userArray = userData.readDatabase();

            for (int i = 0; i < userArray.size(); i++) {
                assertTrue(test.get(i).equals(userArray.get(i)));
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

            ArrayList<Object> userArray = new ArrayList<>();
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