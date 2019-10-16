package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INDEX;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a parser that can parse user input into an Index (Integer).
 */
public class IndexParser implements Parser<Integer> {

    @Override
    public Integer parse(String userInput) throws ParseException {
        try {
            return Integer.parseInt(userInput) - 1;
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
    }
}
