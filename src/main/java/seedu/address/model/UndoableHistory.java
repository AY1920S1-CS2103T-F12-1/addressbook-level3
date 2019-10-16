package seedu.address.model;

import java.util.ArrayList;

import seedu.address.model.events.EventList;

/**
 * UndoableHistory contains all EventList states
 * at different points of time in its eventListStateList
 * as well as a currentStateIndex that stores the index of the
 * current EventList state in the list.
 * It also contains a mainEventList that represents the current EventList
 * state. Duplicates of this mainEventList are stored in the eventListStateList.
 * Whenever an undo or redo command is executed, mainEventList restores itself to a
 * past/future state by copying the data in its duplicate over to itself.
 */
public class UndoableHistory {

    /** The eventList that the GUI is in sync with.
     * This mainEventList is updated every time an
     * undo or redo occurs. It is important that ModelManager
     * works only with the mainEventList object and not
     * duplicates due to its synchronisation with the GUI.
     */
    private EventList mainEventList;

    /**
     * Deep-copies of mainEventList are stored to this list
     * every time a state-changing command is executed.
     * This allows mainEventList to retrieve its data
     * from any of these past or future states when an
     * undo or redo command is called.
     */
    private ArrayList<EventList> eventListStateList;
    private int currentStateIndex;

    UndoableHistory(EventList eventList) {
        mainEventList = eventList;
        eventListStateList = new ArrayList<>();
        // Store a deep-copy of the mainEventList to the list
        eventListStateList.add(new EventList(mainEventList));
        currentStateIndex = 0;
    }

    /**
     * Returns the current state of the EventList.
     *
     * @return EventList mainEventList.
     */
    EventList getCurrentState() {
        return mainEventList;
    }

    /**
     * Creates a deep-copy of the current event list state and saves that copy to the UndoableHistory.
     */
    void commit(EventList eventList) {
        // Store a deep-copy of the mainEventList to the list
        EventList deepCopy = new EventList(eventList);
        assert currentStateIndex >= eventListStateList.size() - 1
                : "Pointer always points to end of list during commit; All future states must have been discarded.";
        eventListStateList.add(deepCopy);
        currentStateIndex++;
    }

    /**
     * Restores the previous event list state from UndoableHistory.
     */
    void undo() {
        currentStateIndex--;
        // Retrieve data from duplicate of its past state
        mainEventList.resetData(eventListStateList.get(currentStateIndex));
    }

    /**
     * Restores the previously undone event list state from UndoableHistory.
     */
    void redo() {
        currentStateIndex++;
    }

    /**
     * Returns true if there are previous event list states to restore, and false otherwise.
     *
     * @return boolean
     */
    boolean canUndo() {
        return currentStateIndex > 0;
    }

    /**
     * Clears all future event list states in eventListStateList beyond the index given by currentStateIndex
     */
    void clearFutureHistory() {
        eventListStateList =
                new ArrayList<>(eventListStateList.subList(0, currentStateIndex + 1));
    }

    @Override
    public String toString() {
        return eventListStateList.size() + " states in history";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        } else {
            if (!(other instanceof UndoableHistory)) {
                return false;
            }
            UndoableHistory otherHistory = ((UndoableHistory) other);
            if (currentStateIndex != otherHistory.currentStateIndex
                    || eventListStateList.size() != otherHistory.eventListStateList.size()) {
                return false;
            }
            for (int i = 0; i < eventListStateList.size(); i++) {
                if (!eventListStateList.get(i).equals(otherHistory.eventListStateList.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }

}
