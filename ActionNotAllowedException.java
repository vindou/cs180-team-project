/**
* ActionNotAllowedException
*
* An exception that is thrown when a forbidden action occurs.
*
* @author Ellie Williams
*
* @version March 29, 2024
*
 */

public class ActionNotAllowedException extends Exception {
    public ActionNotAllowedException(String message) {
        super(message);
    }
}
