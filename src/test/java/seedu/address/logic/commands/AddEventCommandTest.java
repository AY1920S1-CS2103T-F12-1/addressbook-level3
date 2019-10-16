package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.AddEventCommandBuilder.OPTION_END_DATE_TIME;
import static seedu.address.logic.commands.AddEventCommandBuilder.OPTION_REMIND_DATE_TIME;
import static seedu.address.logic.commands.AddEventCommandBuilder.OPTION_TAGS;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

class AddEventCommandTest {

    @Test
    void build_fullCommand_success() {
        String description = "description";
        String start = "11/11/1111 11:00";
        String end = "11/11/1111 12:00";
        String remind = "11/11/1111 08:00";
        String[] tags = new String[]{"a", "b", "c"};
        assertDoesNotThrow(() -> {
            AddEventCommand.newBuilder(null)
                .acceptSentence(description)
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
        String description = "description";
        String start = "11/11/1111 11:00";
        assertDoesNotThrow(() -> {
            // TODO: Create stub
            Model model = new ModelManager();
            assertEquals(model.getEventList().getReadOnlyList().size(), 0);

            Command command = AddEventCommand.newBuilder(model)
                .acceptSentence(description)
                .acceptSentence(start)
                .build();

            // TODO: Equality test
            command.execute();
            assertEquals(model.getEventList().getReadOnlyList().size(), 1);
        });
    }
}
