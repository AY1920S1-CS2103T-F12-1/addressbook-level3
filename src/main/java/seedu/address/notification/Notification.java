package seedu.address.notification;

import seedu.address.model.events.ReadOnlyEventList;

/**
 * The interface for the Notification component of the app.
 */
public interface Notification {
    /**
     * Updates the queue of notifications to be posted.
     *
     * @param readOnlyEventList The list of events for which notifications should be posted
     */
    void updateNotificationQueue(ReadOnlyEventList readOnlyEventList);

    /**
     * Interrupts any ongoing threads so that the program may shutdown gracefully.
     */
    void shutDown();
}
