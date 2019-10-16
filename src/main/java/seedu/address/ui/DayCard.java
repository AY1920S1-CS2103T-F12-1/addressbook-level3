package seedu.address.ui;

import java.time.Instant;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import javafx.scene.layout.VBox;
import seedu.address.model.events.EventSource;

/**
 * An Ui that represents a box in the calendar itself, containing up to 5 events and the date.
 */
public class DayCard extends UiPart<Region> {

    private static final String FXML = "DayCard.fxml";

    private int day;
    private int month;
    private int year;
    private int numDayCardEvent;

    private ArrayList<EventSource> eventList = new ArrayList<>();
    private UiParser uiParser;

    @FXML
    private VBox todayEvents;

    @FXML
    private Label index;

    /**
     * Constructor for ListPanel. Stores the event list, and task list[in v2.0].
     */
    public DayCard(int day, int month, int year) {
        super(FXML);
        this.day = day;
        this.month = month;
        this.year = year;
        this.numDayCardEvent = 0;

        String currentIndex = day + ". ";
        this.index.setText(currentIndex);
    }

    /**
     * Returns a boolean which checks if the given event is the same date as the current iteration of DayCard.
     * @param eventSource The given event to check against.
     * @param uiParser Parses the Instant date into readable langauge.
     * @return a boolean comparing the dates.
     */
    public boolean sameDateAsEvent(EventSource eventSource, UiParser uiParser) {
        try {
            Instant date = eventSource.getStartDateTime().toInstant();
            Integer[] dayMonthYear = uiParser.parseDateToNumbers(date);
            boolean sameDay = dayMonthYear[0].equals(this.day);
            boolean sameMonth = dayMonthYear[1].equals(this.month);
            boolean sameYear = dayMonthYear[2].equals(this.year);
            return sameDay && sameMonth && sameYear;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Adds a DayCardEvent to the DayCard.
     * @param event The given event required for the description.
     */
    public void addDayCardEvent(EventSource event) {
        // We still want to take note of the number of events on that particular day.
        this.numDayCardEvent++;
        if (this.numDayCardEvent <= 5) {
            DayCardEvent addedEventLabel = new DayCardEvent(event.getDescription());
            todayEvents.getChildren().add(addedEventLabel.getRoot());
        }

    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DayCard)) {
            return false;
        }

        // state check
        DayCard card = (DayCard) other;
        return eventList.equals(card.eventList);
    }
}
