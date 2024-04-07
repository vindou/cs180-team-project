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
 * This class tests the various methods in the User class
 *
 *
 *
 * @author Ellie Williams, Jack Juncker
 * @version Mar 30th, 2024.
 */

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class TestsMessageAndConversationClass {

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

        // MESSAGE SUPERCLASS TEST CASES
        @Test(timeout = 2000)
        public void testMessageSuperClassConstructor() {
            User testUser = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            Message testMessage = new Message(testUser);

            User comparedUser = testMessage.getSender();
            int comparedIndex = testMessage.getIndex();

            assertTrue(comparedUser.equals(testUser));
            assertEquals(0, comparedIndex);
        }

        @Test(timeout = 2000)
        public void testMessageIndexSetter() {
            User testUser = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            Message testMessage = new Message(testUser);

            int testIndex = 4;
            try {
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

            assertTrue(cd.getConversationArray().isEmpty());
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
        public void testUserAdditionMethod() {
            User user1 = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("pete", "pete@purdue.edu", "purduepete", "purduemascot4", "08/02/1869");

            ArrayList<User> testArray = new ArrayList<User>();
            testArray.add(user1);

            ConversationDatabase cd = new ConversationDatabase("output.txt");
            Conversation testConversation = new Conversation("cs 180 group", testArray, cd);

            try {
                testConversation.addUser(user2);
            } catch (ActionNotAllowedException ignored) {}

            assertEquals(testConversation.getUsers().size(), 2);
        }

        @Test(timeout = 2000)
        public void testUserRemovalMethod() {
            User user1 = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("pete", "pete@purdue.edu", "purduepete", "purduemascot4", "08/02/1869");

            ArrayList<User> testArray = new ArrayList<User>();
            testArray.add(user1);
            testArray.add(user2);

            ConversationDatabase cd = new ConversationDatabase("output.txt");
            Conversation testConversation = new Conversation("cs 180 group", testArray, cd);

            try {
                testConversation.removeUser(user2);
            } catch (ActionNotAllowedException ignored) {}

            assertEquals(testConversation.getUsers().size(), 1);
        }

        @Test(timeout = 2000)
        public void testMessageAddition() {
            User user1 = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("pete", "pete@purdue.edu", "purduepete", "purduemascot4", "08/02/1869");

            ArrayList<User> testArray = new ArrayList<User>();
            testArray.add(user1);
            testArray.add(user2);

            ConversationDatabase cd = new ConversationDatabase("output.txt");
            Conversation testConversation = new Conversation("cs 180 group", testArray, cd);

            TextMessage tM = new TextMessage(user1, "hi");

            try {
                testConversation.addMessage(tM);
            } catch (ActionNotAllowedException e) {
                e.printStackTrace();
            }

            assertEquals(1, testConversation.getMessages().size());
        }

        @Test(timeout = 3000)
        public void testMessageRemoval() {
            User user1 = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");
            User user2 = new User("pete", "pete@purdue.edu", "purduepete", "purduemascot4", "08/02/1869");

            ArrayList<User> testArray = new ArrayList<User>();
            testArray.add(user1);
            testArray.add(user2);

            ConversationDatabase cd = new ConversationDatabase("output.txt");
            Conversation testConversation = new Conversation("cs 180 group", testArray, cd);

            TextMessage tM = new TextMessage(user1, "hi");
            TextMessage tM2 = new TextMessage(user1, "ho");

            try {
                testConversation.addMessage(tM);
                testConversation.deleteMessage(tM);
            } catch (Exception e) {
                e.printStackTrace();
            }

            assertTrue(testConversation.getMessages().isEmpty());
        }

        @Test(timeout = 2000)
        public void testConversationToString() {
            User user1 = new User("ellie", "will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");

            ArrayList<User> testArray = new ArrayList<User>();

            ConversationDatabase cd = new ConversationDatabase("output.txt");
            Conversation testConversation = new Conversation("cs 180 group", testArray, cd);

            assertEquals(testConversation.fileNameString(), "Conversation #" + testConversation.getID());
        }
    }
}
