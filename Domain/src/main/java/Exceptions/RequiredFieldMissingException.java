package Exceptions;

/**
 * @author Julian
 */
public class RequiredFieldMissingException extends RuntimeException {
    public RequiredFieldMissingException(String message) {
        super("Your Input is missing a required field: " + message);
    }
}
