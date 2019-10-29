package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command!";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format!";
    public static final String MESSAGE_REQUIRED_COMMAND_ARGUMENT = "Argument '%s' is required!";

    public static final String MESSAGE_INVALID_DATE_TIME = "Invalid date time! Please enter in '%s' format.";
    public static final String MESSAGE_INVALID_CALENDAR_DATE = "Invalid calendar date! Please enter in '%s' format.";
    public static final String MESSAGE_INVALID_INDEX = "Invalid index! Please enter an integer.";
    public static final String MESSAGE_INVALID_EVENT_INDEX = "Invalid index! Event with index '%s' does not exist.";
    public static final String MESSAGE_INVALID_TASK_INDEX = "Invalid index! Task with index '%s' does not exist.";

    public static final String MESSAGE_NOTHING_TO_UNDO = "There is nothing to undo!";
    public static final String MESSAGE_NOTHING_TO_REDO = "There is nothing to redo!";

    public static final String MESSAGE_ADD_EVENT_SUCCESS = "New event added: %s";
    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Events deleted: %s";
    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Events edited: %s";
    public static final String MESSAGE_ADD_TASK_SUCCESS = "New task added: %s";
    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Tasks deleted: %s";
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Tasks edited: %s";
    public static final String MESSAGE_UNDO_SUCCESS = "Previous command has been undone!";
    public static final String MESSAGE_REDO_SUCCESS = "Previous undone command has been redone!";
    public static final String MESSAGE_IMPORT_ICS_SUCCESS = "ICS file at %s has been successfully imported!";
    public static final String MESSAGE_EXPORT_ICS_SUCCESS = "Horo has successfully been exported at the filepath: %s";

    public static final String MESSAGE_DAY_VIEW_SUCCESS = "Changed Day to: %s";
    public static final String MESSAGE_WEEK_VIEW_SUCCESS = "Changed Week to week of: %s";
    public static final String MESSAGE_MONTH_VIEW_SUCCESS = "Changed Month to: %s";

    public static final String MESSAGE_DELETE_EVENT_EMPTY = "Please fill in at least INDEXES or TAGS.";
    public static final String MESSAGE_DELETE_EVENT_FAILURE = "No matching events.";
    public static final String MESSAGE_DELETE_TASK_EMPTY = "Please fill in at least INDEXES or TAGS.";
    public static final String MESSAGE_DELETE_TASK_FAILURE = "No matching tasks.";

    public static final String MESSAGE_NOTIFICATION_OFF = "Notifications have been turned off.";
    public static final String MESSAGE_NOTIFICATION_ON = "Notifications have been turned on.";
}
