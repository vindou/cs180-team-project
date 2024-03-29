import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

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
        public void testExpectedOptionOne() {

            // Set the input
            String input = "will2613@purdue.edu" + System.lineSeparator() + "elliewilliams" + System.lineSeparator() + "purdue123" +
                    System.lineSeparator() + "03/16/2004" + System.lineSeparator();

            // Pair the input with the expected result
            User expected = new User("will2613@purdue.edu", "elliewilliams", "purdue123", "03/16/2004");

            // Runs the program with the input values
            receiveInput(input);
            User.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            assertEquals("Make sure your output matches the expected case for the option 1",
                    expected.trim(), output.trim());
        }
