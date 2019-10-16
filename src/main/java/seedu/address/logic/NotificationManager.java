package seedu.address.logic;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.events.ReadOnlyEventList;
import seedu.address.notification.EventNotificationThread;
import seedu.address.notification.Notification;
import seedu.address.notification.SystemTrayCommunicator;


/**
 * The implementing class of the Notification interface.
 */
public class NotificationManager implements Notification {
    private static final Logger logger = LogsCenter.getLogger(NotificationManager.class);

    private EventNotificationThread currentEventNotificationThread;
    private SystemTrayCommunicator systemTrayCommunicator;

    /**
     * Generates a new NotificationManager.
     */
    public NotificationManager() {
        systemTrayCommunicator = new SystemTrayCommunicator();
        systemTrayCommunicator.initialise();
    }

    /**
     * Updates the queue of notifications to be posted.
     *
     * @param readOnlyEventList The list of events for which notifications should be posted
     */
    public void updateNotificationQueue(ReadOnlyEventList readOnlyEventList) {
        interruptExistingThread();
        createNewThread(readOnlyEventList);
    }

    /**
     * Interrupts any ongoing threads so that the program may shutdown gracefully.
     */
    public void shutDown() {
        interruptExistingThread();
    }

    /**
     * Interrupts the currentEventNotificationThread if it exists, and does nothing otherwise.
     */
    private void interruptExistingThread () {
        if (currentEventNotificationThread != null) {
            logger.info("Attempting to interrupt current EventNotificationThread.");
            currentEventNotificationThread.interrupt();
        }
    }

    /**
     * Generates and starts a new EventNotificationThread.
     *
     * @param readOnlyEventList The ReadOnlyEventList through which notifications can be generated.
     */
    private void createNewThread(ReadOnlyEventList readOnlyEventList) {
        currentEventNotificationThread = new EventNotificationThread(systemTrayCommunicator, readOnlyEventList);
        currentEventNotificationThread.setDaemon(true);
        currentEventNotificationThread.start();
    }
}
