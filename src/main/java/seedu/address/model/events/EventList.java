package seedu.address.model.events;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.events.exceptions.EventNotFoundException;

/**
 * Represents a list of Events.
 * Does not allow any null values to be in the list.
 */
public class EventList implements ReadOnlyEventList {

    private final ObservableList<EventSource> list;

    public EventList() {
        this.list = FXCollections.observableArrayList();
    }

    public EventList(ReadOnlyEventList eventList) {
        this();
        this.resetData(eventList);
    }

    /**
     * Returns a deep-copy of an EventList.
     *
     * @param oldEventList the EventList to deep-copy.
     */
    public EventList(EventList oldEventList) {
        this();
        for (EventSource eventSource : oldEventList.list) {
            // Deep-copy an EventSource by calling its copy constructor
            this.list.add(new EventSource(eventSource));
        }
    }

    /**
     * Adds an event to this event list.
     * @param event the event to add
     */
    public void add(EventSource event) {
        requireNonNull(event);
        this.list.add(event);
    }

    /**
     * Check if an event is contained in this event list.
     * @param event the event to check
     * @return whether or not the event is contained in this event list
     */
    public boolean contains(EventSource event) {
        requireNonNull(event);
        return list.contains(event);
    }

    /**
     * Replace an event in this event list with another event.
     * @param event the event to replace
     * @param replacement the replacement event
     */
    public void replace(EventSource event, EventSource replacement) {
        requireAllNonNull(event, replacement);

        int index = list.indexOf(event);
        if (index == -1) {
            throw new EventNotFoundException();
        }

        list.set(index, replacement);
    }

    /**
     * Clears this event list and re-adds events from a given {@link ReadOnlyEventList}.
     * @param eventList the list of events to re-add
     */
    public void resetData(ReadOnlyEventList eventList) {
        requireNonNull(eventList);
        this.list.clear();
        this.list.addAll(eventList.getReadOnlyList());
    }

    /**
     * Removes an event from this event list.
     * @param event the event to remove
     */
    public void remove(EventSource event) {
        requireNonNull(event);
        if (!list.remove(event)) {
            throw new EventNotFoundException();
        }
    }

    @Override
    public ObservableList<EventSource> getReadOnlyList() {
        return this.list;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventList // instanceof handles nulls
                && list.equals(((EventList) other).list));
    }
}
