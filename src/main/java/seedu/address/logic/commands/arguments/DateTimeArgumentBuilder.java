package seedu.address.logic.commands.arguments;

import seedu.address.model.events.DateTime;

/**
 * Represents an ArgumentBuilder responsible for building {@link DateTimeArgument}
 */
public class DateTimeArgumentBuilder extends ArgumentBuilder<DateTime> {

    DateTimeArgumentBuilder(String description) {
        super(description);
    }

    @Override
    Argument<DateTime> argumentBuild() {
        return new DateTimeArgument(this);
    }
}
