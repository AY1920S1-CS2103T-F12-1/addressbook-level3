package seedu.address.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.listeners.CommandInputListener;
import seedu.address.model.events.EventSource;
import seedu.address.model.listeners.EventListListener;
import seedu.address.ui.ColorTheme;
import seedu.address.ui.MainWindow;
import seedu.address.ui.Ui;
import seedu.address.ui.UserOutput;
import seedu.address.ui.listeners.UserOutputListener;

/**
 * The manager of the UI component.
 * Responsible for creating and destroying the graphical ui.
 */
public class UiManager implements Ui, UserOutputListener, EventListListener {

    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/address_book_32.png";

    private MainWindow mainWindow;
    private List<CommandInputListener> uiListeners;

    public UiManager() {
        this.uiListeners = new ArrayList<>();
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = new MainWindow(primaryStage, commandInput -> {
                // TODO: Temporary command
                if (commandInput.equals("calendar")) {
                    this.mainWindow.viewCalendar();
                } else if (commandInput.equals("list")) {
                    this.mainWindow.viewList();
                } else if (commandInput.equals("log")) {
                    this.mainWindow.viewLog();
                } else if (commandInput.equals("week 2")) {
                    this.mainWindow.viewWeek(1, 11, 2019);
                } else if (commandInput.equals("month 2")) {
                    this.mainWindow.viewMonth(11, 2019);
                } else if (commandInput.equals("calendar 11/2019")) {
                    // No need for day
                    this.mainWindow.changeCalendarScreenDate(11, 2019);
                } else {
                    // Notify listeners of new command input.
                    this.uiListeners.forEach(listener -> listener.onCommandInput(commandInput));
                }
            });
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    public void addCommandInputListener(CommandInputListener listener) {
        this.uiListeners.add(listener);
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    private void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    /**
     * Shows an alert dialog on {@code owner} with the given parameters.
     * This method only returns after the user has closed the alert dialog.
     */
    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        alert.showAndWait();
    }

    /**
     * Shows an error alert dialog with {@code title} and error message, {@code e},
     * and exits the application after the user has closed the alert dialog.
     */
    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    public void viewDay(Integer day, Integer month, Integer year) {
        mainWindow.viewDay(day, month, year);
    }

    public void viewWeek(Integer week, Integer month, Integer year) {
        mainWindow.viewWeek(week, month, year);
    }

    public void viewMonth(Integer month, Integer year) {
        mainWindow.viewMonth(month, year);
    }

    @Override
    public void onEventListChange(List<EventSource> events) {
        this.mainWindow.onEventListChange(events);
    }

    @Override
    public void onUserOutput(UserOutput output, ColorTheme result) {
        this.mainWindow.onUserOutput(output, result);
    }
}
