/*
 * Copyright (c) 2024. This code is my creation, my passion, and my contribution to the community. It's open for all to use, learn, and grow from. I sign it with my name proudly, committed to supporting freedom and collaboration in the world of programming.
 */

package script.contextMenus;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import script.controller.WindowController;

import java.io.File;
import java.util.Optional;

public class ContextMenuForFiles extends WindowController {
    public static ContextMenu contextMenuForFiles = new ContextMenu();

    public static ContextMenu getContextMenuForFiles() {
        return contextMenuForFiles;
    }

    public void contextMenuForFile(Optional<MenuItem> menuItem) {
        Menu open = new Menu("Otwórz za pomocą");
        Menu setColor = new Menu("Zmień kolor wyświetlania");
        MenuItem select = new MenuItem("Dodaj z listy programów");
        MenuItem copy = new MenuItem("Kopiuj");
        MenuItem move = new MenuItem("Przenieś");
        MenuItem rename = new MenuItem("Zmień nazwę...");
        MenuItem moveToTrash = new MenuItem("Przenieś do kosza");
        MenuItem properties = new MenuItem("Właściwości");
        open.getItems().add(select);
        if (menuItem.isPresent()){
            menuItem.ifPresent(item -> open.getItems().add(item));
            menuItem.get().setOnAction(actionEvent -> runWithAddedProgram(selected,menuItem.get().getText()));
        }
        copy.setOnAction(actionEvent -> contextMenuCopy((selected)));
        properties.setOnAction(actionEvent -> contextMenuGetProperties(selected));
        rename.setOnAction(actionEvent -> contextMenuRename(selected));
        select.setOnAction(actionEvent -> addExecProgram((selected.getName())));
        moveToTrash.setOnAction(actionEvent -> contextMenuMoveToTrash(new File(selected.getPath())));
        move.setOnAction(actionEvent -> contextMenuMove());
        contextMenuForFiles.getItems().add(open);
        contextMenuForFiles.getItems().add(move);
        contextMenuForFiles.getItems().add(copy);
        contextMenuForFiles.getItems().add(rename);
        contextMenuForFiles.getItems().add(moveToTrash);
        contextMenuForFiles.getItems().add(properties);
        contextMenuForFiles.getItems().add(setColor);
    }
    public void resetList(){
        try {
            for (int i = 0; i < getContextMenuForFiles().getItems().size(); i++) {
               getContextMenuForFiles().getItems().remove(i);
            }
            if (!getContextMenuForFiles().getItems().isEmpty()){
                resetList();
            }
        }catch (Exception e){
            e.getStackTrace();
        }
    }
}
