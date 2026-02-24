package script.actions;

import com.github.plushaze.traynotification.notification.Notification;
import com.github.plushaze.traynotification.notification.Notifications;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import org.apache.commons.io.FileUtils;
import script.helpers.ShowAlerts;

import java.io.*;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import script.helpers.ShowTrayNotification;

public class Copy {
    ShowTrayNotification showTrayNotification = new ShowTrayNotification();

    public void copy(File from, File to, ProgressBar progressBar) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Kopiowanie");
        alert.setHeaderText("Kopiowanie z " + from + " do " + to);
        alert.setContentText("Wszystko się zgadza ?");
        ButtonType okButton = new ButtonType("OK");
        ButtonType cancelButton = new ButtonType("Anuluj", ButtonType.CANCEL.getButtonData());
        alert.getButtonTypes().setAll(okButton, cancelButton);
        alert.showAndWait().ifPresent(response -> {
            if (response == okButton) {
                if (from.isDirectory()) { // coping dirs
                    Task<Void> task = new Task<Void>() {
                        @Override
                        protected Void call() {
                            try {
                                FileUtils.copyDirectoryToDirectory(from, to);
                            } catch (IOException e) {
                                showTrayNotification.viewNotification("Błąd kopiowania", e.getMessage().toString(), Notifications.ERROR);
                                throw new RuntimeException(e);
                            }
                            return null;
                        }
                    };
                    progressBar.setProgress(-1);
                    task.setOnSucceeded(event -> {
                        if (task.isDone()) {
                            progressBar.setProgress(1);
                            showTrayNotification.viewNotification("Skopiowano", from+" do "+to, Notifications.SUCCESS);
                        }
                    });
                    task.setOnCancelled(event -> {
                        progressBar.setProgress(0.0);
                    });
                    task.setOnFailed(event -> {
                        progressBar.setProgress(0.0);
                    });
                    new Thread(task).start();
                } else {// coping files
                    Task<Void> task = new Task<Void>() {
                        @Override
                        protected Void call() {
                            try {
                                FileUtils.copyFileToDirectory(from, to);
                            } catch (IOException e) {
                                showTrayNotification.viewNotification("Błąd kopiowania", e.getMessage().toString(), Notifications.ERROR);
                                throw new RuntimeException(e);
                            }
                            return null;
                        }
                    };
                    progressBar.setProgress(-1);
                    task.setOnSucceeded(event -> {
                        if (task.isDone()) {
                            progressBar.setProgress(1);
                            showTrayNotification.viewNotification("Skopiowano", from+" do "+to, Notifications.SUCCESS);
                        }
                    });

                    task.setOnCancelled(event -> {
                        progressBar.setProgress(0.0);
                    });

                    task.setOnFailed(event -> {
                        progressBar.setProgress(0.0);
                    });
                    new Thread(task).start();
                }

            }
        });
    }
}
