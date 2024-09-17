package script.actions;

import script.helpers.ShowAlerts;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Move {
    ShowAlerts showAlerts = new ShowAlerts();

    public void move(File from, File to) {
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
                    try {
                        FileUtils.moveDirectoryToDirectory(from, to, true);
                        showAlerts.Alert(Alert.AlertType.INFORMATION, "Przeniesiono ", "" + from + " do " + from, "Kopiowanie ");
                    } catch (IOException e) {
                        showAlerts.Alert(Alert.AlertType.ERROR, "Kopiowanie nie udane ", String.valueOf(e), "B");
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        FileUtils.moveFileToDirectory(from, to, true);
                        showAlerts.Alert(Alert.AlertType.INFORMATION, "Skopiowano ", "" + from + " do " + from, "Kopiowanie ");
                    } catch (IOException e) {
                        showAlerts.Alert(Alert.AlertType.ERROR, "Kopiowanie nie udane ", String.valueOf(e), "B");
                        throw new RuntimeException(e);
                    }
                }
        });
    }
}
