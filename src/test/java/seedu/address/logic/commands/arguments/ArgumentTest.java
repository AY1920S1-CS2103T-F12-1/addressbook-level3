package seedu.address.logic.commands.arguments;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Objects;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.ArgumentException;

class ArgumentTest {

    @Test
    void build_acceptNull_failure() {
        assertThrows(NullPointerException.class, () -> new ArgumentMock().accept(null));
    }

    @Test
    void build_optionalArgument_success() {
        assertDoesNotThrow(() -> new ArgumentMock().build(false));
    }

    @Test
    void build_acceptOptionalArgument_success() {
        String[] arguments = { "", " ", "a" };
        for (String arg : arguments) {
            assertDoesNotThrow(() -> {
                new ArgumentMock()
                    .accept(arg)
                    .build(false);
            });
        }
    }

    @Test
    void build_requiredArgument_failure() {
        assertThrows(ArgumentException.class, () -> new ArgumentMock().build(true));
    }

    @Test
    void build_acceptRequiredArgument_success() {
        String[] arguments = { "", " ", "a" };
        for (String test : arguments) {
            assertDoesNotThrow(() -> {
                new ArgumentMock()
                    .accept(test)
                    .build(true);
            });
        }
    }

    private static class ArgumentMock extends Argument<Object> {

        ArgumentMock() {
            super(new ArgumentBuilderStub());
        }

        @Override
        Object parse(String userInput) {
            // should not be called when argument is optional.
            Objects.requireNonNull(userInput);
            return "";
        }
    }

    private static class ArgumentBuilderStub extends ArgumentBuilder<Object> {

        ArgumentBuilderStub() {
            super("");
        }

        @Override
        Argument<Object> argumentBuild() {
            return new ArgumentMock();
        }
    }
}
