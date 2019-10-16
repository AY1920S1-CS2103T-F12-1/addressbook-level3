package seedu.address.logic.commands.arguments;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a variable argument of Strings.
 */
public class StringVariableArguments extends VariableArguments<String> {

    StringVariableArguments(VariableArgumentsBuilder<String> builder) {
        super(builder);
    }

    public static StringVariableArgumentsBuilder newBuilder(String description) {
        return new StringVariableArgumentsBuilder(description);
    }

    @Override
    String parse(String userInput) throws ParseException {
        return userInput;
    }
}
