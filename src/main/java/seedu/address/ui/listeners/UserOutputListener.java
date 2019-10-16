package seedu.address.ui.listeners;

import seedu.address.ui.UserOutput;

/**
 * Represents a listener that will be notified with new user output.
 * User output flows from business logic -> user interface.
 */
public interface UserOutputListener {

    void onUserOutput(UserOutput output);
}
