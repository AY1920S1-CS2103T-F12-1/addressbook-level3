package seedu.address.model.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TaskSourceBuilderTest {

    @Test
    void constructor_descriptionNullAsArgument_shouldThrowException() {
        Assertions.assertThrows(NullPointerException.class, (
        ) -> new TaskSourceBuilder(null));
    }

    @Test
    void constructor_descriptionStoredProperly_valueAsExpected() {
        String description = "Test";
        TaskSourceBuilder tsb = new TaskSourceBuilder(description);
        assertEquals(tsb.getDescription(), description);
    }

    @Test
    void constructor_dueDateStoredProperly_valueAsExpected() {
        TaskSourceBuilder tsb = new TaskSourceBuilder("test");
        assertNull(tsb.getDueDate());
    }

    @Test
    void constructor_isDoneStoredProperly_valueAsExpected() {
        TaskSourceBuilder tsb = new TaskSourceBuilder("test");
        assertFalse(tsb.isDone());
    }
}
