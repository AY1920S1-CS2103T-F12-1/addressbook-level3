package seedu.address.ui;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import seedu.address.model.events.EventSource;
import seedu.address.model.listeners.EventListListener;

/**
 * An Ui that stores the logged feedback from the program to the user.
 */
public class CalendarPanel extends UiPart<Region> implements EventListListener {

    private static final String FXML = "CalendarPanel.fxml";

    private UiParser uiParser;
    private YearMonth yearMonth;
    private LocalDate calendarDate;
    private ArrayList<DayCard> currentMonthDayCards;

    @FXML
    private GridPane calendar;

    @FXML
    private Label month;

    /**
     * Constructor for ListPanel. Stores the event list, and task list[in v2.0].
     */
    public CalendarPanel(UiParser uiParser) {
        super(FXML);
        this.yearMonth = YearMonth.now();
        this.calendarDate = LocalDate.now();
        this.currentMonthDayCards = new ArrayList<>();
        this.uiParser = uiParser;
        setCurrentMonth();
        resetCalendar();
    }

    /**
     * Sets the label to be the current Month.
     */
    private void setCurrentMonth() {
        String currentMonth = yearMonth.getMonth().toString().toLowerCase();
        currentMonth = currentMonth.substring(0, 1).toUpperCase() + currentMonth.substring(1);
        month.setText(currentMonth);
    }

    /**
     * Fills the index of the calendar.
     */
    private void resetCalendar() {
        this.currentMonthDayCards.clear();

        int index = 1;
        int startingDay = calendarDate.getDayOfWeek().getValue();
        int totalDays = yearMonth.lengthOfMonth();
        for (int weeks = 0; weeks < 6; weeks++) {
            for (int days = 0; days < 7; days++) {
                if (weeks == 0 && days == 0) {
                    days = startingDay - 3;
                    continue;
                }
                if (index > totalDays) {
                    break;
                }
                DayCard todayCard = new DayCard(index, yearMonth.getMonthValue(), yearMonth.getYear());
                calendar.add(todayCard.getRoot(), days, weeks);
                currentMonthDayCards.add(todayCard);
                index++;
            }
        }
    }


    @Override
    public void onEventListChange(List<EventSource> events) {
        resetCalendar();
        for (EventSource event : events) {
            DayCard dayCard = currentMonthDayCards.get(
                uiParser.getDay(event.getStartDateTime().toInstant()) - 1);
            if (dayCard.sameDateAsEvent(event, uiParser)) {
                dayCard.addDayCardEvent(event);
            }
        }
    }
}
