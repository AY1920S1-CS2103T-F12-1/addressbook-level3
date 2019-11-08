package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DateTime;
import seedu.address.model.ModelData;
import seedu.address.model.ModelManager;
import seedu.address.model.events.EventSource;

class UndoRedoManagerTest {

    @Test
    void undo_nothingToUndo_exceptionThrown() {
        ModelManager modelManager = new ModelManager();
        UndoRedoManager undoRedoManager = new UndoRedoManager(modelManager);
        undoRedoManager.start();
        assertThrows(CommandException.class, undoRedoManager::undo);
    }

    @Test
    void undo_nothingToRedo_exceptionThrown() {
        ModelManager modelManager = new ModelManager();
        UndoRedoManager undoRedoManager = new UndoRedoManager(modelManager);
        undoRedoManager.start();
        assertThrows(CommandException.class, undoRedoManager::redo);

        String description = "description";
        DateTime start = DateTime.now();
        List<EventSource> events = new ArrayList<>(modelManager.getEvents());
        events.add(EventSource.newBuilder(description, start).build());
        modelManager.setModelData(new ModelData(events, modelManager.getTasks()));
        assertThrows(CommandException.class, undoRedoManager::redo);
    }

    @Test
    void onModelListChange_modelListChanged_writtenCorrectly() {
        ModelManager modelManager = new ModelManager();
        UndoRedoManager undoRedoManager = new UndoRedoManager(modelManager);
        undoRedoManager.start();
        assertEquals(undoRedoManager.getUndoStateList().size(), 1);
        assertEquals(undoRedoManager.getUndoIndex(), 0);
        // Test whether a deep-copied version of ModelData in ModelManager has been stored to UndoRedoManager,
        // and not the original object itself
        assertTrue(undoRedoManager.getUndoStateList().get(0) != modelManager.getModel());
        assertEquals(undoRedoManager.getUndoStateList().get(0), modelManager.getModel());

        String description = "description";
        DateTime start = DateTime.now();
        List<EventSource> events = new ArrayList<>(modelManager.getEvents());
        events.add(EventSource.newBuilder(description, start).build());
        modelManager.setModelData(new ModelData(events, modelManager.getTasks()));
        assertEquals(undoRedoManager.getUndoStateList().size(), 2);
        assertEquals(undoRedoManager.getUndoIndex(), 1);

        // ModelData in ModelManager should no longer match the previous version in UndoRedoManager
        assertFalse(undoRedoManager.getUndoStateList().get(0).equals(modelManager.getModel()));
        // Test whether a deep-copied version of ModelData in ModelManager has been stored to UndoRedoManager,
        // and not the original object itself
        assertTrue(undoRedoManager.getUndoStateList().get(1) != modelManager.getModel());
        assertEquals(undoRedoManager.getUndoStateList().get(1), modelManager.getModel());
    }

    @Test
    void undo_validUndo_writtenCorrectly() {
        ModelManager modelManager = new ModelManager();
        UndoRedoManager undoRedoManager = new UndoRedoManager(modelManager);
        undoRedoManager.start();

        String description = "description";
        DateTime start = DateTime.now();
        List<EventSource> events = new ArrayList<>(modelManager.getEvents());
        events.add(EventSource.newBuilder(description, start).build());
        modelManager.setModelData(new ModelData(events, modelManager.getTasks()));

        assertDoesNotThrow(undoRedoManager::undo);
        assertEquals(undoRedoManager.getUndoIndex(), 0);
        // ModelData in ModelManager should no longer match the latest version in UndoRedoManager
        assertFalse(undoRedoManager.getUndoStateList().get(1).equals(modelManager.getModel()));
        // Test whether ModelData in ModelManager has been updated to match the previous version in UndoRedoManager
        // (Value-equality but not reference equality)
        assertTrue(undoRedoManager.getUndoStateList().get(0) != modelManager.getModel());
        assertEquals(undoRedoManager.getUndoStateList().get(0), modelManager.getModel());
    }

    @Test
    void undo_validRedo_writtenCorrectly() {
        ModelManager modelManager = new ModelManager();
        UndoRedoManager undoRedoManager = new UndoRedoManager(modelManager);
        undoRedoManager.start();

        String description = "description";
        DateTime start = DateTime.now();
        List<EventSource> events = new ArrayList<>(modelManager.getEvents());
        events.add(EventSource.newBuilder(description, start).build());
        modelManager.setModelData(new ModelData(events, modelManager.getTasks()));

        assertDoesNotThrow(undoRedoManager::undo);

        assertDoesNotThrow(undoRedoManager::redo);
        assertEquals(undoRedoManager.getUndoIndex(), 1);
        // ModelData in ModelManager should no longer match the previous version in UndoRedoManager
        assertFalse(undoRedoManager.getUndoStateList().get(0).equals(modelManager.getModel()));
        // Test whether ModelData in ModelManager has been updated to match the previous version in UndoRedoManager
        // (Value-equality but not reference equality)
        assertTrue(undoRedoManager.getUndoStateList().get(1) != modelManager.getModel());
        assertEquals(undoRedoManager.getUndoStateList().get(1), modelManager.getModel());
    }

}
