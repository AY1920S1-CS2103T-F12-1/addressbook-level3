package seedu.address.logic.composers;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Composer that can compose an {@link Instant} into a String.
 */
public class InstantComposer {

    private final DateTimeFormatter composer;

    public InstantComposer(DateTimeFormatter composer) {
        this.composer = composer;
    }

    public String compose(Instant instant) {
        return this.composer.format(instant);
    }
}
