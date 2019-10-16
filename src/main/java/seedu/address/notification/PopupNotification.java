package seedu.address.notification;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.events.DateTime;

/**
 * Represents a notification to be posted to the system tray.
 */
public class PopupNotification {
    public final String name;
    public final String description;
    public final DateTime timeToNotify;

    /**
     * Creates a new PopupNotification with the appropriate name, description and time to be posted.
     *
     * @param name The name to show up when the notification is posted
     * @param description The description to accompany the name when the notification is posted
     * @param timeToNotify The time at which the notification should be posted
     */
    public PopupNotification (String name, String description, DateTime timeToNotify) {
        requireAllNonNull(name, description, timeToNotify);
        this.name = name;
        this.description = description;
        this.timeToNotify = timeToNotify;
    }
}
