package script.actions;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import script.helpers.AddToList;
import script.helpers.ShowAlerts;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;


public class Move {
    ShowAlerts showAlerts = new ShowAlerts();

    public void move(File from, File to, ProgressBar progressBar) {
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
                                showAlerts.Alert(Alert.AlertType.ERROR, "Kopiowanie nie udane ", String.valueOf(e), "B");
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
                            showAlerts.Alert(Alert.AlertType.INFORMATION, "Przeniesiono ", "" + from + " do " + from, "Kopiowanie ");
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
                } else {
                    Task<Void> task = new Task<>() {
                        @Override
                        protected Void call() throws Exception {
                            try {
                                FileUtils.moveFileToDirectory(from, to, true);
                            } catch (IOException e) {
                                showAlerts.Alert(Alert.AlertType.ERROR, "Kopiowanie nie udane ", String.valueOf(e), "B");
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
                            showAlerts.Alert(Alert.AlertType.INFORMATION, "Przeniesiono ", "" + from + " do " + from, "Kopiowanie ");
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
        });
    }
    public void moveAll(File to, ProgressBar progressBar) {
        AddToList addToList = new AddToList();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Przenoszenie");
        alert.setHeaderText("Przenoszenie z " + AddToList.list + " do " + to);
        alert.setContentText("Wszystko się zgadza ?");
        ButtonType okButton = new ButtonType("OK");
        ButtonType cancelButton = new ButtonType("Anuluj", ButtonType.CANCEL.getButtonData());
        alert.getButtonTypes().setAll(okButton, cancelButton);
        alert.showAndWait().ifPresent(response -> {
            if (response == okButton){
                for (File files : AddToList.list) {
                    if (files.isDirectory()) {
                        Task<Void> task = new Task<>() {
                            @Override
                            protected Void call() throws Exception {
                                try {
                                    FileUtils.moveDirectoryToDirectory(files, to, true);
                                } catch (IOException e) {
                                    showAlerts.Alert(Alert.AlertType.ERROR, "Kopiowanie nie udane ", String.valueOf(e), "B");
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
                                showAlerts.Alert(Alert.AlertType.INFORMATION, "Skopiowano ", "Wykonano", "Kopiowanie ");
                                addToList.removeAll();
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
                    } else {
                        Task<Void> task = new Task<>() {
                            @Override
                            protected Void call() throws Exception {
                                try {
                                    FileUtils.moveFileToDirectory(files, to, true);
                                } catch (IOException e) {
                                    showAlerts.Alert(Alert.AlertType.ERROR, "Kopiowanie nie udane ", String.valueOf(e), "B");
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
                                showAlerts.Alert(Alert.AlertType.INFORMATION, "Skopiowano ", "Wykonano", "Kopiowanie ");
                                addToList.removeAll();
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
            }
        });
    }
}
