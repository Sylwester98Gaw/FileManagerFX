/*
 * Copyright (c) 2025. This code is my creation, my passion, and my contribution to the community. It's open for all to use, learn, and grow from. I sign it with my name proudly, committed to supporting freedom and collaboration in the world of programming.
 */

package script.actions;

import com.github.plushaze.traynotification.notification.Notifications;
import script.exec.ExecuteCommand;
import script.helpers.AddToList;
import script.helpers.ShowTrayNotification;

import java.io.File;

// integration witch trash-cli
/*
trash-put           trash files and directories.
trash-empty         empty the trashcan(s).
trash-list          list trashed files.
trash-restore       restore a trashed file.
trash-rm            remove individual files from the trashcan.
todo problem z nazwami które zawierają białe znaki
 */
public class TrashIntegration {
    ExecuteCommand executeCommand = new ExecuteCommand();
    ShowTrayNotification showTrayNotification = new ShowTrayNotification();

    public void restore(File path) {
     //   executeCommand.commands("trash-restore " + path.getName());
    }

    public void trashFilesList(){

    }

    public void moveToTrashAll() {
        for (int i = 0; i < AddToList.list.size(); i++) {
            executeCommand.commands("trash-put " + AddToList.list.get(i));
            showTrayNotification.viewNotification("Przeniesiono do kosza", String.valueOf(AddToList.list.get(i)), Notifications.SUCCESS);
            // troche bez sensu heh informacja się nakłada
        }
    }

    public void moveToTrash(File path) {
        executeCommand.commands("trash-put " + path);
        showTrayNotification.viewNotification("Przeniesiono do kosza", path.getName(), Notifications.SUCCESS);
    }

    public void delete(File fileToDelete) {
        executeCommand.commands("trash-rm " + fileToDelete.getName());
        showTrayNotification.viewNotification("Usunięto z kosza",fileToDelete.getName(), Notifications.SUCCESS);
    }

    public void deleteAllFromTrash(){
        executeCommand.commands("trash-empty");
        showTrayNotification.viewNotification("Kosz", "Kosz został opróżniony", Notifications.SUCCESS);
    }
}
