package seedu.address.logic.parser;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Parser that is able to parse user input into an object of type {@code T}.
 */
public interface Parser<T> {

    /**
     * Parses {@code userInput} into an object of type {@code T} and returns it.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    T parse(String userInput) throws ParseException;
}
