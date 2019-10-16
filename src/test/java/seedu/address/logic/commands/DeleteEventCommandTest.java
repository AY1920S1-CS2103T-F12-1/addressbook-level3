package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.DeleteEventCommandBuilder.OPTION_TAGS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.events.DateTime;
import seedu.address.model.events.EventSource;

class DeleteEventCommandTest {

    @Test
    void build_fullCommand_success() {
        String[] indexes = new String[]{"1", "2", "3"};
        String[] tags = new String[]{"a", "b", "c"};
        assertDoesNotThrow(() -> {
            DeleteEventCommand.newBuilder(null)
                .acceptSentence(indexes[0])
                .acceptSentence(indexes[1])
                .acceptSentence(indexes[2])
                .acceptSentence(OPTION_TAGS)
                .acceptSentence(tags[0])
                .acceptSentence(tags[1])
                .acceptSentence(tags[2])
                .build();
        });
    }

    @Test
    void execute_requiredCommand_success() {
        // TODO: This should throw an exception
        assertDoesNotThrow(() -> {
            Model model = new ModelManager();
            assertEquals(model.getEventList().getReadOnlyList().size(), 0);

            Command command = DeleteEventCommand.newBuilder(model)
                .build();

            command.execute();
            assertEquals(model.getEventList().getReadOnlyList().size(), 0);
        });
    }

    @Test
    void execute_deleteOne_success() {
        String index = "2";
        assertDoesNotThrow(() -> {
            Model model = new ModelManager();
            model.addEvent(EventSource.newBuilder("a", DateTime.now()).build());
            model.addEvent(EventSource.newBuilder("b", DateTime.now()).build());
            model.addEvent(EventSource.newBuilder("c", DateTime.now()).build());
            assertEquals(model.getEventList().getReadOnlyList().size(), 3);

            Command command = DeleteEventCommand.newBuilder(model)
                .acceptSentence(index)
                .build();

            command.execute();
            assertEquals(model.getEventList().getReadOnlyList().size(), 2);
        });
    }

    @Test
    void execute_deleteOneInvalidIndex_failed() {
        String[] indexes = new String[]{"-1", "0", "1"};
        for (String index : indexes) {
            assertThrows(CommandException.class, () -> {
                Model model = new ModelManager();
                assertEquals(model.getEventList().getReadOnlyList().size(), 0);

                Command command = DeleteEventCommand.newBuilder(model)
                    .acceptSentence(index)
                    .build();

                command.execute();
            });
        }
    }

    @Test
    void execute_deleteMultiple_success() {
        String[] indexes = new String[]{"2", "3", "4"};
        assertDoesNotThrow(() -> {
            Model model = new ModelManager();
            model.addEvent(EventSource.newBuilder("a", DateTime.now()).build());
            model.addEvent(EventSource.newBuilder("b", DateTime.now()).build());
            model.addEvent(EventSource.newBuilder("c", DateTime.now()).build());
            model.addEvent(EventSource.newBuilder("d", DateTime.now()).build());
            model.addEvent(EventSource.newBuilder("e", DateTime.now()).build());
            assertEquals(model.getEventList().getReadOnlyList().size(), 5);

            Command command = DeleteEventCommand.newBuilder(model)
                .acceptSentence(indexes[0])
                .acceptSentence(indexes[1])
                .acceptSentence(indexes[2])
                .build();

            command.execute();
            assertEquals(model.getEventList().getReadOnlyList().size(), 2);
        });
    }

    @Test
    void execute_deleteMultipleInvalidIndexes_failed() {
        String[] indexes = new String[]{"1", "2", "3"};
        assertThrows(CommandException.class, () -> {
            Model model = new ModelManager();
            model.addEvent(EventSource.newBuilder("a", DateTime.now()).build());
            model.addEvent(EventSource.newBuilder("b", DateTime.now()).build());
            assertEquals(model.getEventList().getReadOnlyList().size(), 2);

            Command command = DeleteEventCommand.newBuilder(model)
                .acceptSentence(indexes[0])
                .acceptSentence(indexes[1])
                .acceptSentence(indexes[2])
                .build();

            command.execute();
        });
    }
}
