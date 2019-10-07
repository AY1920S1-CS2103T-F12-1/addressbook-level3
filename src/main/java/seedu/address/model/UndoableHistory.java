package seedu.address.model;

import seedu.address.commons.core.LogsCenter;

import java.util.ArrayList;
import java.util.logging.Logger;

public class UndoableHistory {

    /** The addressBook that the GUI is in sync with.
     * This mainAddressBook is updated every time an
     * undo or redo occurs. It is important that ModelManager
     * works only with the mainAddressBook object and not
     * duplicates due to its synchronisation with the GUI.
     */
    private AddressBook mainAddressBook;

    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Deep-copies of mainAddressBook are stored to this list
     * every time a state-changing command is executed.
     * This allows mainAddressBook to retrieve its data
     * from any of these past or future states when an
     * undo or redo command is called.
     */
    private ArrayList<AddressBook> addressBookStateList;
    private int currentStateIndexPointer;

    UndoableHistory(AddressBook addressBook) {
        mainAddressBook = addressBook;
        addressBookStateList = new ArrayList<>();
        // Store a deep-copy of the mainAddressBook to the list
        addressBookStateList.add(new AddressBook(mainAddressBook));
        currentStateIndexPointer = 0;
    }

    /**
     * Returns the current state of the AddressBook.
     *
     * @return AddressBook mainAddressBook.
     */
    AddressBook getCurrentState() {
        return mainAddressBook;
    }

    /**
     * Saves the current AddressBook state to the UndoableHistory.
     */
    void commit(AddressBook addressBook) {
        // Store a deep-copy of the mainAddressBook to the list
        AddressBook deepCopy = new AddressBook(addressBook);
        assert currentStateIndexPointer >= addressBookStateList.size() - 1 :
                "Pointer always points to end of list during commit; All future states must have been discarded.";
        addressBookStateList.add(deepCopy);
        currentStateIndexPointer++;

        // -------Logging---------------------------------------------------------------
        logger.info("State List: ");
        addressBookStateList.get(currentStateIndexPointer).getPersonList()
                .forEach(p -> logger.info(p.name.fullName));
    }

    /**
     * Restores the previous address book state from UndoableHistory.
     */
    void undo() {
        currentStateIndexPointer--;
        // Retrieve data from duplicate of its past state
        mainAddressBook.resetData(addressBookStateList.get(currentStateIndexPointer));

        // -------Logging---------------------------------------------------------------
        logger.info("State List: ");
        addressBookStateList.get(currentStateIndexPointer).getPersonList()
                .forEach(p -> logger.info(p.name.fullName));
    }

    /**
     * Restores the previously undone address book state from UndoableHistory.
     */
    void redo() {
        currentStateIndexPointer++;
    }

    /**
     * Returns true if there are previous address book states to restore, and false otherwise.
     *
     * @return boolean
     */
    boolean canUndo() {
        return currentStateIndexPointer > 0;
    }

    /**
     * Clears all future address book states in addressBookStateList beyond the index given by currentStateIndexPointer
     */
    void clearFutureHistory() {
        addressBookStateList =
                new ArrayList<>(addressBookStateList.subList(0, currentStateIndexPointer + 1));
    }

    @Override
    public String toString() {
        return addressBookStateList.size() + " states in history";
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
            if (currentStateIndexPointer != otherHistory.currentStateIndexPointer
                    || addressBookStateList.size() != otherHistory.addressBookStateList.size()) {
                return false;
            }
            for (int i = 0; i < addressBookStateList.size(); i++) {
                if (!addressBookStateList.get(i).equals(otherHistory.addressBookStateList.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }

}
