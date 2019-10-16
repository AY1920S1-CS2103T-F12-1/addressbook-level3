package seedu.address.model.listeners;

import java.util.List;

import seedu.address.model.events.EventSource;

/**
 * Represents a listener that will be notified whenever the EventList in ModelManager changes.
 */
public interface EventListListener {

    void onEventListChange(List<EventSource> events);
}
