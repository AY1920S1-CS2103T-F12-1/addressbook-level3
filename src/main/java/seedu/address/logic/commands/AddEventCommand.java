package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_ADD_EVENT_SUCCESS;

import java.util.Objects;

import seedu.address.model.DateTime;
import seedu.address.model.ModelManager;
import seedu.address.model.events.EventSource;
import seedu.address.ui.UserOutput;

/**
 * Represents a Command which adds an EventSource to the Model.
 */
public class AddEventCommand extends Command {

    private final ModelManager model;
    private final EventSource event;

    AddEventCommand(AddEventCommandBuilder builder) {
        String description = Objects.requireNonNull(builder.getDescription());
        DateTime start = Objects.requireNonNull(builder.getStart());

        this.model = builder.getModel();
        this.event = EventSource.newBuilder(description, start)
            .setEnd(builder.getEnd())
            .setRemind(builder.getRemind())
            .setTags(builder.getTags())
            .build();
    }

    public static CommandBuilder newBuilder(ModelManager model) {
        return new AddEventCommandBuilder(model).init();
    }

    @Override
    public UserOutput execute() {
        model.addEvents(this.event);
        return new UserOutput(String.format(MESSAGE_ADD_EVENT_SUCCESS, this.event.getDescription()));
    }
}
