package script.actions;

import script.exec.ExecuteCommand;
import script.helpers.ShowAlerts;
import javafx.scene.control.Alert;

import java.io.File;

public class Terminal {
    ShowAlerts showAlerts = new ShowAlerts();
    ExecuteCommand executeCommand = new ExecuteCommand();
    String systemEv = System.getenv("XDG_CURRENT_DESKTOP");

    public void openTerminal(File path) {
        if (path.isDirectory() && !path.isFile()) try {
            switch (systemEv) {
                case "KDE":
                    executeCommand.commands("konsole", path.getPath());
                    break;
                case "GNOME":
                    executeCommand.commands("gnome-terminal", path.getPath());
                    break;
                default:
                    executeCommand.commands("xterm", path.getPath());
                    break;
            }
        } catch (Exception e) {
            showAlerts.Alert(Alert.AlertType.ERROR, "Wystąpił problem z terminalem ", String.valueOf(e), "Błąd");
        }
    }
}
