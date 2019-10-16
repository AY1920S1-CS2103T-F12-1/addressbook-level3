package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

/**
 * An Ui that stores the logged feedback from the program to the user.
 */
public class LogPanel extends UiPart<Region> {

    private static final String FXML = "LogPanel.fxml";

    @FXML
    private VBox boxLog;

    @FXML
    private ScrollPane scrollLog;

    /**
     * Constructor for the LogPanel. Sets the size of the LogPanel to the user's screen size.
     */
    public LogPanel() {
        super(FXML);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenHeight = primaryScreenBounds.getHeight();
        double screenWidth = primaryScreenBounds.getWidth();

        boxLog.setPrefHeight(screenHeight - screenHeight / 5);
        boxLog.setPrefWidth(screenWidth / 2);
    }

    /**
     * Creates an instance of LogBox.
     * @param feedbackToUser To provide the Log Box the feedback to the user.
     */
    public void createLogBox(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        LogBox logBox = new LogBox(feedbackToUser);
        boxLog.getChildren().add(logBox.getRoot());
    }

}
