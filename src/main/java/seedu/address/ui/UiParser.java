package seedu.address.ui;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * A component to convert/parse the terms in post logic into readable, UI format for the users.
 */
public class UiParser {

    /**
     * Returns an array of Integer, size 3, containing {day, month, year} of the event.
     * @param date The time of a given event source
     * @return an array of Integer, size 3, containing {day, month, year} of the event
     */
    public Integer[] parseDateToNumbers(Instant date) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                .withZone(ZoneId.systemDefault());
        String eventDate = dateFormat.format(date);
        String[] dayMonthYear = eventDate.split("/");
        Integer[] parsedDate = {Integer.valueOf(dayMonthYear[0]),
                Integer.valueOf(dayMonthYear[1]),
                Integer.valueOf(dayMonthYear[2])};
        return parsedDate;
    }

    /**
     * Returns an array of String indicating the date and time for the users to read.
     * @param date The given date of the event.
     * @return an array of String indicating the date and time for the users to read.
     */
    public String parseDateToString(Instant date) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                .withZone(ZoneId.systemDefault());
        return dateFormat.format(date);
    }

    public Integer getDay(Instant date) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd")
                .withZone(ZoneId.systemDefault());
        String eventDate = dateFormat.format(date);
        return Integer.valueOf(eventDate);
    }
}
