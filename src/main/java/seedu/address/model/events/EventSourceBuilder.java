package seedu.address.model.events;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a Builder responsible for creating {@link EventSource}.
 */
public class EventSourceBuilder {

    // Required
    private final String description;
    private final DateTime start;

    // Optional
    private DateTime end;
    private Set<String> tags;

    EventSourceBuilder(String description, DateTime start) {
        this.description = Objects.requireNonNull(description);
        this.start = Objects.requireNonNull(start);
    }

    public EventSourceBuilder setEnd(DateTime end) {
        this.end = end;
        return this;
    }

    public EventSourceBuilder setTags(Collection<String> tags) {
        this.tags = new HashSet<>(tags);
        return this;
    }

    public EventSource build() {
        return new EventSource(this);
    }

    String getDescription() {
        return description;
    }

    DateTime getStart() {
        return start;
    }

    DateTime getEnd() {
        return end;
    }

    Set<String> getTags() {
        return tags;
    }
}
