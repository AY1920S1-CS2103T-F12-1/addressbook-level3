package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EDIT_EVENT_SUCCESS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EVENT_INDEX;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.events.DateTime;
import seedu.address.model.events.EventSource;
import seedu.address.ui.UserOutput;

/**
 * Represents a Command that edits EventSources in the Model.
 */
public class EditEventCommand extends Command {

    private final Model model;
    private final List<Integer> indexes;
    private final String description;
    private final DateTime start;
    private final DateTime end;
    private final DateTime remind;
    private final List<String> tags;

    EditEventCommand(EditEventCommandBuilder builder) {
        this.model = builder.getModel();
        this.indexes = Objects.requireNonNull(builder.getIndexes());
        this.description = builder.getDescription();
        this.start = builder.getStart();
        this.end = builder.getEnd();
        this.remind = builder.getRemind();
        this.tags = builder.getTags();
    }

    public static CommandBuilder newBuilder(Model model) {
        return new EditEventCommandBuilder(model).init();
    }

    @Override
    public UserOutput execute() throws CommandException {
        ObservableList<EventSource> list = model.getEventList().getReadOnlyList();

        List<EventSource> events = new ArrayList<>();
        for (Integer index : indexes) {
            try {
                events.add(list.get(index));
            } catch (IndexOutOfBoundsException e) {
                throw new CommandException(String.format(MESSAGE_INVALID_EVENT_INDEX, index + 1));
            }
        }

        for (EventSource event : events) {
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

            EventSource replacement = EventSource.newBuilder(description, start)
                .build();
            model.setEvent(event, replacement);
        }

        return new UserOutput(String.format(MESSAGE_EDIT_EVENT_SUCCESS, events.stream()
            .map(EventSource::getDescription)
            .collect(Collectors.joining(", "))));
    }
}
