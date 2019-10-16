package seedu.address.logic.commands;

import java.util.HashMap;
import java.util.Map;

import seedu.address.logic.commands.exceptions.ArgumentException;
import seedu.address.logic.commands.options.Option;
import seedu.address.logic.commands.options.OptionBuilder;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents an object which can build commands.
 * Responsible for giving the correct user input to the correct {@link Option}.
 */
public abstract class CommandBuilder {

    // Context is the current Option to add arguments to.
    private Option context;

    // Represents the command arguments in a Command.
    private Option arguments;
    private Map<String, Option> options;

    /**
     * This method must be called first.
     */
    CommandBuilder init() {
        // Build OptionBuilders
        this.arguments = this.getCommandArguments().build();
        this.options = new HashMap<>();
        for (Map.Entry<String, OptionBuilder> entry : this.getCommandOptions().entrySet()) {
            String keyword = entry.getKey();
            OptionBuilder option = entry.getValue();
            this.options.put(keyword, option.build());
        }

        // Set the initial context.
        this.context = this.arguments;
        this.context.setActive();
        return this;
    }

    /**
     * Accepts a sentence from user input tokens.
     * A sentence can either be an option or an argument.
     * @param sentence a sentence from user input
     */
    public CommandBuilder acceptSentence(String sentence) {
        if (this.options.containsKey(sentence)) {
            // Sets the context.
            this.context = this.options.get(sentence);
            this.context.setActive();
        } else {
            // Adds an argument to the context.
            this.context.acceptArgument(sentence);
        }
        return this;
    }

    /**
     * Builds all arguments and returns a Command.
     * @return the built Command
     * @throws ArgumentException if any argument is required but null
     * @throws ParseException if any argument is required but null
     */
    public Command build() throws ArgumentException, ParseException {
        this.arguments.build();
        for (Option option : this.options.values()) {
            option.build();
        }
        return this.commandBuild();
    }

    abstract OptionBuilder getCommandArguments();

    abstract Map<String, OptionBuilder> getCommandOptions();

    abstract Command commandBuild();
}
