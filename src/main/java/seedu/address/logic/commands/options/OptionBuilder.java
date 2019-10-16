package seedu.address.logic.commands.options;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.arguments.Argument;
import seedu.address.logic.commands.arguments.ArgumentBuilder;
import seedu.address.logic.commands.arguments.VariableArguments;
import seedu.address.logic.commands.arguments.VariableArgumentsBuilder;

/**
 * Represents an object that builds {@link Option}.
 */
public class OptionBuilder {

    private final List<Argument> arguments;
    private VariableArguments variableArguments;

    OptionBuilder() {
        this.arguments = new ArrayList<>();
    }

    /**
     * Adds an argument to this option.
     * @param argument the argument
     * @return this instance
     */
    public OptionBuilder addArgument(ArgumentBuilder argument) {
        this.arguments.add(argument.build());
        return this;
    }

    public OptionBuilder setVariableArguments(VariableArgumentsBuilder variableArguments) {
        this.variableArguments = variableArguments.build();
        return this;
    }

    public Option build() {
        return new Option(this.arguments, this.variableArguments);
    }
}
