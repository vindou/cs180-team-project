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
 * This class tests the various methods in the Server class
 *
 *
 *
 * @author Gilbert Chang
 * @version April 15, 2024.
 */

import static org.junit.Assert.assertEquals;

/**
 * This class tests the various methods in the Server class
 *
 *
 *
 * @author Gilbert Chang
 * @version April 15, 2024.
 */

@RunWith(Enclosed.class)

/**
 * This class tests the various methods in the Server class
 *
 *
 *
 * @author Gilbert Chang
 * @version April 15, 2024.
 */

public class TestsServerClass {
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
     * This class tests the various methods in the Server class
     *
     *
     *
     * @author Gilbert Chang
     * @version April 15, 2024.
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
        public void testUserRegistration() {
            try {
                ServerClass server = new ServerClass(5327);
                server.start();
                assertTrue(server.registerUser(new User("gilbert",
                        "gc90student@gmail.com",
                        "changd0u",
                        "hi",
                        "09/17/2005")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Test(timeout = 5000)
        public void testUserDuplicateRegistration() {
            try {
                ServerClass server = new ServerClass(5328);
                server.start();
                assertTrue(server.registerUser(new User("gilbert",
                        "gc90student@gmail.com",
                        "changd0u",
                        "hi",
                        "09/17/2005")));
                assertFalse(server.registerUser(new User("gilbert",
                        "gc90student@gmail.com",
                        "changd0u",
                        "hi",
                        "09/17/2005")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Test(timeout = 5000)
        public void testAddingFriends() {
            try {
                ServerClass server = new ServerClass(5329);
                server.start();
                User gilbert1 = new User("gilbert",
                        "gc90student@gmail.com",
                        "changd0u",
                        "hi",
                        "09/17/2005");
                User gilbert2 = new User("gilbert2",
                        "gc90student@gmail.com",
                        "changd9u",
                        "hi",
                        "09/17/2005");
                assertTrue(server.registerUser(gilbert1));
                assertTrue(server.registerUser(gilbert2));
                assertTrue(gilbert1.addFriend(gilbert2));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Test(timeout = 5000)
        public void testBlockingUsers() {
            try {
                ServerClass server = new ServerClass(5330);
                server.start();
                User gilbert1 = new User("gilbert",
                        "gc90student@gmail.com",
                        "changd0u",
                        "hi",
                        "09/17/2005");
                User gilbert2 = new User("gilbert2",
                        "gc90student@gmail.com",
                        "changd9u",
                        "hi",
                        "09/17/2005");
                assertTrue(server.registerUser(gilbert1));
                assertTrue(server.registerUser(gilbert2));
                assertTrue(gilbert1.blockFriend(gilbert2));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Test(timeout = 5000)
        public void testCreatingConvos() {
            try {
                ServerClass server = new ServerClass(5331);
                server.start();
                User gilbert1 = new User("gilbert",
                        "gc90student@gmail.com",
                        "changd0u",
                        "hi",
                        "09/17/2005");
                User gilbert2 = new User("gilbert2",
                        "gc90student@gmail.com",
                        "changd9u",
                        "hi",
                        "09/17/2005");
                ArrayList<User> testArray = new ArrayList<>();
                testArray.add(gilbert1);
                testArray.add(gilbert2);
                assertTrue(server.registerUser(gilbert1));
                assertTrue(server.registerUser(gilbert2));
                assertTrue(server.createConversation(new Conversation("tests",
                        testArray,
                        server.getConvos())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Test(timeout = 5000)
        public void testUserQuery() {
            try {
                ServerClass server = new ServerClass(5332);
                server.start();
                User gilbert1 = new User("gilbert",
                        "gc90student@gmail.com",
                        "changd0u",
                        "hi",
                        "09/17/2005");
                User gilbert2 = new User("gilbert2",
                        "gc90student@gmail.com",
                        "changd9u",
                        "hi",
                        "09/17/2005");
                ArrayList<User> testArray = new ArrayList<>();
                testArray.add(gilbert1);
                testArray.add(gilbert2);
                assertTrue(server.registerUser(gilbert1));
                assertTrue(server.registerUser(gilbert2));
                assertTrue(server.searchForUsers("chang").size() == 2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}