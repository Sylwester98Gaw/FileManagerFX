package script.actions;

import com.github.plushaze.traynotification.notification.Notifications;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import script.helpers.AddToList;
import script.helpers.ShowAlerts;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.commons.io.FileUtils;
import script.helpers.ShowTrayNotification;

import java.io.File;
import java.io.IOException;


public class Move {
    ShowTrayNotification showTrayNotification = new ShowTrayNotification();

    public void move(File from, File to, ProgressBar progressBar) {
        if (to != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Przenoszenie");
            alert.setHeaderText("Przenoszenie z " + from + " do " + to);
            alert.setContentText("Wszystko się zgadza ?");
            ButtonType okButton = new ButtonType("OK");
            ButtonType cancelButton = new ButtonType("Anuluj", ButtonType.CANCEL.getButtonData());
            alert.getButtonTypes().setAll(okButton, cancelButton);
            alert.showAndWait().ifPresent(response -> {
                if (response == okButton)
                    if (from.isDirectory()) {
                        Task<Void> task = new Task<>() {
                            @Override
                            protected Void call() throws Exception {
                                try {
                                    FileUtils.moveDirectoryToDirectory(from, to, true);
                                } catch (IOException e) {
                                    showTrayNotification.viewNotification("Przenoszenie nie udane", e.getMessage().toString(), Notifications.ERROR);
                                    throw new RuntimeException(e);
                                }
                                return null;
                            }
                        };
                        progressBar.setProgress(-1);
                        task.setOnSucceeded(event -> {
                            if (task.isDone()) {
                                progressBar.setProgress(1);
                                showTrayNotification.viewNotification("Przeniesiono", from + " do " + to, Notifications.SUCCESS);
                            }
                        });
                        task.setOnCancelled(event -> {
                            progressBar.setProgress(0.0);
                        });

                        task.setOnFailed(event -> {
                            progressBar.setProgress(0.0);
                        });
                        new Thread(task).start();
                    } else {
                        Task<Void> task = new Task<>() {
                            @Override
                            protected Void call() throws Exception {
                                try {
                                    FileUtils.moveFileToDirectory(from, to, true);
                                } catch (IOException e) {
                                    showTrayNotification.viewNotification("Przenoszenie nie udane", e.getMessage().toString(), Notifications.ERROR);
                                    throw new RuntimeException(e);
                                }
                                return null;
                            }
                        };
                        progressBar.setProgress(-1);
                        task.setOnSucceeded(event -> {
                            if (task.isDone()) {
                                progressBar.setProgress(1);
                                showTrayNotification.viewNotification("Przeniesiono", from + " do " + to, Notifications.SUCCESS);
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
            });
        }
    }
    public void moveAll(File to, ProgressBar progressBar) {
        if (to != null) {
            AddToList addToList = new AddToList();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Przenoszenie");
            alert.setHeaderText("Przenoszenie z " + AddToList.list.toString() + " do " + to);
            alert.setContentText("Wszystko się zgadza ?");
            ButtonType okButton = new ButtonType("OK");
            ButtonType cancelButton = new ButtonType("Anuluj", ButtonType.CANCEL.getButtonData());
            alert.getButtonTypes().setAll(okButton, cancelButton);
            alert.showAndWait().ifPresent(response -> {
                if (response == okButton) {
                    for (File files : AddToList.list) {
                        if (files.isDirectory()) {
                            Task<Void> task = new Task<>() {
                                @Override
                                protected Void call() throws Exception {
                                    try {
                                        FileUtils.moveDirectoryToDirectory(files, to, true);
                                    } catch (IOException e) {
                                        showTrayNotification.viewNotification("Przenoszenie nie udane", e.getMessage().toString(), Notifications.ERROR);
                                        throw new RuntimeException(e);
                                    }
                                    return null;
                                }
                            };
                            progressBar.setProgress(-1);
                            task.setOnSucceeded(event -> {
                                if (task.isDone()) {
                                    progressBar.setProgress(1);
                                    showTrayNotification.viewNotification("Przeniesiono", "Wykonano wiele zadań", Notifications.SUCCESS);
                                    addToList.removeAll();
                                }
                            });
                            task.setOnCancelled(event -> {
                                progressBar.setProgress(0.0);
                            });

                            task.setOnFailed(event -> {
                                progressBar.setProgress(0.0);
                            });
                            new Thread(task).start();
                        } else {
                            Task<Void> task = new Task<>() {
                                @Override
                                protected Void call() throws Exception {
                                    try {
                                        FileUtils.moveFileToDirectory(files, to, true);
                                    } catch (IOException e) {
                                        showTrayNotification.viewNotification("Przenoszenie nie udane", e.getMessage().toString(), Notifications.ERROR);
                                        throw new RuntimeException(e);
                                    }
                                    return null;
                                }
                            };
                            progressBar.setProgress(-1);
                            task.setOnSucceeded(event -> {
                                if (task.isDone()) {
                                    progressBar.setProgress(1);
                                    showTrayNotification.viewNotification("Przeniesiono", "Wykonano wiele zadań", Notifications.SUCCESS);
                                    addToList.removeAll();
                                }
                            });
                            task.setOnCancelled(event -> {
                                progressBar.setProgress(0.0);
                                addToList.removeAll();
                            });
                            task.setOnFailed(event -> {
                                progressBar.setProgress(0.0);
                                addToList.removeAll();
                            });
                            new Thread(task).start();
                        }
                    }
                }
            });
        }
    }
}
