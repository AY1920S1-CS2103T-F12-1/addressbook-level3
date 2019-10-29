package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_ADD_TASK_SUCCESS;

import java.util.Objects;

import seedu.address.model.ModelManager;
import seedu.address.model.tasks.TaskSource;
import seedu.address.ui.UserOutput;

/**
 * Represents a Command which adds a TaskSource to the Model.
 */
public class AddTaskCommand extends Command {

    private final ModelManager model;
    private final TaskSource task;

    AddTaskCommand(AddTaskCommandBuilder builder) {
        String description = Objects.requireNonNull(builder.getDescription());

        this.model = builder.getModel();
        this.task = TaskSource.newBuilder(description)
            .setDueDate(builder.getDueDate())
            .setTags(builder.getTags())
            .build();
    }

    public static CommandBuilder newBuilder(ModelManager model) {
        return new AddTaskCommandBuilder(model).init();
    }

    @Override
    public UserOutput execute() {
        model.addTasks(this.task);
        return new UserOutput(String.format(MESSAGE_ADD_TASK_SUCCESS, this.task.getDescription()));
    }
}
