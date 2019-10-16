package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.model.events.DateTime.USER_DATE_TIME_PATTERN;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

class DateTimeParserTest {

    private static final DateTimeParser PARSER =
        new DateTimeParser(DateTimeFormatter.ofPattern(USER_DATE_TIME_PATTERN)
        .withZone(ZoneId.systemDefault()));

    @Test
    void parse_invalidDateTime_failure() {
        String[] tests = { "", " ", "a", "101010 1010", "9/9/9 9:00", "13/13/1313 13:13" };
        for (String test : tests) {
            assertThrows(ParseException.class, () -> PARSER.parse(test));
        }
    }

    @Test
    void parse_validDateTime_success() {
        String[] tests = { "12/12/1212 12:12", "01/01/0001 00:00", "31/12/9999 23:59" };
        for (String test : tests) {
            assertDoesNotThrow(() -> PARSER.parse(test));
        }
    }
}
