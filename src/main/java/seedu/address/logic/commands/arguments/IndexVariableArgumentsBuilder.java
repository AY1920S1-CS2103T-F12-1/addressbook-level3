package seedu.address.logic.commands.arguments;

/**
 * Represents a VariableArgumentsBuilder responsible for building {@link IndexVariableArguments}
 */
public class IndexVariableArgumentsBuilder extends VariableArgumentsBuilder<Integer> {

    IndexVariableArgumentsBuilder(String description) {
        super(description);
    }

    @Override
    VariableArguments<Integer> argumentBuild() {
        return new IndexVariableArguments(this);
    }
}
