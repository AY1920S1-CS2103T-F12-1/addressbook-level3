package seedu.address.logic.commands.listeners;

/**
 * Represents a listener that will be notified with new command input.
 * Command input flows from user interface -> business logic.
 */
public interface CommandInputListener {

    void onCommandInput(String input);
}
