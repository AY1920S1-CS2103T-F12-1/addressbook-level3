package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EDIT_EVENT_SUCCESS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EVENT_INDEX;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DateTime;
import seedu.address.model.ModelManager;
import seedu.address.model.events.EventSource;
import seedu.address.ui.UserOutput;

/**
 * Represents a Command that edits EventSources in the Model.
 */
public class EditEventCommand extends Command {

    private final ModelManager model;
    private final List<Integer> indexes;
    private final String description;
    private final DateTime start;
    private final DateTime end;
    private final DateTime remind;
    private final List<String> tags;

    EditEventCommand(EditEventCommandBuilder builder) {
        this.model = builder.getModel();
        this.indexes = builder.getIndexes();
        this.description = builder.getDescription();
        this.start = builder.getStart();
        this.end = builder.getEnd();
        this.remind = builder.getRemind();
        this.tags = builder.getTags();
    }

    public static CommandBuilder newBuilder(ModelManager model) {
        return new EditEventCommandBuilder(model).init();
    }

    @Override
    public UserOutput execute() throws CommandException {
        List<EventSource> list = model.getEventList();

        List<EventSource> toEdit = new ArrayList<>();
        for (Integer index : indexes) {
            try {
                toEdit.add(list.get(index));
            } catch (IndexOutOfBoundsException e) {
                throw new CommandException(String.format(MESSAGE_INVALID_EVENT_INDEX, index + 1));
            }
        }

        // Replace field if it is not null.
        for (EventSource event : toEdit) {
            String description;
            if (this.description == null) {
                description = event.getDescription();
            } else {
                description = this.description;
            }

            DateTime start;
            if (this.start == null) {
                start = event.getStartDateTime();
            } else {
                start = this.start;
            }

            DateTime end;
            if (this.end == null) {
                end = event.getEnd();
            } else {
                end = this.end;
            }

            DateTime remind;
            if (this.remind == null) {
                remind = event.getRemind();
            } else {
                remind = this.remind;
            }

            Collection<String> tags;
            if (this.tags == null) {
                tags = event.getTags();
            } else {
                tags = this.tags;
            }

            EventSource replacement = EventSource.newBuilder(description, start)
                .setEnd(end)
                .setRemind(remind)
                .setTags(tags)
                .build();
            model.replaceEvent(event, replacement);
        }

        return new UserOutput(String.format(MESSAGE_EDIT_EVENT_SUCCESS, toEdit.stream()
            .map(EventSource::getDescription)
            .collect(Collectors.joining(", "))));
    }
}
