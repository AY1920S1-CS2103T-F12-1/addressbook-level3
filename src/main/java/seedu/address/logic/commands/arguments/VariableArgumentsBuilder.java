package seedu.address.logic.commands.arguments;

import java.util.List;

/**
 * Represents an object that builds variable arguments.
 * @param <T> the type of variable arguments to build
 */
public abstract class VariableArgumentsBuilder<T> {

    private final String description;

    private VariableArguments<T> argument;

    VariableArgumentsBuilder(String description) {
        this.description = description;
    }

    public List<T> getValues() {
        return this.argument.getValues();
    }

    /**
     * Builds the variable arguments.
     * @return the built variable arguments
     */
    public VariableArguments<T> build() {
        this.argument = argumentBuild();
        return this.argument;
    }

    abstract VariableArguments<T> argumentBuild();

    String getDescription() {
        return description;
    }
}
