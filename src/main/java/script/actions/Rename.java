package script.actions;

import javafx.scene.control.Alert;
import script.helpers.ShowAlerts;

import java.io.File;

public class Rename {
    File path;
    String extension;
    String newName;
    ShowAlerts showAlerts = new ShowAlerts();

    public Rename(String newName,String extension, File path) {
        this.newName = newName;
        this.extension = extension;
        this.path = path;
    }

    public void renameFile() {
        File oldName = new File((path.getAbsolutePath()));
        if (path.isDirectory()) {
            File newDirName = new File(oldName.getParent() + "/" + newName);
            System.out.println(newDirName.getAbsolutePath());
            if (oldName.renameTo(newDirName)) {
                showAlerts.Alert(Alert.AlertType.INFORMATION, "Zadanie zmiany nazwy", "Wykonane", "OK");
            } else {
                showAlerts.Alert(Alert.AlertType.INFORMATION, "Zadanie zmiany nazwy", "Nie wykonane", "Błąd");
            }
        }else {
            File newFileName = new File(oldName.getParent() + "/" + newName+"."+extension);
            System.out.println(newFileName.getAbsolutePath());
            if (oldName.renameTo(newFileName)) {
                showAlerts.Alert(Alert.AlertType.INFORMATION, "Zadanie zmiany nazwy", "Wykonane", "OK");
            } else {
                showAlerts.Alert(Alert.AlertType.INFORMATION, "Zadanie zmiany nazwy", "Nie wykonane", "Błąd");

            }
        }
    }
}
