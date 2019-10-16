package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

class CommandParserTest {

    private static final String COMMAND_ADD_EVENT = "add_event";
    private static final String COMMAND_DELETE_EVENT = "delete_event";
    private static final String COMMAND_EDIT_EVENT = "edit_event";

    private static CommandParser commandParser;

    @BeforeAll
    static void setUp() {
        CommandKeywordParser keywordParser = new CommandKeywordParser();
        keywordParser.addCommand(COMMAND_ADD_EVENT, () -> AddEventCommand.newBuilder(null));
        keywordParser.addCommand(COMMAND_DELETE_EVENT, () -> DeleteEventCommand.newBuilder(null));
        keywordParser.addCommand(COMMAND_EDIT_EVENT, () -> EditEventCommand.newBuilder(null));
        commandParser = new CommandParser(keywordParser);
    }

    @Test
    void parse_invalidInputFormat_failure() {
        String[] tests = { "", " " };
        for (String test : tests) {
            assertThrows(ParseException.class, () -> commandParser.parse(test));
        }
    }

    @Test
    void parse_addEventCommand_success() throws ParseException {
        String test = "add_event 'Celebrate Rori Birthday' '18/08/2019 16:00'";
        assertTrue(commandParser.parse(test) instanceof AddEventCommand);
    }

    @Test
    void parse_deleteEventCommand_success() throws ParseException {
        String test = "delete_event 1 2 3";
        assertTrue(commandParser.parse(test) instanceof DeleteEventCommand);
    }

    @Test
    void parse_editEventCommand_success() throws ParseException {
        String test = "edit_event 1 2 3";
        assertTrue(commandParser.parse(test) instanceof EditEventCommand);
    }
}
