package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_TIME;
import static seedu.address.model.events.DateTime.USER_DATE_TIME_PATTERN;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.events.DateTime;

/**
 * Represents a {@link Parser} that can parse user input into a {@link DateTime} object.
 */
public class DateTimeParser implements Parser<DateTime> {

    private final DateTimeFormatter parser;

    public DateTimeParser(DateTimeFormatter parser) {
        this.parser = parser;
    }

    @Override
    public DateTime parse(String userInput) throws ParseException {
        try {
            Instant instant = Instant.from(this.parser.parse(userInput));
            return new DateTime(instant);
        } catch (DateTimeParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_DATE_TIME, USER_DATE_TIME_PATTERN));
        }
    }
}
