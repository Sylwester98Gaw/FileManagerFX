package script.actions;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import org.apache.commons.io.FileUtils;
import script.helpers.ShowAlerts;

import java.io.*;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Copy {
    ShowAlerts showAlerts = new ShowAlerts();

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
                                showAlerts.Alert(Alert.AlertType.ERROR, "Kopiowanie nie udane ", String.valueOf(e), "Błąd");
                                throw new RuntimeException(e);
                            }
                            return null;
                        }
                    };
                    progressBar.progressProperty().bind(task.progressProperty());
                    task.setOnSucceeded(event -> {
                        if (task.isDone()) {
                            progressBar.progressProperty().unbind();
                            progressBar.setProgress(1);
                            showAlerts.Alert(Alert.AlertType.INFORMATION, "Skopiowano ", "" + from + " do " + to, "Kopiowanie ");
                        }
                    });
                    task.setOnCancelled(event -> {
                        progressBar.progressProperty().unbind();
                        progressBar.setProgress(0.0);
                    });

                    task.setOnFailed(event -> {
                        progressBar.progressProperty().unbind();
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
                                showAlerts.Alert(Alert.AlertType.ERROR, "Kopiowanie nie udane ", String.valueOf(e), "Błąd");
                                throw new RuntimeException(e);
                            }
                            return null;
                        }
                    };
                    progressBar.progressProperty().bind(task.progressProperty());
                    task.setOnSucceeded(event -> {
                        if (task.isDone()) {
                            progressBar.progressProperty().unbind();
                            progressBar.setProgress(1);
                            showAlerts.Alert(Alert.AlertType.INFORMATION, "Skopiowano ", "" + from + " do " + to, "Kopiowanie ");
                        }
                    });

                    task.setOnCancelled(event -> {
                        progressBar.progressProperty().unbind();
                        progressBar.setProgress(0.0);
                    });

                    task.setOnFailed(event -> {
                        progressBar.progressProperty().unbind();
                        progressBar.setProgress(0.0);
                    });
                    new Thread(task).start();
                }

            }
        });
    }
}
