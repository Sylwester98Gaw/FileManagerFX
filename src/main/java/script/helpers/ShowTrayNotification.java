/*
 * Copyright (c) 2025. This code is my creation, my passion, and my contribution to the community. It's open for all to use, learn, and grow from. I sign it with my name proudly, committed to supporting freedom and collaboration in the world of programming.
 */

package script.helpers;

import com.github.plushaze.traynotification.animations.Animations;
import com.github.plushaze.traynotification.notification.Notification;
import com.github.plushaze.traynotification.notification.TrayNotification;
import javafx.util.Duration;

// dodaj to jako informacje mniej istotne zamiast klikania ok ok ok hehe
public class ShowTrayNotification {
    public ShowTrayNotification viewNotification(String title, String message, Notification notification){
        TrayNotification tray = new TrayNotification();
        tray.setAnimation(Animations.POPUP);
        tray.setTitle(title);
        tray.setMessage(message);
        tray.setNotification(notification);
        tray.showAndDismiss(Duration.seconds(2));
        return null;
    }
}
