package seedu.address.logic.commands.arguments;

/**
 * Represents an object that builds arguments.
 * @param <T> the type of the argument to build
 */
public abstract class ArgumentBuilder<T> {

    private final String description;

    private Argument<T> argument;

    ArgumentBuilder(String description) {
        this.description = description;
    }

    public T getValue() {
        return this.argument.getValue();
    }

    /**
     * Builds the argument.
     * @return the built argument
     */
    public Argument<T> build() {
        this.argument = argumentBuild();
        return this.argument;
    }

    abstract Argument<T> argumentBuild();

    String getDescription() {
        return description;
    }
}
