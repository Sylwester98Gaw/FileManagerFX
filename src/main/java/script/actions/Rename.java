package script.actions;

import javafx.scene.control.Alert;
import script.helpers.ShowAlerts;

import java.io.File;

public class Rename {
    File path;
    String newName;
    ShowAlerts showAlerts = new ShowAlerts();

    public Rename(String newName, File path) {
        this.newName = newName;
        this.path = path;
    }

    public void renameFile() {
        if (path.isDirectory()) {
            File oldName = new File(String.valueOf(path));
            File newFileName = new File(oldName.getParent() + "/" + newName);
            System.out.println(newFileName);
            if (oldName.renameTo(newFileName)) {
                showAlerts.Alert(Alert.AlertType.INFORMATION, "Zadanie zmiany nazwy", "Wykonane", "OK");
            } else {
                showAlerts.Alert(Alert.AlertType.INFORMATION, "Zadanie zmiany nazwy", "Nie wykonane", "Błąd");

            }
        }else {
            showAlerts.Alert(Alert.AlertType.INFORMATION,"Tylko dla katalogów", "Na razie zmiana naazwy działa tylko dla katalogów", "Informacja");
        }
    }
}
