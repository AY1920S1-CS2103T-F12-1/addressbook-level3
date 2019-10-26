package seedu.address.model.tasks;

import java.util.Objects;
import java.util.Set;

import seedu.address.model.DateTime;

/**
 * Represents a TaskSource in Horo.
 * It is immutable.
 */
public class TaskSource {

    // Required
    private final String description;
    private final DateTime dueDate;
    private final boolean isCompleted;

    // Optional
    //private final Duration expectedDuration;
    private final Set<String> tags;

    TaskSource(TaskSourceBuilder taskSourceBuilder) {
        this.description = taskSourceBuilder.getDescription();
        this.dueDate = taskSourceBuilder.getDueDate();
        this.isCompleted = taskSourceBuilder.getCompletionStatus();
        this.tags = taskSourceBuilder.getTags();
    }

    /**
     * Copy constructor.
     * Creates a deep-copy of an TaskSource.
     * @param taskSource the taskSource to deep-copy.
     */
    public TaskSource(TaskSource taskSource) {
        this.description = taskSource.description;
        this.dueDate = taskSource.dueDate;
        this.isCompleted = taskSource.isCompleted;
        this.tags = taskSource.tags;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof TaskSource) {
            TaskSource t = (TaskSource) object;
            return Objects.equals(this.description, t.description)
                && Objects.equals(this.dueDate, t.dueDate)
                && Objects.equals(this.isCompleted, t.isCompleted)
                && Objects.equals(this.tags, t.tags);
        }
        return false;
    }

    public static TaskSourceBuilder newBuilder(String description, DateTime dueDate, boolean isCompleted) {
        return new TaskSourceBuilder(description, dueDate, isCompleted);
    }

    /*
    TODO: implement the following method:
    public String toIcsString
    */
}
