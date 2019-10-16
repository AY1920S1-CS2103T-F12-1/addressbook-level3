package seedu.address.logic.commands.exceptions;

/**
 * Represents an exception thrown when creating a command argument.
 */
public class ArgumentException extends Exception {
    public ArgumentException(String message) {
        super(message);
    }
}
