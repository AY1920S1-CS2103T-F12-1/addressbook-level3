package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.events.EventSource;

/**
 * An Ui that stores the logged feedback from the program to the user.
 */
public class ListPanel extends UiPart<Region> {

    private static final String FXML = "ListPanel.fxml";

    @FXML
    private VBox list;

    /**
     * Constructor for ListPanel. Stores the event list, and task list[in v2.0].
     */
    public ListPanel(ObservableList<EventSource> eventList, UiParser uiParser) {
        super(FXML);
        EventListPanel eventListPanel = new EventListPanel(uiParser);
        list.getChildren().add(eventListPanel.getRoot());
    }
}
