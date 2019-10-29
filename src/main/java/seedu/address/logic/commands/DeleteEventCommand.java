package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_DELETE_EVENT_EMPTY;
import static seedu.address.commons.core.Messages.MESSAGE_DELETE_EVENT_FAILURE;
import static seedu.address.commons.core.Messages.MESSAGE_DELETE_EVENT_SUCCESS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EVENT_INDEX;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.events.EventSource;
import seedu.address.ui.UserOutput;

/**
 * Represents a Command that deletes EventSources from the Model.
 */
public class DeleteEventCommand extends Command {

    private final ModelManager model;
    private final List<Integer> indexes;
    private final List<String> tags;

    DeleteEventCommand(DeleteEventCommandBuilder builder) {
        this.model = builder.getModel();
        this.indexes = builder.getIndexes();
        this.tags = builder.getTags();
    }

    public static CommandBuilder newBuilder(ModelManager model) {
        return new DeleteEventCommandBuilder(model).init();
    }

    @Override
    public UserOutput execute() throws CommandException {
        List<EventSource> list = model.getEventList();

        List<EventSource> toDelete = new ArrayList<>();
        if (this.indexes.isEmpty()) {
            if (this.tags.isEmpty()) {
                throw new CommandException(MESSAGE_DELETE_EVENT_EMPTY);
            }

            // Indexes empty but tags is not empty:
            // Delete all events with matching tags.
            for (EventSource event : list) {
                Set<String> tags = event.getTags();
                if (tags == null || tags.containsAll(this.tags)) {
                    toDelete.add(event);
                }
            }
        } else {
            // Indexes not empty:
            // Delete given events with matching tags
            for (Integer index : indexes) {
                try {
                    EventSource event = list.get(index);
                    // Delete EventSource only if it matches all tags.
                    Set<String> tags = event.getTags();
                    if (tags == null || tags.containsAll(this.tags)) {
                        toDelete.add(list.get(index));
                    }
                } catch (IndexOutOfBoundsException e) {
                    throw new CommandException(String.format(MESSAGE_INVALID_EVENT_INDEX, index + 1));
                }
            }
        }

        // No events found.
        if (toDelete.isEmpty()) {
            throw new CommandException(MESSAGE_DELETE_EVENT_FAILURE);
        }

        for (EventSource event : toDelete) {
            model.removeEvent(event);
        }

        return new UserOutput(String.format(MESSAGE_DELETE_EVENT_SUCCESS, toDelete.stream()
            .map(EventSource::getDescription)
            .collect(Collectors.joining(", "))));
    }
}
