package script.namespacesSecurity;

import javafx.scene.control.Alert;
import script.helpers.ShowAlerts;

public class NamesChecker {
    /*
        Checks for "/" in "name" if it is, returns true, otherwise false
     */
    ShowAlerts showAlerts = new ShowAlerts();
    public boolean checkName (String name){
        if (name.indexOf("/") >= 0){
            return true;
        }else {
            return false;
        }
    }
    public void badName (){
        showAlerts.Alert(Alert.AlertType.WARNING,"Uwaga !","Nie można w nazwie używać znaku /","Mamy problem :/");
    }
}
