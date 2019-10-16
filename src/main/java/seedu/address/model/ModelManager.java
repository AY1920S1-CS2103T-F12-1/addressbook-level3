package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.events.EventList;
import seedu.address.model.events.EventSource;
import seedu.address.model.events.ReadOnlyEventList;
import seedu.address.model.listeners.EventListListener;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UndoableHistory undoableHistory;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

    private final List<EventListListener> eventListListeners;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyEventList readOnlyEventList,
            ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        EventList eventList = new EventList(readOnlyEventList);

        this.addressBook = new AddressBook(addressBook);
        this.undoableHistory = new UndoableHistory(eventList);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());

        this.eventListListeners = new ArrayList<>();
    }

    public ModelManager() {
        this(new AddressBook(), new EventList(), new UserPrefs());
    }

    public void addEventListListener(EventListListener listener) {
        this.eventListListeners.add(listener);
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== EventsBook ================================================================================

    @Override
    public void addEvent(EventSource event) {
        EventList eventList = undoableHistory.getCurrentState();

        eventList.add(event);

        // Save the modified EventList state to the UndoableHistory
        commitToHistory(eventList);

        eventListListeners.forEach(l -> l.onEventListChange(eventList.getReadOnlyList()));
    }

    @Override
    public void deleteEvent(EventSource target) {
        EventList eventList = undoableHistory.getCurrentState();

        eventList.remove(target);

        // Save the modified EventList state to the UndoableHistory
        commitToHistory(eventList);

        eventListListeners.forEach(l -> l.onEventListChange(eventList.getReadOnlyList()));
    }

    @Override
    public ReadOnlyEventList getEventList() {
        return undoableHistory.getCurrentState();
    }

    @Override
    public boolean hasEvent(EventSource event) {
        return undoableHistory.getCurrentState().contains(event);
    }

    @Override
    public void setEvent(EventSource target, EventSource editedEvent) {
        EventList eventList = undoableHistory.getCurrentState();

        eventList.replace(target, editedEvent);

        // Save the modified EventList state to the UndoableHistory
        commitToHistory(eventList);

        eventListListeners.forEach(l -> l.onEventListChange(eventList.getReadOnlyList()));
    }

    @Override
    public void setEventList(ReadOnlyEventList readOnlyEventList) {
        EventList eventList = undoableHistory.getCurrentState();

        eventList.resetData(readOnlyEventList);

        // Save the modified EventList state to the UndoableHistory
        commitToHistory(eventList);

        eventListListeners.forEach(l -> l.onEventListChange(eventList.getReadOnlyList()));
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public ObservableList<EventSource> getFilteredEventList() {
        return undoableHistory.getCurrentState().getReadOnlyList();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
            && userPrefs.equals(other.userPrefs)
            && filteredPersons.equals(other.filteredPersons);
    }

    //=========== UndoableHistory ================================================================================

    /**
     * Creates a deep-copy of the current event list state and saves that copy to the UndoableHistory.
     */
    public void commitToHistory(EventList eventList) {
        undoableHistory.commit(eventList);
    }

    /**
     * Restores the previous event list state from UndoableHistory.
     */
    @Override
    public void undoFromHistory() {
        undoableHistory.undo();
    }

    /**
     * Restores the previously undone event list state from UndoableHistory.
     */
    @Override
    public void redoFromHistory() {
        undoableHistory.redo();
    }

    /**
     * Returns true if there are previous event list states to restore, and false otherwise.
     *
     * @return boolean
     */
    @Override
    public boolean canUndoHistory() {
        return undoableHistory.canUndo();
    }

    /**
     * Clears all future event list states in UndoableHistory beyond the current state.
     */
    @Override
    public void clearFutureHistory() {
        undoableHistory.clearFutureHistory();
    }

}
