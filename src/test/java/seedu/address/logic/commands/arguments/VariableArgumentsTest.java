package seedu.address.logic.commands.arguments;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class VariableArgumentsTest {

    @Test
    void build_acceptNull_failure() {
        assertThrows(NullPointerException.class, () -> new VariableArgumentsMock().accept(null));
    }

    @Test
    void build_noArguments_success() {
        assertDoesNotThrow(() -> new VariableArgumentsMock().build());
    }

    @Test
    void build_multipleArguments_success() {
        String[] arguments = { "", " ", "a" };
        assertDoesNotThrow(() -> {
            new VariableArgumentsMock()
                .accept(arguments[0])
                .accept(arguments[1])
                .accept(arguments[2])
                .build();
        });
    }

    private static class VariableArgumentsMock extends VariableArguments<Object> {

        VariableArgumentsMock() {
            super(new VariableArgumentsBuilderStub());
        }

        @Override
        Object parse(String userInput) {
            return "";
        }
    }

    private static class VariableArgumentsBuilderStub extends VariableArgumentsBuilder<Object> {

        VariableArgumentsBuilderStub() {
            super("");
        }

        @Override
        VariableArguments<Object> argumentBuild() {
            return new VariableArgumentsMock();
        }
    }
}
