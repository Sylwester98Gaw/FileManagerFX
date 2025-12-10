/*
 * Copyright (c) 2024. This code is my creation, my passion, and my contribution to the community. It's open for all to use, learn, and grow from. I sign it with my name proudly, committed to supporting freedom and collaboration in the world of programming.
 */

package script.contextMenus;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import script.controller.WindowController;

public class ContextMenuMultipleSelect extends WindowController {
    public static ContextMenu contextMenuMultipleSelect = new ContextMenu();

    public static ContextMenu getContextMenuMultipleSelect() {
        return contextMenuMultipleSelect;
    }
    public void contextMenuMultipleSelect(){
        MenuItem copy = new MenuItem("Kopiuj");
        MenuItem move = new MenuItem("Przenieś");
        MenuItem moveToTrash = new MenuItem("Przenieś do kosza");
        moveToTrash.setOnAction(_ -> contextMenuMoveToTrash(null));
        move.setOnAction(_ -> contextMenuMove());
        //contextMenuMultipleSelect.getItems().add(copy); TODO
        contextMenuMultipleSelect.getItems().add(move);
        contextMenuMultipleSelect.getItems().add(moveToTrash);
    }
}
