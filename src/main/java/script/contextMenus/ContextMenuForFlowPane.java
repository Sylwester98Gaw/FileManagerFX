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
        MenuItem pasteL = new MenuItem("Wklej");
       // MenuItem move = new MenuItem("Przenieś");
        Menu menuL = new Menu("Stwórz...");
        MenuItem createDirL = new MenuItem("Stwórz katalog");
        MenuItem createFileL = new MenuItem("Stwórz plik");
        menuL.getItems().add(createDirL);
        menuL.getItems().add(createFileL);

        pasteL.setOnAction(actionEvent -> contextMenuPaste(new File(file.getAbsolutePath())));
        createDirL.setOnAction(actionEvent -> contextMenuCreateDir(new File(file.getAbsolutePath())));
        createFileL.setOnAction(actionEvent -> contextMenuCreateFile(new File(file.getAbsolutePath())));
       // move.setOnAction(actionEvent -> contextMenuMove(new File(path.getText())));
        contextMenuFlowPane.getItems().add(menuL);
        contextMenuFlowPane.getItems().add(pasteL);
        //contextMenuFlowPane.getItems().add(move);
        //**********************************************
    }
}
