package seedu.address.ui.panel.calendar;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays the day on the calendar.
 */
public class CalendarGridDay extends UiPart<Region> {

    private static final float BASE_SATURATION = 0.25f;
    private static final float MAX_SATURATION = 0.75f;
    private static final int MAX_EVENT = 10;
    private static final String FXML = "CalendarGridDay.fxml";
    private Integer day;
    private Integer month;
    private Integer year;
    private Integer totalEvents;

    @FXML
    private Circle calendarDayCircle;

    @FXML
    private Label calendarDay;

    public CalendarGridDay(Integer day, Integer month, Integer year, Integer totalEvents) {
        super(FXML);
        this.day = day;
        this.month = month;
        this.year = year;
        this.totalEvents = totalEvents;
        calendarDay.setText(day.toString());
        colorChange();
    }

    /**
     * Increases the total number of Events and changes the color accordingly.
     */
    public void addAnEvent() {
        this.totalEvents++;
        colorChange();
    }

    /**
     * Return the day, month and year.
     *
     * @return The day, month and year.
     */
    public Integer[] getDayMonthYear() {
        Integer[] dayMonthYear = {day, month, year};
        return dayMonthYear;
    }

    /**
     * Reduces the opacity as the given Calendar Screen is of a different month.
     */
    public void reduceOpacity() {
        calendarDay.setStyle("-fx-opacity: " + 0.25);
    }

    /**
     * Adjusts the color of the circle to indicate how many events are there on that day.
     */
    private void colorChange() {
        ColorAdjust colorAdjust = new ColorAdjust();
        if (this.totalEvents == 0) {
            calendarDayCircle.setStyle("-fx-opacity: " + 0);
            calendarDayCircle.setEffect(colorAdjust);
        } else {
            calendarDayCircle.setStyle("-fx-opacity: " + 0.5);
            colorAdjust.setSaturation(getSaturationValue(this.totalEvents));
            calendarDayCircle.setEffect(colorAdjust);
        }
    }

    /**
     * Returns a float value of a saturation by the number of given events against a threshold value.
     *
     * @param events The total number of events of the current day.
     * @return Returns a float value of a saturation by the number of given events against a threshold value.
     */
    private float getSaturationValue(float events) {
        return (events / MAX_EVENT) * (MAX_SATURATION - BASE_SATURATION) + BASE_SATURATION;
    }


}
