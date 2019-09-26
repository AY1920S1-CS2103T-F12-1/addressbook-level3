package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable
 */
public class Remark {

    public final String value;

    /**
     * Constructs a {@code Remark}.
     *
     * @param value the remark.
     */
    public Remark(String value) {
        requireNonNull(value);
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
