package seedu.address.model.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TaskSourceTest {
    @Test
    void equality_useSameTaskSourceBuilder_valueAsExpected() {
        TaskSourceBuilder tsb = new TaskSourceBuilder("test");
        TaskSource t1 = new TaskSource(tsb);
        TaskSource t2 = new TaskSource(tsb);
        assertEquals(t1, t2);
    }

    @Test
    void newBuilderCorrectness_useSameArgumentParameters_valueAsExpected() {
        String description = "test";

        TaskSourceBuilder tsb1 = new TaskSourceBuilder(description);
        TaskSourceBuilder tsb2 = TaskSource.newBuilder(description);

        assertEquals(tsb1.getDescription(), tsb2.getDescription());
        assertEquals(tsb1.getDueDate(), tsb2.getDueDate());
        assertEquals(tsb1.isDone(), tsb2.isDone());
        assertEquals(tsb1.getTags(), tsb2.getTags());
    }
}
