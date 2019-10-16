package seedu.address.logic.commands.arguments;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.events.DateTime;

/**
 * Represents a command argument of type DateTime.
 */
public class DateTimeArgument extends Argument<DateTime> {

    DateTimeArgument(ArgumentBuilder<DateTime> builder) {
        super(builder);
    }

    public static DateTimeArgumentBuilder newBuilder(String description) {
        return new DateTimeArgumentBuilder(description);
    }

    @Override
    DateTime parse(String userInput) throws ParseException {
        return DateTime.fromUserInput(userInput);
    }
}
