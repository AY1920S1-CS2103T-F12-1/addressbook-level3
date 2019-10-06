package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;

/**
 * Undoes the previous command.
 * Commands can be undone up to the starting up of the program.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "The previous command has been undone";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!undoableHistory.isUndoable()) {
            throw new CommandException(MESSAGE_NOTHING_TO_UNDO);
        }

        undoableHistory.undo();

        return new CommandResult(MESSAGE_SUCCESS);
    }

}
