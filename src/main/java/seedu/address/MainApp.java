package seedu.address;

import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.logic.CommandManager;
import seedu.address.logic.NotificationManager;
import seedu.address.logic.UiManager;
import seedu.address.logic.commands.AddEventCommand;
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
import seedu.address.model.undo.UndoRedoManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    private static final Version VERSION = new Version(0, 6, 0, true);
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    private static final String COMMAND_ADD_EVENT = "add_event";
    private static final String COMMAND_DELETE_EVENT = "delete_event";
    private static final String COMMAND_EDIT_EVENT = "edit_event";
    private static final String COMMAND_UNDO = "undo";
    private static final String COMMAND_REDO = "redo";
    private static final String COMMAND_IMPORT_ICS = "import";
    private static final String COMMAND_EXPORT_ICS = "export";
    private static final String COMMAND_NOTIFICATION_OFF = "notif_off";
    private static final String COMMAND_NOTIFICATION_ON = "notif_on";
    private static final String COMMAND_DAY_VIEW = "day";
    private static final String COMMAND_WEEK_VIEW = "week";
    private static final String COMMAND_MONTH_VIEW = "month";

    private UiManager uiManager;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing AddressBook ]===========================");
        super.init();

        CommandManager commandManager = new CommandManager();
        ModelManager modelManager = new ModelManager();
        NotificationManager notificationManager = new NotificationManager(modelManager);
        uiManager = new UiManager();
        UndoRedoManager undoRedoManager = new UndoRedoManager();

        registerCommands(commandManager, modelManager, undoRedoManager, notificationManager, uiManager);

        addListeners(commandManager, modelManager, undoRedoManager, uiManager);
    }


    /**
     * Registers each of the commands to the CommandManager.
     *
     * @param commandManager The CommandManager to which commands will be registered.
     * @param modelManager The ModelManager to be used with a command.
     * @param undoRedoManager The UndoRedoManager to be used with a command.
     * @param notificationManager The NotificationManager to be used with a command.
     * @param uiManager The UiManager to be used with a command.
     */
    private void registerCommands(CommandManager commandManager, ModelManager modelManager,
                                  UndoRedoManager undoRedoManager, NotificationManager notificationManager,
                                  UiManager uiManager) {

        commandManager.addCommand(COMMAND_ADD_EVENT, () -> AddEventCommand.newBuilder(modelManager));
        commandManager.addCommand(COMMAND_DELETE_EVENT, () -> DeleteEventCommand.newBuilder(modelManager));
        commandManager.addCommand(COMMAND_EDIT_EVENT, () -> EditEventCommand.newBuilder(modelManager));
        commandManager.addCommand(COMMAND_UNDO, () -> UndoCommand.newBuilder(undoRedoManager));
        commandManager.addCommand(COMMAND_REDO, () -> RedoCommand.newBuilder(undoRedoManager));
        commandManager.addCommand(COMMAND_IMPORT_ICS, () -> ImportIcsCommand.newBuilder(modelManager));
        commandManager.addCommand(COMMAND_EXPORT_ICS, () -> ExportIcsCommand.newBuilder(modelManager));
        commandManager
                .addCommand(COMMAND_NOTIFICATION_OFF, () -> NotificationOffCommand.newBuilder(notificationManager));
        commandManager.addCommand(COMMAND_NOTIFICATION_ON, () -> NotificationOnCommand.newBuilder(notificationManager));
        commandManager.addCommand(COMMAND_DAY_VIEW, () -> DayViewCommand.newBuilder(uiManager));
        commandManager.addCommand(COMMAND_WEEK_VIEW, () -> WeekViewCommand.newBuilder(uiManager));
        commandManager.addCommand(COMMAND_MONTH_VIEW, () -> MonthViewCommand.newBuilder(uiManager));
    }


    /**
     * Registers each listener to the appropriate manager.
     *
     * @param commandManager The CommandManager to be used either as a listener or a notifier.
     * @param modelManager The ModelManager to be used either as a listener or a notifier.
     * @param undoRedoManager The UndoRedoManager to be used either as a listener or a notifier.
     * @param uiManager The UiManager to be used either as a listener or a notifier.
     */
    private void addListeners(CommandManager commandManager, ModelManager modelManager,
                                   UndoRedoManager undoRedoManager, UiManager uiManager) {

        commandManager.addUserOutputListener(uiManager);

        modelManager.addEventListListener(uiManager);
        modelManager.addEventListListener(undoRedoManager);

        uiManager.addCommandInputListener(commandManager);

        undoRedoManager.addUndoRedoListener(modelManager);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting AddressBook " + MainApp.VERSION);
        uiManager.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Address Book ] =============================");
        System.exit(0);
    }
}
