/*
 * Copyright (c) 2024. This code is my creation, my passion, and my contribution to the community. It's open for all to use, learn, and grow from. I sign it with my name proudly, committed to supporting freedom and collaboration in the world of programming.
 */

package script.contextMenus;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import script.controller.WindowController;

public class ContextMenuForTrash extends WindowController {
    public static ContextMenu contextMenuForTrash = new ContextMenu();

    public static ContextMenu getContextMenuForTrash() {
        return contextMenuForTrash;
    }
    public void contextMenuForTrash(){
        MenuItem move = new MenuItem("Przywróć do...");
        MenuItem delete = new MenuItem("Usuń");
        delete.setOnAction(actionEvent -> contextMenuDeleteDir(selected));
        move.setOnAction(actionEvent -> contextMenuMove());
        contextMenuForTrash.getItems().add(delete);
        contextMenuForTrash.getItems().add(move);
    }
}
