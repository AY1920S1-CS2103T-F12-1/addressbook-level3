package seedu.address.notification;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.NotificationManager;

/**
 * The Listener responding to a Notification being clicked.
 */
public class NotificationClickActionListener implements ActionListener {
    private static final Logger logger = LogsCenter.getLogger(NotificationManager.class);

    /**
     * This Listener's reaction to an ActionEvent it is listening to being called.
     *     Specifically for this class, this is called whenever a notification is clicked on.
     *
     * @param e The ActionEvent that this Listener is listening to.
     */
    public void actionPerformed(ActionEvent e) {
        /*
         This is currently undefined behaviour for the first milestone.
         In the future, as things get more developed, this should be replaced with opening up the app.
         */
        logger.info("Notification Clicked");
    }
}
