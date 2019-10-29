package seedu.address;

import java.nio.file.Paths;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.logic.CommandManager;
import seedu.address.logic.NotificationManager;
import seedu.address.logic.StorageManager;
import seedu.address.logic.UiManager;
import seedu.address.logic.UndoRedoManager;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.DayViewCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.ExportIcsCommand;
import seedu.address.logic.commands.ImportIcsCommand;
import seedu.address.logic.commands.MonthViewCommand;
import seedu.address.logic.commands.NotificationOffCommand;
import seedu.address.logic.commands.NotificationOnCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.WeekViewCommand;
import seedu.address.model.ModelManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    private static final Version VERSION = new Version(0, 6, 0, true);
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    private static final String COMMAND_ADD_EVENT = "add_event";
    private static final String COMMAND_DELETE_EVENT = "delete_event";
    private static final String COMMAND_EDIT_EVENT = "edit_event";
    private static final String COMMAND_ADD_TASK = "add_task";
    private static final String COMMAND_UNDO = "undo";
    private static final String COMMAND_REDO = "redo";
    private static final String COMMAND_IMPORT_ICS = "import";
    private static final String COMMAND_EXPORT_ICS = "export";
    private static final String COMMAND_NOTIFICATION_OFF = "notif_off";
    private static final String COMMAND_NOTIFICATION_ON = "notif_on";
    private static final String COMMAND_DAY_VIEW = "day";
    private static final String COMMAND_WEEK_VIEW = "week";
    private static final String COMMAND_MONTH_VIEW = "month";

    private ModelManager modelManager;
    private UiManager uiManager;
    private StorageManager storageManager;
    private UndoRedoManager undoRedoManager;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing AddressBook ]===========================");
        super.init();

        CommandManager commandManager = new CommandManager();
        modelManager = new ModelManager();
        NotificationManager notificationManager = new NotificationManager(modelManager);
        storageManager = new StorageManager();
        uiManager = new UiManager();
        undoRedoManager = new UndoRedoManager();

        // Register commands to CommandManager.
        commandManager.addCommand(COMMAND_ADD_EVENT, () -> AddEventCommand.newBuilder(modelManager));
        commandManager.addCommand(COMMAND_DELETE_EVENT, () -> DeleteEventCommand.newBuilder(modelManager));
        commandManager.addCommand(COMMAND_EDIT_EVENT, () -> EditEventCommand.newBuilder(modelManager));
        commandManager.addCommand(COMMAND_ADD_TASK, () -> AddTaskCommand.newBuilder(modelManager));
        commandManager.addCommand(COMMAND_UNDO, () -> UndoCommand.newBuilder(undoRedoManager));
        commandManager.addCommand(COMMAND_REDO, () -> RedoCommand.newBuilder(undoRedoManager));
        commandManager.addCommand(COMMAND_IMPORT_ICS, () -> ImportIcsCommand.newBuilder(modelManager));
        commandManager.addCommand(COMMAND_EXPORT_ICS, () -> ExportIcsCommand.newBuilder(modelManager));
        commandManager.addCommand(COMMAND_NOTIFICATION_OFF, () ->
            NotificationOffCommand.newBuilder(notificationManager));
        commandManager.addCommand(COMMAND_NOTIFICATION_ON, () ->
            NotificationOnCommand.newBuilder(notificationManager));
        commandManager.addCommand(COMMAND_DAY_VIEW, () -> DayViewCommand.newBuilder(uiManager));
        commandManager.addCommand(COMMAND_WEEK_VIEW, () -> WeekViewCommand.newBuilder(uiManager));
        commandManager.addCommand(COMMAND_MONTH_VIEW, () -> MonthViewCommand.newBuilder(uiManager));

        storageManager.setEventsFile(Paths.get("data", "events.json"));
        storageManager.setTasksFile(Paths.get("data", "tasks.json"));


        // Add Listeners
        commandManager.addUserOutputListener(uiManager);

        modelManager.addEventListListener(uiManager);
        modelManager.addTaskListListener(uiManager);
        modelManager.addModelListListener(storageManager);
        modelManager.addModelListListener(undoRedoManager);

        storageManager.addModelResetListener(modelManager);

        uiManager.addCommandInputListener(commandManager);
        undoRedoManager.addModelResetListener(modelManager);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting AddressBook " + MainApp.VERSION);

        // Start UiManager
        uiManager.start(primaryStage);

        // Load Model from storage
        storageManager.load();

        // Start UndoRedoManager
        undoRedoManager.start(modelManager.getModelList());
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Address Book ] =============================");
        System.exit(0);
    }
}
