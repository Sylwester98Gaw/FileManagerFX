package script.actions;

import script.helpers.ShowAlerts;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import script.namespacesSecurity.NamesChecker;

public class CreateDirOrFiles {
    ShowAlerts showAlerts = new ShowAlerts();
    NamesChecker namesChecker = new NamesChecker();

    public void makeNewDirectory(File pathWhere,boolean type) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nowy katalog/plik");
        dialog.setHeaderText("Podaj nazwe nowego katalogu który zostanie utworzony w " + pathWhere.getParent());
        dialog.setContentText("Nazwa:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(directoryName -> {
            if (!namesChecker.checkName(result.toString())) {
                if (type){
                    File newDirectory = new File(pathWhere + "/" + result.get()); //MakeDir true
                    mkdir(newDirectory);
                }else {
                    File newDirectory = new File(pathWhere + "/" + result.get()+".txt"); //MakeFile false
                    mkfile(newDirectory);
                }

            } else {
                badName();
            }
        });
    }

    public void mkdir(File newDirectory) {
        if (newDirectory.exists()) {
            showAlerts.Alert(Alert.AlertType.ERROR, newDirectory.getName(), "Katalog już istnieje", "Bład");
        } else {
            boolean success = newDirectory.mkdir();
            if (success) {
                System.out.println("Zadanie zostało wykonane");
            } else {
                showAlerts.Alert(Alert.AlertType.ERROR, newDirectory.getName(), "Coś nie działa", "Błąd");
            }
        }
    }

    public void mkfile(File newFile) {
        if (newFile.exists()) {
            showAlerts.Alert(Alert.AlertType.ERROR, newFile.getName(), "Plik już istnieje", "Bład");
        } else {
            boolean success = false;
            try {
                success = newFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (success) {
                System.out.println("Zadanie zostało wykonane");
            } else {
                showAlerts.Alert(Alert.AlertType.ERROR, newFile.getName(), "Coś nie działa", "Błąd");
            }
        }
    }
    public void badName(){
        showAlerts.Alert(Alert.AlertType.WARNING,"Uwaga !","Nie można w nazwie używać znaku /","Mamy problem :/");
    }
}
