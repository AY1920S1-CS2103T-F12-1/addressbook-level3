package seedu.address.logic.commands.options;

import java.util.List;

import seedu.address.logic.commands.arguments.Argument;
import seedu.address.logic.commands.arguments.VariableArguments;
import seedu.address.logic.commands.exceptions.ArgumentException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents an option in a Command.
 * It can contain 0..* other arguments, and can contain 0..1 variable arguments.
 */
public class Option {

    // Determines whether or not this option is used in the Command.
    private boolean active;
    private int argumentIndex;

    private final List<Argument> arguments;
    private final VariableArguments variableArguments;

    Option(List<Argument> arguments, VariableArguments variableArguments) {
        this.arguments = arguments;
        this.variableArguments = variableArguments;
    }

    public static OptionBuilder newBuilder() {
        return new OptionBuilder();
    }

    /**
     * Accepts user input and passes it to the next argument for parsing.
     * @param argument the user input
     */
    public void acceptArgument(String argument) {
        if (this.argumentIndex < this.arguments.size()) {
            this.arguments.get(this.argumentIndex).accept(argument);
            this.argumentIndex++;
        } else if (this.variableArguments != null) {
            this.variableArguments.accept(argument);
        }
    }

    /**
     * Builds all arguments.
     * @throws ArgumentException if any argument is required but null
     * @throws ParseException if any user input is invalid
     */
    public void build() throws ArgumentException, ParseException {
        for (Argument argument : this.arguments) {
            argument.build(this.active);
        }

        if (this.variableArguments != null) {
            this.variableArguments.build();
        }
    }

    public void setActive() {
        this.active = true;
    }

    public boolean isActive() {
        return active;
    }
}
