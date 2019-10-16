package seedu.address.ics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import seedu.address.model.events.DateTime;
import seedu.address.model.events.EventSource;

/***
 * Parses an ICS file to allow importing into Horo.
 */
public class IcsParser {

    private static final String FILE_DOES_NOT_EXIST = "Sorry, the file path you've specified is invalid!";
    private static final String FILE_CANNOT_BE_READ = "Sorry, the file specified cannot be read!";
    private static final String FILE_CANNOT_BE_FOUND = "Sorry, the file specified cannot be found!";
    private static final String INVALID_FILE_EXTENSION = "The file specified is not an ICS file!";
    private static final String FILE_IS_CORRUPTED = "The ICS file is corrupted!";

    /**
     * This enum represents the different types of objects the IcsParser could be parsing at any given point in time.
     */
    private enum ParseState { None, Todo, Event }

    private File icsFile;

    private IcsParser(String path) throws IcsException {
        this.icsFile = getIcsFile(path);
    }

    private File getIcsFile(String path) throws IcsException {
        File file = new File(path);
        if (!file.exists()) {
            throw new IcsException(FILE_DOES_NOT_EXIST);
        } else if (!file.canRead()) {
            throw new IcsException(FILE_CANNOT_BE_READ);
        } else if (!file.toString().endsWith(".ics")) {
            throw new IcsException(INVALID_FILE_EXTENSION);
        }
        return file;
    }

    public static IcsParser parse(String path) throws IcsException {
        return new IcsParser(path);
    }

    public ArrayList<EventSource> getEvents() throws IcsException {
        ArrayList<EventSource> events = new ArrayList<>();
        try {
            ParseState currentlyParsing = ParseState.None;
            BufferedReader br = new BufferedReader(new FileReader(icsFile));
            StringBuilder stringBuilder = new StringBuilder("");
            while (br.ready()) {
                String line = br.readLine();
                if (currentlyParsing == ParseState.Event) {
                    if (line.startsWith("END:VEVENT")) {
                        currentlyParsing = ParseState.None;
                        EventSource eventSource = createEvent(stringBuilder.toString());
                        events.add(eventSource);
                    } else {
                        stringBuilder.append(line).append("\n");
                    }
                } else {
                    if (line.equals("BEGIN:VEVENT")) {
                        if (currentlyParsing != ParseState.None) {
                            throw new IcsException(FILE_IS_CORRUPTED);
                        } else {
                            currentlyParsing = ParseState.Event;
                            stringBuilder = new StringBuilder("");
                        }
                    }
                }
            }
            return events;
        } catch (FileNotFoundException e) {
            throw new IcsException(FILE_CANNOT_BE_FOUND);
        } catch (IOException e) {
            throw new IcsException(FILE_CANNOT_BE_READ);
        }
    }

    /**
     * Converts the timestamp from the format given in the ICS file to a DateTime object.
     * @param timestamp A timestamp in the default ICS file specification format.
     * @return A DateTime object representing the timestamp.
     * @throws IcsException Thrown when the timestamp provided is invalid.
     */
    private DateTime parseTimeStamp(String timestamp) throws IcsException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            return new DateTime(sdf.parse(timestamp).toInstant());
        } catch (ParseException e) {
            throw new IcsException("The timestamp provided is invalid!");
        }
    }

    /**
     * Creates an EventSource object from the data provided in the ICS File.
     * Currently it will only parse the Start time and Description of the ICS VEvent.
     * @param segment A String that represents the Event object in the ICS File.
     * @return an EventSource object representing the data provided.
     * @throws IcsException Exception thrown when there was an issue while making the EventSource object.
     */
    public EventSource createEvent(String segment) throws IcsException {
        String[] lines = segment.split("\\r?\\n");
        String description = "";
        DateTime dateTime = null;
        for (String line : lines) {
            if (line.startsWith("DESCRIPTION:")) {
                description = line.replaceFirst("DESCRIPTION:", "");
            } else if (line.startsWith("DTSTART:")) {
                String timestamp = line.replaceFirst("DTSTART:", "");
                dateTime = parseTimeStamp(timestamp);
            } else if (line.equals("END:VCALENDAR")) {
                if (description.equals("") || dateTime == null) {
                    throw new IcsException(FILE_IS_CORRUPTED);
                }
            }
        }
        if (description.equals("")) {
            throw new IcsException("The description of an event cannot be empty!");
        }
        return EventSource.newBuilder(description, dateTime)
            .build();
    }
}
