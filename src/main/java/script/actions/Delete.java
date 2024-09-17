package script.actions;

import script.helpers.ShowAlerts;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.commons.io.FileUtils;
import script.helpers.FileSys;

import java.io.File;
import java.io.IOException;

public class Delete {
    ShowAlerts showAlerts = new ShowAlerts();

    public void moveToTrash(File path){
        showAlerts.Alert(Alert.AlertType.INFORMATION,"Przenoszenie do kosza",path.getName(),"Do kosza");
        try {
            if (!path.isDirectory()){
                FileUtils.moveFileToDirectory(path, new File(FileSys.HOME.getPath() + "/.local/share/Trash/files"),false);
            }else {
                FileUtils.moveDirectoryToDirectory(path, new File(FileSys.HOME.getPath() + "/.local/share/Trash/files"),false);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public void delete(File file) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie usuwania");
        alert.setHeaderText("Czy na pewno chcesz usunąć ten plik? " + file);
        ButtonType okButton = new ButtonType("OK");
        ButtonType cancelButton = new ButtonType("Anuluj", ButtonType.CANCEL.getButtonData());
        alert.getButtonTypes().setAll(okButton, cancelButton);
        alert.showAndWait().ifPresent(response -> {
            if (response == okButton) {
                File fileToDel = new File(String.valueOf(file));
                if (fileToDel.isDirectory()) {
                    try {
                        FileUtils.deleteDirectory(fileToDel);
                        showAlerts.Alert(Alert.AlertType.INFORMATION, "Usuwanie", fileToDel.getName(), "Usuwanie katalogu ");

                    } catch (IOException e) {
                        showAlerts.Alert(Alert.AlertType.ERROR, "Usuwanie nie udane ", String.valueOf(e), "Błąd");
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        FileUtils.delete(fileToDel);
                        showAlerts.Alert(Alert.AlertType.INFORMATION, "Usuwanie", fileToDel.getName(), "Usuwanie pliku ");

                    } catch (IOException e) {
                        showAlerts.Alert(Alert.AlertType.ERROR, "Usuwanie nie udane ", String.valueOf(e), "Błąd");
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}
