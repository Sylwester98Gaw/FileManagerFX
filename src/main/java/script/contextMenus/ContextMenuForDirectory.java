/*
 * Copyright (c) 2024. This code is my creation, my passion, and my contribution to the community. It's open for all to use, learn, and grow from. I sign it with my name proudly, committed to supporting freedom and collaboration in the world of programming.
 */

package script.contextMenus;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import script.controller.WindowController;
import script.lang.LangENG;
import script.properties.Config;

import java.io.File;

public class ContextMenuForDirectory extends WindowController{
    public static ContextMenu dirContext = new ContextMenu();

    public static ContextMenu getDirContext() {
        return dirContext;
    }

    public static void setDirContext(ContextMenu dirContext) {
        ContextMenuForDirectory.dirContext = dirContext;
    }

    //Config config = new Config();
    public void contextMenuForDirectory() {
        Menu setColor = new Menu("Zmień kolor wyświetlania");

        MenuItem redColor = new MenuItem("Czerwony");
        MenuItem blueColor = new MenuItem("Niebieski");
        MenuItem greenColor = new MenuItem("Zielony");
        MenuItem silverColor = new MenuItem("Srebrny");
        MenuItem whiteColor = new MenuItem("Biały");
        MenuItem yellowColor = new MenuItem("Żółty");
        MenuItem purpleColor = new MenuItem("Purpura");
        MenuItem deleteColor = new MenuItem("Usuń kolor");

        MenuItem copy = new MenuItem("Kopiuj");
        MenuItem addBokmarks = new MenuItem("Dodaj do zakładek");
        MenuItem paste = new MenuItem("Wklej");
        MenuItem move = new MenuItem("Przenieś");
        MenuItem rename = new MenuItem("Zmień nazwę...");
        MenuItem terminal = new MenuItem("Terminal");
        Menu menu = new Menu("Stwórz...");
        MenuItem createDir = new MenuItem("Stwórz katalog");
        MenuItem createFile = new MenuItem("Stwórz plik");
        menu.getItems().add(createDir);
        menu.getItems().add(createFile);
        setColor.getItems().add(redColor);
        setColor.getItems().add(greenColor);
        setColor.getItems().add(blueColor);
        setColor.getItems().add(silverColor);
        setColor.getItems().add(whiteColor);
        setColor.getItems().add(yellowColor);
        setColor.getItems().add(purpleColor);
        setColor.getItems().add(deleteColor);
        MenuItem moveToTrash = new MenuItem("Przenieś do kosza");
        MenuItem properties = new MenuItem("Właściwości");

        copy.setOnAction(actionEvent -> contextMenuCopy(selected));
        paste.setOnAction(actionEvent -> contextMenuPaste(selected));
        createDir.setOnAction(actionEvent -> contextMenuCreateDir(new File(selected.toURI())));
        createFile.setOnAction(actionEvent -> contextMenuCreateFile(new File(selected.toURI())));
        properties.setOnAction(actionEvent -> contextMenuGetProperties(new File(selected.getPath())));
        terminal.setOnAction(actionEvent -> contextMenuOpenTerminal(new File(selected.getPath())));
        move.setOnAction(actionEvent -> contextMenuMove());
        rename.setOnAction(actionEvent -> contextMenuRename(selected));
        moveToTrash.setOnAction(actionEvent -> contextMenuMoveToTrash(new File(selected.getPath())));
        addBokmarks.setOnAction(actionEvent -> addDirToBookmarks());

        redColor.setOnAction(actionEvent -> setViewColor("red", new File(selected.getName())));
        greenColor.setOnAction(actionEvent -> setViewColor("green", new File(selected.getName())));
        blueColor.setOnAction(actionEvent -> setViewColor("blue", new File(selected.getName())));
        silverColor.setOnAction(actionEvent -> setViewColor("silver", new File(selected.getName())));
        whiteColor.setOnAction(actionEvent -> setViewColor("white", new File(selected.getName())));
        yellowColor.setOnAction(actionEvent -> setViewColor("yellow", new File(selected.getName())));
        purpleColor.setOnAction(actionEvent -> setViewColor("purple", new File(selected.getName())));
        deleteColor.setOnAction(actionEvent -> setViewColor("none",new File(selected.getName())));

        dirContext.getItems().add(move);
        dirContext.getItems().add(copy);
        dirContext.getItems().add(rename);
        dirContext.getItems().add(paste);
        dirContext.getItems().add(addBokmarks);
        dirContext.getItems().add(menu);
        dirContext.getItems().add(moveToTrash);
        dirContext.getItems().add(properties);
        dirContext.getItems().add(terminal);
        dirContext.getItems().add(setColor);
    }

}
