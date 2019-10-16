package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.EditEventCommandBuilder.OPTION_DESCRIPTION;
import static seedu.address.logic.commands.EditEventCommandBuilder.OPTION_END_DATE_TIME;
import static seedu.address.logic.commands.EditEventCommandBuilder.OPTION_REMIND_DATE_TIME;
import static seedu.address.logic.commands.EditEventCommandBuilder.OPTION_START_DATE_TIME;
import static seedu.address.logic.commands.EditEventCommandBuilder.OPTION_TAGS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.events.DateTime;
import seedu.address.model.events.EventSource;

class EditEventCommandTest {

    @Test
    void build_fullCommand_success() {
        String[] indexes = new String[]{"1", "2", "3"};
        String description = "description";
        String start = "11/11/1111 11:00";
        String end = "11/11/1111 12:00";
        String remind = "11/11/1111 08:00";
        String[] tags = new String[]{"a", "b", "c"};
        assertDoesNotThrow(() -> {
            EditEventCommand.newBuilder(null)
                .acceptSentence(indexes[0])
                .acceptSentence(indexes[1])
                .acceptSentence(indexes[2])
                .acceptSentence(OPTION_DESCRIPTION)
                .acceptSentence(description)
                .acceptSentence(OPTION_START_DATE_TIME)
                .acceptSentence(start)
                .acceptSentence(OPTION_END_DATE_TIME)
                .acceptSentence(end)
                .acceptSentence(OPTION_REMIND_DATE_TIME)
                .acceptSentence(remind)
                .acceptSentence(OPTION_TAGS)
                .acceptSentence(tags[0])
                .acceptSentence(tags[1])
                .acceptSentence(tags[2])
                .build();
        });
    }

    @Test
    void execute_requiredCommand_success() {
        String[] indexes = new String[]{"1", "2", "3"};
        assertDoesNotThrow(() -> {

            Model model = new ModelManager();
            model.addEvent(EventSource.newBuilder("a", DateTime.now()).build());
            model.addEvent(EventSource.newBuilder("b", DateTime.now()).build());
            model.addEvent(EventSource.newBuilder("c", DateTime.now()).build());

            Command command = EditEventCommand.newBuilder(model)
                .acceptSentence(indexes[0])
                .acceptSentence(indexes[1])
                .acceptSentence(indexes[2])
                .build();

            assertEquals(model.getEventList().getReadOnlyList().size(), 3);

            // TODO: Equality test
            command.execute();
            assertEquals(model.getEventList().getReadOnlyList().size(), 3);
        });
    }

    @Test
    void execute_editOneInvalidIndex_failed() {
        String[] indexes = new String[]{"-1", "0", "1"};
        for (String index : indexes) {
            assertThrows(CommandException.class, () -> {
                Model model = new ModelManager();
                assertEquals(model.getEventList().getReadOnlyList().size(), 0);

                Command command = EditEventCommand.newBuilder(model)
                    .acceptSentence(index)
                    .build();

                command.execute();
            });
        }
    }

    @Test
    void execute_editMultipleInvalidIndex_failed() {
        String[] indexes = new String[]{"1", "2", "3"};
        assertThrows(CommandException.class, () -> {
            Model model = new ModelManager();
            model.addEvent(EventSource.newBuilder("a", DateTime.now()).build());
            model.addEvent(EventSource.newBuilder("b", DateTime.now()).build());
            assertEquals(model.getEventList().getReadOnlyList().size(), 2);

            Command command = EditEventCommand.newBuilder(model)
                .acceptSentence(indexes[0])
                .acceptSentence(indexes[1])
                .acceptSentence(indexes[2])
                .build();

            command.execute();
        });
    }
}
