package seedu.address.logic.commands.arguments;

/**
 * Represents a command argument of type String.
 */
public class StringArgument extends Argument<String> {

    StringArgument(ArgumentBuilder<String> builder) {
        super(builder);
    }

    public static StringArgumentBuilder newBuilder(String description) {
        return new StringArgumentBuilder(description);
    }

    @Override
    String parse(String userInput) {
        return userInput;
    }
}
