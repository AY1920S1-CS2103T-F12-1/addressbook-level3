package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Event}.
 */
public class DayCardEvent extends UiPart<Region> {

    private static final String FXML = "DayCardEvent.fxml";

    @FXML
    private Label eventDescription;

    public DayCardEvent(String description) {
        super(FXML);
        this.eventDescription.setText(description);
        eventDescription.setPadding(new Insets(0, 0, 0, 5));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventCard)) {
            return false;
        }

        // state check
        DayCardEvent card = (DayCardEvent) other;
        return eventDescription.equals(card.eventDescription);
    }
}
