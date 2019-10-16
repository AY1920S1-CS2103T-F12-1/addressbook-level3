package seedu.address.notification;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.NotificationManager;
import seedu.address.model.events.DateTime;
import seedu.address.model.events.EventSource;
import seedu.address.model.events.ReadOnlyEventList;


/**
 * A Thread running parallel to the main program that posts notifications to the user's System Tray.
 */
public class EventNotificationThread extends Thread {
    private static final Logger logger = LogsCenter.getLogger(NotificationManager.class);

    private SystemTrayCommunicator systemTrayCommunicator;
    private Queue<PopupNotification> notificationQueue = new ArrayDeque<>();

    /**
     * Creates a new EventNotificationThread. This requires a SystemTrayCommunicator through which notifications
     *     can be posted, as well as a ReadOnlyEventList from which notifications can be generated.
     *
     * @param systemTrayCommunicator The SystemTrayCommunicator through which each notification will be posted.
     * @param readOnlyEventList A read-only copy of the EventList from which notifications will be generated.
     */
    public EventNotificationThread(SystemTrayCommunicator systemTrayCommunicator,
                                   ReadOnlyEventList readOnlyEventList) {
        requireAllNonNull(systemTrayCommunicator, readOnlyEventList);
        this.systemTrayCommunicator = systemTrayCommunicator;

        for (EventSource eventSource : readOnlyEventList.getReadOnlyList()) {
            String name = eventSource.getDescription();
            DateTime eventDateTime = eventSource.getStartDateTime();

            PopupNotification popupNotif = new PopupNotification(name, eventDateTime.toString(), eventDateTime);
            notificationQueue.offer(popupNotif);
        }
    }

    /**
     * Causes this thread to start running. It will do so until no notifications are left to be posted,
     *     or if it is otherwise interrupted.
     */
    @Override
    public void run() {
        try {
            while (!notificationQueue.isEmpty()) {
                PopupNotification popupNotification = notificationQueue.poll();
                sleepUntilNotification(popupNotification);
                systemTrayCommunicator.postNewNotification(popupNotification.name, popupNotification.description);
            }

        } catch (InterruptedException e) {
            logger.info("EventNotificationThread successfully interrupted.");
        }
    }

    /**
     * Sleeps this thread until the time for the notification to be posted.
     *
     * @param popupNotification The PopupNotification to be posted to be posted next.
     * @exception InterruptedException If the thread is interrupted.
     */
    private void sleepUntilNotification(PopupNotification popupNotification) throws InterruptedException {
        long msecsNowToPopupNotification = DateTime.now().msecsTimeUntil(popupNotification.timeToNotify);

        if (msecsNowToPopupNotification > 0) {
            Thread.sleep(msecsNowToPopupNotification);
        }
    }
}
