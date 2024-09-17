package script.namespacesSecurity;

public class NamesChecker {
    /*
        Checks for "/" in "name" if it is, returns true, otherwise false
     */
    public boolean checkName (String name){
        if (name.indexOf("/") >= 0){
            return true;
        }else {
            return false;
        }
    }
}
