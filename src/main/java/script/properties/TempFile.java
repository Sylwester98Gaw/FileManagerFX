package script.properties;

import script.helpers.ShowAlerts;
import javafx.scene.control.Alert;
import script.helpers.FileSys;

import java.io.*;

public class TempFile {
    private static final File file = new File(FileSys.HOME.getPath()+"/.SFM_files/tmps/SFM_Coping_Dat.tmp");
    ShowAlerts showAlerts = new ShowAlerts();
    public void createTmpFile(String paths) { //when click copy
        try {
            if (file.exists()){
                file.delete();
            }
            new File(file.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.append(paths);
            file.deleteOnExit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readTmpFile() { // when click paste-move
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
            String line;
            while ((line = reader.readLine()) != null) {
                return line;
            }
        } catch (FileNotFoundException e) {
            showAlerts.Alert(Alert.AlertType.ERROR, "Plik nie znaleziony", "Brak pliku tymczasowego  "+e, "Błąd");
        } catch (IOException | NullPointerException e) {
            showAlerts.Alert(Alert.AlertType.ERROR, "Błąd odczytu", "Problem z odczytem pliku tymczasowego  "+e, "Błąd");
        }
        return null;
    }
}





