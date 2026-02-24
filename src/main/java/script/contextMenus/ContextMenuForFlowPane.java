/*
 * Copyright (c) 2024. This code is my creation, my passion, and my contribution to the community. It's open for all to use, learn, and grow from. I sign it with my name proudly, committed to supporting freedom and collaboration in the world of programming.
 */

package script.contextMenus;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import script.controller.WindowController;
import java.io.File;
public class ContextMenuForFlowPane extends  WindowController  {
    public static ContextMenu contextMenuFlowPane = new ContextMenu();

    public static ContextMenu getContextMenuFlowPane() {
        return contextMenuFlowPane;
    }
    public void flowPaneContextMenu() {
        //**********************************************
        MenuItem paste = new MenuItem("Wklej");
        Menu menu = new Menu("Stwórz...");
        MenuItem createDir = new MenuItem("Stwórz katalog");
        MenuItem createFile = new MenuItem("Stwórz plik");
        menu.getItems().add(createDir);
        menu.getItems().add(createFile);

        paste.setOnAction(_ -> contextMenuPaste(new File(file.getAbsolutePath())));
        createDir.setOnAction(_ -> contextMenuCreateDir(new File(file.getAbsolutePath())));
        createFile.setOnAction(_ -> contextMenuCreateFile(new File(file.getAbsolutePath())));
        contextMenuFlowPane.getItems().add(menu);
        contextMenuFlowPane.getItems().add(paste);
        //**********************************************
    }
    public void resetList(){
        try {
            for (int i = 0; i < getContextMenuFlowPane().getItems().size(); i++) {
                getContextMenuFlowPane().getItems().remove(i);
            }
            if (!getContextMenuFlowPane().getItems().isEmpty()){
                resetList();
            }
        }catch (Exception e){
            e.getStackTrace();
        }
    }
}
