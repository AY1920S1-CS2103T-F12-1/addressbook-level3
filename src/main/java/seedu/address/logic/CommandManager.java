package seedu.address.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.listeners.CommandInputListener;
import seedu.address.logic.parser.CommandKeywordParser;
import seedu.address.logic.parser.CommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.UserOutput;
import seedu.address.ui.listeners.UserOutputListener;

/**
 * Represents a class that invokes commands based on command input it receives from a Ui.
 * Represents the invoker class in https://en.wikipedia.org/wiki/Command_pattern.
 */
public class CommandManager implements CommandInputListener {

    private final Logger logger = LogsCenter.getLogger(CommandManager.class);
    private final CommandKeywordParser keywordParser;
    private final CommandParser commandParser;
    private final List<UserOutputListener> userOutputListeners;

    public CommandManager() {
        this.keywordParser = new CommandKeywordParser();
        this.commandParser = new CommandParser(this.keywordParser);
        this.userOutputListeners = new ArrayList<>();
    }

    /**
     * Adds a command to be tracked by CommandManager.
     * @param keyword the command keyword
     * @param builder the supplier to invoke
     */
    public void addCommand(String keyword, Supplier<CommandBuilder> builder) {
        this.keywordParser.addCommand(keyword, builder);
    }

    public void addUserOutputListener(UserOutputListener listener) {
        this.userOutputListeners.add(listener);
    }

    @Override
    public void onCommandInput(String input) {
        try {
            Command command = this.commandParser.parse(input);
            UserOutput output = command.execute();
            userOutputListeners.forEach(l -> l.onUserOutput(output));
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + input);
            userOutputListeners.forEach(l -> l.onUserOutput(new UserOutput(e.getMessage())));
        }
    }
}
