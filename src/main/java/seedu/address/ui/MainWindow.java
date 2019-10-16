package seedu.address.ui;

import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.events.EventSource;
import seedu.address.model.listeners.EventListListener;
import seedu.address.ui.listeners.UserOutputListener;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> implements UserOutputListener, EventListListener {
    public static final int WIDTH_PADDING = 20;
    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Consumer<String> onCommandInput;
    private UiParser uiParser;

    // Independent Ui parts residing in this Ui container
    private EventListPanel eventListPanel;
    private CalendarPanel calendarPanel;
    private LogPanel logPanel;
    private HelpWindow helpWindow;

    private boolean calendarMode;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane viewPanelPlaceholder;

    @FXML
    private StackPane logPanelPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private GridPane gridManager;

    @FXML
    private VBox vBoxPane;

    @FXML
    private Label chatLog;

    @FXML
    private Label list;

    public MainWindow(Stage primaryStage, Consumer<String> onCommandInput) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.onCommandInput = onCommandInput;
        this.uiParser = new UiParser();
        this.calendarMode = false;

        setWindowDefaultSize(new GuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillInnerParts() {
        calendarPanel = new CalendarPanel(uiParser);
        eventListPanel = new EventListPanel(uiParser);
        viewPanelPlaceholder.getChildren().add(calendarPanel.getRoot());
        viewPanelPlaceholder.getChildren().add(eventListPanel.getRoot());
        calendarPanel.getRoot().setVisible(false);

        logPanel = new LogPanel();
        logPanelPlaceholder.getChildren().add(logPanel.getRoot());

        // StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        // statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this.onCommandInput);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        editInnerParts();
    }

    /**
     * Edits the size of the nodes based on the user's screen size.
     */
    private void editInnerParts() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenHeight = primaryScreenBounds.getHeight();
        double screenWidth = primaryScreenBounds.getWidth();

        logPanelPlaceholder.setPrefWidth(screenWidth);
        viewPanelPlaceholder.setPrefWidth(screenWidth);

        // Set the stage width and height
        primaryStage.setMaxWidth(screenWidth);
        primaryStage.setMaxHeight(screenHeight);
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    public void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        // logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Temporary method to toggle the view panel on the right.
     */
    public void toggleView() {
        if (calendarMode) {
            calendarMode = false;
            calendarPanel.getRoot().setVisible(false);
            eventListPanel.getRoot().setVisible(true);
        } else {
            calendarMode = true;
            eventListPanel.getRoot().setVisible(false);
            calendarPanel.getRoot().setVisible(true);
        }
    }

    @Override
    public void onEventListChange(List<EventSource> events) {
        this.eventListPanel.onEventListChange(events);
        this.calendarPanel.onEventListChange(events);
    }

    @Override
    public void onUserOutput(UserOutput output) {
        this.logPanel.createLogBox(output.toString());
    }
}
