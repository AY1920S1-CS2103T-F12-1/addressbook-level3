package seedu.address.logic.commands.arguments;

/**
 * Represents an ArgumentBuilder responsible for building {@link StringArgument}
 */
public class StringArgumentBuilder extends ArgumentBuilder<String> {

    StringArgumentBuilder(String description) {
        super(description);
    }

    @Override
    Argument<String> argumentBuild() {
        return new StringArgument(this);
    }
}
