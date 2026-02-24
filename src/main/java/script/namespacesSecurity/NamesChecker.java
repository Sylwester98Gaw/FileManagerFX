package script.namespacesSecurity;

import javafx.scene.control.Alert;
import script.helpers.ShowAlerts;

public class NamesChecker {
    /*
         Checks for "/" in "name" if it is, returns true, otherwise false
      */
    ShowAlerts showAlerts = new ShowAlerts();
    public boolean checkName (String name){
        return name.contains("/");
    }
    public boolean checkIsEmpty (String name){
        return name.isEmpty();
    }
    public void badName (){
        showAlerts.Alert(Alert.AlertType.WARNING,"Uwaga !","Nie można w nazwie używać znaku /, lub nazwa jest pusta.","Problem z nazwą");
    }
}
