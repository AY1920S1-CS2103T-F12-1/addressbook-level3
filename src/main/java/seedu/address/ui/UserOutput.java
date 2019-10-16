package seedu.address.ui;

import static java.util.Objects.requireNonNull;

/**
 * Represents the output from business logic, to be displayed to the user.
 */
public class UserOutput {

    private final String output;

    public UserOutput(String output) {
        this.output = requireNonNull(output);
    }

    @Override
    public String toString() {
        return this.output;
    }
}
