package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_DELETE_TASK_EMPTY;
import static seedu.address.commons.core.Messages.MESSAGE_DELETE_TASK_FAILURE;
import static seedu.address.commons.core.Messages.MESSAGE_DELETE_TASK_SUCCESS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_INDEX;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelData;
import seedu.address.model.ModelManager;
import seedu.address.model.tasks.TaskSource;
import seedu.address.ui.UserOutput;

/**
 * Represents a Command that deletes TaskSources from the Model.
 */
public class DeleteTaskCommand extends Command {

    private final ModelManager model;
    private final List<Integer> indexes;
    private final List<String> tags;

    DeleteTaskCommand(DeleteTaskCommandBuilder builder) {
        this.model = builder.getModel();
        this.indexes = builder.getIndexes();
        this.tags = builder.getTags();
    }

    public static CommandBuilder newBuilder(ModelManager model) {
        return new DeleteTaskCommandBuilder(model).init();
    }

    @Override
    public UserOutput execute() throws CommandException {
        List<TaskSource> tasks = new ArrayList<>(model.getTasks());

        LinkedHashSet<TaskSource> toDelete = new LinkedHashSet<>();
        if (this.indexes.isEmpty()) {
            if (this.tags.isEmpty()) {
                throw new CommandException(MESSAGE_DELETE_TASK_EMPTY);
            }

            // Indexes empty but tags is not empty:
            // Delete all tasks with matching tags.
            for (TaskSource task : tasks) {
                Set<String> tags = task.getTags();
                if (tags == null || tags.containsAll(this.tags)) {
                    toDelete.add(task);
                }
            }
        } else {
            // Indexes not empty:
            // Delete given tasks with matching tags
            for (Integer index : indexes) {
                try {
                    TaskSource task = tasks.get(index);
                    // Delete TaskSource only if it matches all tags.
                    Set<String> tags = task.getTags();
                    if (tags == null || tags.containsAll(this.tags)) {
                        toDelete.add(tasks.get(index));
                    }
                } catch (IndexOutOfBoundsException e) {
                    throw new CommandException(String.format(MESSAGE_INVALID_TASK_INDEX, index));
                }
            }
        }

        // No tasks found.
        if (toDelete.isEmpty()) {
            throw new CommandException(MESSAGE_DELETE_TASK_FAILURE);
        }

        // Remove task if toDelete contains the same task
        tasks.removeIf(toDelete::contains);

        // Replace model
        this.model.setModelData(new ModelData(this.model.getEvents(), tasks));

        return new UserOutput(String.format(MESSAGE_DELETE_TASK_SUCCESS, toDelete.stream()
            .map(TaskSource::getDescription)
            .collect(Collectors.joining(", "))));
    }
}
