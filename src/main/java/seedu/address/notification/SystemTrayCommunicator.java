package seedu.address.notification;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

/**
 * Controls interactions between the main program and the System Tray.
 */
public class SystemTrayCommunicator {
    private static final String TRAY_ICON_NAME = "Horo";
    private static final String TRAY_ICON_IMAGE_PATH = "system_tray_icon.png";

    private static TrayIcon trayIcon;
    private static boolean systemTrayIsSupported;

    /**
     * Sets up the System Tray app, if applicable.
     */
    public void initialise() {
        systemTrayIsSupported = SystemTray.isSupported();
        
        if(systemTrayIsSupported) {
            SystemTray tray = SystemTray.getSystemTray();
            trayIcon = getTrayIcon();

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println(e);
            }
        }
    }

    /**
     * Posts a new notification to the System Tray.
     * 
     * @param name The name of the notification.
     * @param description The description of the notification.
     */
    public void postNewNotification(String name, String description) {

        if(systemTrayIsSupported) {
            trayIcon.displayMessage(name, description, MessageType.INFO);
        }
    }

    /**
     * Creates a new icon on the System Tray, and returns its reference.
     * 
     * @return The reference to a new System Tray icon.
     */
    private static TrayIcon getTrayIcon() {
        TrayIcon trayIcon = new TrayIcon(getImage(), TRAY_ICON_NAME, getPopupMenu());
        trayIcon.addActionListener(getNotificationClickActionListener());

        return trayIcon;
    }

    /**
     * Locates and returns the image to be used as the Tray Icon.
     * 
     * @return the image to be used as the Tray Icon.
     */
    private static Image getImage() {
        return Toolkit.getDefaultToolkit()
            .getImage(System.getProperty("user.dir") + "/" + TRAY_ICON_IMAGE_PATH);
    }
    
    /**
     * Creates and returns the PopupMenu designed for this app.
     * 
     * @return The PopupMenu designed for the System Tray app.
     */
    private static PopupMenu getPopupMenu() {
        PopupMenu popup = new PopupMenu();

        // popup menu items
        MenuItem aboutItem = new MenuItem("Hi this is Horo.");

        //Add components to pop-up menu
        popup.add(aboutItem);

        return popup;
    }

    /**
     * Creates and returns an instance of the ClickActionListener used for this app.
     *     The listener will be called whenever the user clicks on a notification.
     * 
     * @return An instance of the ClickActionListener used for this app.
     */
    private static NotificationClickActionListener getNotificationClickActionListener() {
        return new NotificationClickActionListener();
    }
}
