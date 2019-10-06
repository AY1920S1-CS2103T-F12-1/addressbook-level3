package seedu.address.model;

import java.util.ArrayList;

public class UndoableHistory {

    private ArrayList<AddressBook> addressBookStateList;
    private int currentStateIndexPointer;

    UndoableHistory(AddressBook addressBook) {
        addressBookStateList = new ArrayList<>();
        addressBookStateList.add(addressBook);
        currentStateIndexPointer = 0;
    }

    AddressBook getCurrentState() {
        return addressBookStateList.get(currentStateIndexPointer);
    }

    /**
     * Saves the current AddressBook state to the UndoableHistory.
     */
    void commit(AddressBook addressBook) {
        addressBookStateList.add(addressBook);
        currentStateIndexPointer++;
    }

    /**
     * Restores the previous address book state from UndoableHistory.
     */
    void undo() {
        currentStateIndexPointer--;
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
