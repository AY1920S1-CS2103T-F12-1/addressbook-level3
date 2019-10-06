package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UndoableHistory undoableHistory;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook readOnlyAddressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(readOnlyAddressBook, userPrefs);

        logger.fine("Initializing with address book: " + readOnlyAddressBook + " and user prefs " + userPrefs);

        AddressBook addressBook = new AddressBook(readOnlyAddressBook);

        undoableHistory = new UndoableHistory(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(addressBook.getPersonList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
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
    public void setAddressBook(ReadOnlyAddressBook readOnlyAddressBook) {
        // Make a deep-copy of the current state of AddressBook
        AddressBook addressBook = new AddressBook(undoableHistory.getCurrentState());

        // Clear the AddressBook
        addressBook.resetData(readOnlyAddressBook);

        // Save the modified AddressBook state to the UndoableHistory
        commitToHistory(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return undoableHistory.getCurrentState();
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return undoableHistory.getCurrentState().hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        // Make a deep-copy of the current state of AddressBook
        AddressBook addressBook = new AddressBook(undoableHistory.getCurrentState());

        // Delete the person
        addressBook.removePerson(target);

        // Save the modified AddressBook state to the UndoableHistory
        commitToHistory(addressBook);
    }

    @Override
    public void addPerson(Person person) {
        // Make a deep-copy of the current state of AddressBook
        AddressBook addressBook = new AddressBook(undoableHistory.getCurrentState());

        // Add the person
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // Save the modified AddressBook state to the UndoableHistory
        commitToHistory(addressBook);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        // Make a deep-copy of the current state of AddressBook
        AddressBook addressBook = new AddressBook(undoableHistory.getCurrentState());

        // Override the target person with the edited Person
        addressBook.setPerson(target, editedPerson);

        // Save the modified AddressBook state to the UndoableHistory
        commitToHistory(addressBook);
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
        return undoableHistory.equals(other.undoableHistory)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

    //=========== Undoable History ==========================================================================

    /**
     * Saves the current AddressBook state to the UndoableHistory.
     */
    @Override
    public void commitToHistory(AddressBook addressBook) {
        undoableHistory.commit(addressBook);
    }

    /**
     * Restores the previous address book state from UndoableHistory.
     */
    @Override
    public void undoFromHistory() {
        undoableHistory.undo();
    }

    /**
     * Restores the previously undone address book state from UndoableHistory.
     */
    @Override
    public void redoFromHistory() {
        undoableHistory.redo();
    }

    /**
     * Returns true if there are previous address book states to restore, and false otherwise.
     *
     * @return boolean
     */
    @Override
    public boolean canUndoHistory() {
        return undoableHistory.canUndo();
    }

}
