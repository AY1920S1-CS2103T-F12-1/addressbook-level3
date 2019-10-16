package seedu.address.model.events;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import seedu.address.logic.composers.InstantComposer;
import seedu.address.logic.parser.DateTimeParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a date and time.
 * Internally stored as seconds from epoch, without any timezone information.
 */
public class DateTime implements Comparable<DateTime> {

    public static final String USER_DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm";

    private static final ZoneId TIMEZONE = ZoneId.systemDefault();

    private static final DateTimeParser USER_PARSER =
        new DateTimeParser(DateTimeFormatter.ofPattern(USER_DATE_TIME_PATTERN)
            .withZone(TIMEZONE));
    private static final InstantComposer USER_COMPOSER =
        new InstantComposer(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

    private static final DateTimeFormatter ICS_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")
        .withZone(ZoneId.of("UTC"));
    private static final DateTimeParser ICS_PARSER = new DateTimeParser(ICS_FORMATTER);
    private static final InstantComposer ICS_COMPOSER = new InstantComposer(ICS_FORMATTER);

    private final Instant instant;

    public DateTime(Instant instant) {
        this.instant = instant;
    }

    public static DateTime fromIcsString(String string) throws ParseException {
        return ICS_PARSER.parse(string);
    }

    public static DateTime fromUserInput(String string) throws ParseException {
        return USER_PARSER.parse(string);
    }

    /**
     * Creates a new instance of DateTime according to the clock.
     *
     * @return A new instance of DateTime according to the clock.
     */
    public static DateTime now() {
        return new DateTime(Instant.now());
    }

    /**
     * Computes the number of milliseconds between the calling instance of DateTime
     *     and the argument instance of Time. If the argument instance of DateTime
     *     occurs before the calling instance, the result returned will be negative.
     *
     * @param futureTime A DateTime that does not have (but is expected) to be in the future.
     * @return The number of milliseconds between the calling instance of DateTime
     *     and the argument instance of DateTime.
     */
    public long msecsTimeUntil(DateTime futureTime) {
        return futureTime.instant.toEpochMilli() - this.instant.toEpochMilli();
    }

    public Instant toInstant() {
        return this.instant;
    }

    public String toIcsString() {
        return ICS_COMPOSER.compose(this.instant);
    }

    @Override
    public String toString() {
        return USER_COMPOSER.compose(this.instant);
    }

    @Override
    public int compareTo(DateTime o) {
        return this.instant.compareTo(o.instant);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof DateTime) {
            DateTime d = (DateTime) object;
            return this.instant.equals(d.instant);
        }
        return false;
    }
}
