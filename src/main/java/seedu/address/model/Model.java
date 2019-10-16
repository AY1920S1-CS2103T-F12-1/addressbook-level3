package seedu.address.model;

import java.nio.file.Path;

import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.events.EventList;
import seedu.address.model.events.EventSource;
import seedu.address.model.events.ReadOnlyEventList;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    ReadOnlyEventList getEventList();

    void setEventList(ReadOnlyEventList eventList);

    boolean hasEvent(EventSource event);

    void deleteEvent(EventSource target);

    void addEvent(EventSource event);

    void setEvent(EventSource target, EventSource editedEvent);

    /**
     * Returns an unmodifiable view of the filtered Event Source List
     */
    ObservableList<EventSource> getFilteredEventList();

    /**
     * Creates a deep-copy of the current event list state and saves that copy to the UndoableHistory.
     */
    void commitToHistory(EventList eventList);

    /**
     * Restores the previous event list state from UndoableHistory.
     */
    void undoFromHistory();

    /**
     * Restores the previously undone event list state from UndoableHistory.
     */
    void redoFromHistory();

    /**
     * Returns true if there are previous event list states to restore, and false otherwise.
     *
     * @return boolean
     */
    boolean canUndoHistory();

    /**
     * Clears all future event list states in UndoableHistory beyond the current state.
     */
    void clearFutureHistory();

}
