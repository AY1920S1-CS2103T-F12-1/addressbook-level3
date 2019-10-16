package seedu.address.logic.commands.arguments;

import seedu.address.logic.parser.IndexParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a variable argument of Indexes.
 */
public class IndexVariableArguments extends VariableArguments<Integer> {

    IndexVariableArguments(VariableArgumentsBuilder<Integer> builder) {
        super(builder);
    }

    public static IndexVariableArgumentsBuilder newBuilder(String description) {
        return new IndexVariableArgumentsBuilder(description);
    }

    @Override
    Integer parse(String userInput) throws ParseException {
        return new IndexParser().parse(userInput);
    }
}
