package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_ADD_EVENT_SUCCESS;

import java.util.Objects;

import seedu.address.model.Model;
import seedu.address.model.events.DateTime;
import seedu.address.model.events.EventSource;
import seedu.address.ui.UserOutput;

/**
 * Represents a Command which adds an EventSource to the Model.
 */
public class AddEventCommand extends Command {

    private final Model model;
    private final EventSource event;

    AddEventCommand(AddEventCommandBuilder builder) {
        String description = Objects.requireNonNull(builder.getDescription());
        DateTime start = Objects.requireNonNull(builder.getStart());

        this.model = builder.getModel();
        this.event = EventSource.newBuilder(description, start)
            .build();
    }

    public static CommandBuilder newBuilder(Model model) {
        return new AddEventCommandBuilder(model).init();
    }

    @Override
    public UserOutput execute() {
        model.addEvent(this.event);
        return new UserOutput(String.format(MESSAGE_ADD_EVENT_SUCCESS, this.event.getDescription()));
    }
}
