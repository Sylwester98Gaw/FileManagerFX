/*
 * Copyright (c) 2024. This code is my creation, my passion, and my contribution to the community.
 * It's open for all to use, learn, and grow from. I sign it with my name proudly, committed to supporting freedom and collaboration in the world of programming.
 */

package script.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import org.apache.commons.io.FilenameUtils;
import script.contextMenus.ContextMenuForTrash;
import script.labels.Labels;
import script.properties.Config;
import script.properties.TempFile;

import script.actions.*;
import script.contextMenus.ContextMenuForDirectory;
import script.contextMenus.ContextMenuForFiles;
import script.contextMenus.ContextMenuForFlowPane;
import script.exec.ExecuteCommand;
import script.helpers.*;

public class WindowController extends Labels implements Initializable, Runnable {
    public ListView<Label> bookmarks;
    public Label path;
    public Label copyItemName;
    public Label lastPath;
    public Label labelSelected;
    public static File selected;
    public static File file;
    public static File fileStatusControl;
    public TextField goPatch;
    public Button goButton;
    public Button backButton;
    public Button forwardButton;
    public MenuItem authorButton;
    public Button terminalButton;
    public CheckBox hidden;
    public FlowPane flowPane;
    public VBox vBox;
    public ImageView status; //  do wyjebania ?
    public ProgressBar progressBar;
    public Label loadedFiles;
    public CheckBox checkMultipleSelect;
    ExecuteCommand executeCommand = new ExecuteCommand();
    public boolean isHiddenFilter = false;
    ShowAlerts showAlerts = new ShowAlerts();
    public static int actObjects;
    ContextMenuForFiles contextMenuForFiles;
    private String lastPathDir;
    public void run() {
        ContextMenuForFlowPane contextMenuForFlowPane = new ContextMenuForFlowPane();
        contextMenuForFlowPane.flowPaneContextMenu();

        ContextMenuForDirectory contextMenuForDirectory = new ContextMenuForDirectory();
        contextMenuForDirectory.contextMenuForDirectory();

        ContextMenuForTrash contextMenuForTrash = new ContextMenuForTrash();
        contextMenuForTrash.contextMenuForTrash();

        contextMenuForFiles = new ContextMenuForFiles();

        GetProgress.setProgressBar(progressBar);
        GetBookmarksList.setGetBookmark(bookmarks);

        path.setText(Arg.getArgument()); // start position

        file = new File(path.getText());
        status.setImage(getImageFromName("fmfxicon.png", 96, 96));
        goButton.setGraphic(new ImageView(getImageFromName("go.png", "manager", 26, 26)));
        authorButton.setGraphic(new ImageView(getImageFromName("info.png", "manager", 16, 16)));
        backButton.setGraphic(new ImageView(getImageFromName("back.png", "manager", 16, 16)));
        forwardButton.setGraphic(new ImageView(getImageFromName("forward.png","manager",16,16)));
        terminalButton.setGraphic(new ImageView(getImageFromName("term.png", "manager", 16, 16)));
        checkMultipleSelect.setGraphic(new ImageView(getImageFromName("multiple.png","manager",16,16)));
        hidden.setGraphic(new ImageView(getImageFromName("hidden.png", "manager", 32, 32)));
        goButton.setOnMouseEntered(mouseEvent -> buttonEntered(goButton));
        goButton.setOnMouseExited(mouseEvent -> buttonExited(goButton));
        backButton.setOnMouseEntered(mouseEvent -> buttonEntered(backButton));
        backButton.setOnMouseExited(mouseEvent -> buttonExited(backButton));
        terminalButton.setOnMouseEntered(mouseEvent -> buttonEntered(terminalButton));
        terminalButton.setOnMouseExited(mouseEvent -> buttonExited(terminalButton));
        forwardButton.setOnMouseEntered(mouseEvent -> buttonEntered(forwardButton));
        forwardButton.setOnMouseExited(mouseEvent -> buttonExited(forwardButton));
        forwardButton.setDisable(true);
        refresh(flowPane, path, lastPath, goPatch, isHiddenFilter,loadedFiles);
        createBookmarks();
        createTooltips();
        autoUpdateSystem();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        run();
    }

    void buttonEntered(Button button) {
        button.setStyle("-fx-background-color: #44484d; -fx-background-radius: 5;");
    }

    void buttonExited(Button button) {
        button.setStyle("-fx-background-color: #202224; -fx-background-radius: 5;");
    }

    void createTooltips() {
        Tooltip tooltipBackButton = new Tooltip("Wróć");
        Tooltip tooltipForwardButton = new Tooltip("Wcześniej otwarty");
        Tooltip tooltipGoButton = new Tooltip("Przejdź do...");
        Tooltip tooltipHidden = new Tooltip("Ukryte pliki/katalogi");
        Tooltip tooltipTerminal = new Tooltip("Terminal");
        Tooltip tooltipMultiple = new Tooltip("Tryb zaznaczania");
        Tooltip tooltipLoaded = new Tooltip("Liczba załadowanych elementów");
        terminalButton.setTooltip(tooltipTerminal);
        hidden.setTooltip(tooltipHidden);
        goButton.setTooltip(tooltipGoButton);
        backButton.setTooltip(tooltipBackButton);
        forwardButton.setTooltip(tooltipForwardButton);
        checkMultipleSelect.setTooltip(tooltipMultiple);
        loadedFiles.setTooltip(tooltipLoaded);
    }

    void createBookmarks() {
        try {
            GetBookmarksList.getGetBookmark().getItems().removeAll();
            Label fileSys = new Label("System");
            Label home = new Label("Home");
            Label trash = new Label("Trash");
            fileSys.setGraphic(new ImageView(getImageFromName("system.png", "bookmarks", getxBookmark(), getyBookmark())));
            home.setGraphic(new ImageView(getImageFromName("home.png", "bookmarks", getxBookmark(), getyBookmark())));
            trash.setGraphic(new ImageView(getImageFromName("trash.png", "bookmarks", getxBookmark(), getyBookmark())));
            GetBookmarksList.getGetBookmark().getItems().add(fileSys);
            GetBookmarksList.getGetBookmark().getItems().add(home);
            GetBookmarksList.getGetBookmark().getItems().add(trash);
            for (String addedBookmark : Config.bookmarkMap.keySet()) {
                Label label = new Label(addedBookmark);
                label.setGraphic(new ImageView(getImageFromName("mark.png", "bookmarks", getxBookmark(), getyBookmark())));
                GetBookmarksList.getGetBookmark().getItems().add(label);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Trash
     *
     * @param path
     */
    public void contextMenuMoveToTrash(File path) {
        Delete delete = new Delete();
        delete.moveToTrash(path);
    }

    public void contextMenuCreateDir(File path) {
        CreateDirOrFiles createDirectory = new CreateDirOrFiles();
        createDirectory.makeNewDirectory(path, true);
    }

    public void contextMenuCreateFile(File path) {
        CreateDirOrFiles createDirectory = new CreateDirOrFiles();
        createDirectory.makeNewDirectory(path, false);
    }

    public void contextMenuCreateZipFile() {

    }

    public void addDirToBookmarks() {
        Config config = new Config();
        config.saveBookmarks(selected.getName(), new File(selected.getPath()));
        config.loadAddedBookmarks();
        for (String addedBookmark : Config.bookmarkMap.keySet()) {
            Label label = new Label(addedBookmark);
            label.setGraphic(new ImageView(getImageFromName("mark.png", "bookmarks", getxBookmark(), getyBookmark())));
            GetBookmarksList.getGetBookmark().getItems().add(label);
        }
    }

    public void contextMenuOpenTerminal(File path) {
        Terminal terminal = new Terminal();
        terminal.openTerminal(path);
    }

    public void contextMenuDeleteDir(File fileToDelete) {
        Delete delete = new Delete();
        delete.delete(fileToDelete);
    }

    public void contextMenuMove() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(FileSys.HOME.getPath()));
        directoryChooser.setTitle("Przenoszenie");
        Move move = new Move();
        move.move(selected, directoryChooser.showDialog(null));
    }

    public void contextMenuCopy(File path) {
        TempFile tempFile = new TempFile();
        tempFile.createTmpFile(String.valueOf(path));
//        copyItemName.setText(path.toString());
    }

    public void contextMenuPaste(File where) {
        TempFile tempFile = new TempFile();
        Copy copyAction = new Copy();
        copyAction.copy(new File(tempFile.readTmpFile()), where, GetProgress.getProgressBar());
    }

    public void contextMenuRename(File path) {
    }

    public void addExecProgram(String path) {
        try {
            Config config = new Config();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Dodaj opcjonalny program");
            fileChooser.setInitialDirectory(new File("/bin"));
            File a = new File(fileChooser.showOpenDialog(null).toURI());
            config.saveAddedProgram(String.valueOf(a), path);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public void contextMenuGetProperties(File file) { // TODO rozwinąć okno właściwości !!
        GetSize getSize = new GetSize();
        System.out.println(file);
        String name;
        if (file.isDirectory()) {
            name = "Katalog '";
        } else {
            name = "Plik '";
        }
        showAlerts.Alert(Alert.AlertType.INFORMATION, file.getName(), name + file.getName() + "' Zajmuje " +
                getSize.getMb(file) + " MB, " + getSize.getKb(file) + " KB, " + getSize.getSize(file) + " B", "Właściwości");
    }

    public void goButtAction() {
        try {
            File file = new File(goPatch.getText());
            System.out.println(file.getPath());
            if (file.exists()) {
                path.setText(file.getPath());
                refresh(this.flowPane, path, lastPath, goPatch, isHiddenFilter,loadedFiles);
            } else {
                showAlerts.Alert(Alert.AlertType.INFORMATION, "Katalog nie istnieje", "Ścieżka nie jest prawidłowa", "Informacja");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void termButtonAction(ActionEvent actionEvent) {
        Terminal terminal = new Terminal();
        terminal.openTerminal(new File(path.getText()));
    }

    public boolean checkTheFileExtension(File file) {
        if (!file.isDirectory()) {
            String extension = FilenameUtils.getExtension(file.getName());
            return "sh".equals(extension) || "AppImage".equals(extension) || "desktop".equals(extension);
        }
        return false;
    }

    ///////////////////////////////////////
    // OPENING PROGRAMS
    //////////////////////////////////////
    public void runInProgram(File file) {
        if (checkTheFileExtension(file)) {
            System.out.println("uruchomione sposób prymitywny");
            executeCommand.commands(file.getPath());
        } else {
            executeCommand.commands("xdg-open " + getUrl(file));
            System.out.println("uruchomione za pomocą systemowego XDG-OPEN");
        }
    }

    public void runWithAddedProgram(File file, String program) {
        executeCommand.commands(program + " " + getUrl(file));
        System.out.println("uruchomione za pomocą dodatkowego oprogramowania przypisanego do pliku");
    }

    public URL getUrl(File file) {
        URL url;
        try {
            url = file.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return url;
    }

    //////////////////////////////////////////
    public void backAction() {
        {
            try {
                if (file != null) {
                    lastPathDir = path.getText();
                    forwardButton.setDisable(false);
                    path.setText(file.getParent());
                    file = new File(path.getText());
                    selected = null;
                    backButton.setDisable(path.getText().equals("/"));
                    refresh(this.flowPane, path, lastPath, goPatch, isHiddenFilter,loadedFiles);
                }
            } catch (NullPointerException e) {
                System.out.println(path.getText());
                file = new File(path.getText());
                path.setText(file.getParent());
                selected = null;
                backAction();
            }
        }
    }
    public void forwardAction(ActionEvent actionEvent) {
        try {
            if (lastPathDir !=null){
                selected = null;
                path.setText(lastPathDir);
                file = new File(path.getText().trim());
                objects = 0;
                lastPathDir = null;
                forwardButton.setDisable(true);
                refresh(this.flowPane, path, lastPath, goPatch, isHiddenFilter,loadedFiles);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void goToBookmarksDir(MouseEvent mouseEvent) {
        try {
            if (mouseEvent.getButton() == MouseButton.PRIMARY){
                path.setText(getBookmarkDir());
                goPatch.setText(getBookmarkDir());
                file = new File(path.getText());
                backButton.setDisable(path.getText().equals("/"));
                refresh(this.flowPane, path, lastPath, goPatch, isHiddenFilter,loadedFiles);
            }else if (mouseEvent.getButton() == MouseButton.SECONDARY){
                System.out.println("nic tu narazie  nie ma i chuj");// todo
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    public String getBookmarkDir() {
        String value = (bookmarks.getSelectionModel().getSelectedItem()).getText();
        lastPathDir =  null;
        switch (value) {
            case "Home":
                return FileSys.HOME.getPath();
            case "System":
                return FileSys.SYSTEM.getPath();
            case "Trash":
                return FileSys.HOME.getPath() + FileSys.TRASH.getPath();
        }
        return Config.bookmarkMap.get(value);
    }
    public void refreshList() {
        refresh(this.flowPane, path, lastPath, goPatch, isHiddenFilter,loadedFiles);
    }

    public void hiddenAction() {
        if (hidden.isSelected()) {
            hidden.setGraphic(new ImageView(getImageFromName("hidden-on.png", "manager", 32, 32)));
        } else {
            hidden.setGraphic(new ImageView(getImageFromName("hidden.png", "manager", 32, 32)));
        }
        isHiddenFilter = hidden.isSelected();
        refreshList();
    }

    public void event(MouseEvent mouseEvent) {
        ContextMenuForFiles.getContextMenuForFiles().hide();
        ContextMenuForDirectory.getDirContext().hide();
        ContextMenuForTrash.getContextMenuForTrash().hide();
        try {
            if (clicked == null) {
                selected = null;
            } else {
                selected = new File(lastPath.getText() + "/" + clicked.getText());
                labelSelected.setText(String.valueOf(selected));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (mouse == 2 && mouseEvent.getButton() == MouseButton.PRIMARY) {
            assert selected != null;
            path.setText(selected.toString());
            file = new File(path.getText().trim());
            if (file.isDirectory()) {
                lastPathDir = null;
                forwardButton.setDisable(true);
                objects = 0;
                refresh(this.flowPane, path, lastPath, goPatch, isHiddenFilter,loadedFiles);
                selected = null;
            } else {
                System.out.println("Nie jest to katalog mogę tylko wykonać");
                runInProgram(file);
                file = new File(file.getParent());
                path.setText(file.getPath()); // Fixes a bug related to hidden files
                selected = null;
            }
        }
        if (!(clicked == null) && mouseEvent.getButton() == MouseButton.SECONDARY) {
            if (selected.isDirectory() && !path.getText().equals(FileSys.HOME.getPath() + FileSys.TRASH.getPath())) {
                ContextMenuForDirectory.getDirContext().show(flowPane, mouseEvent.getScreenX(), mouseEvent.getScreenY());

            } else if (selected.isDirectory() || selected.isFile() && path.getText().equals(FileSys.HOME.getPath() + FileSys.TRASH.getPath())) {
                ContextMenuForFiles.getContextMenuForFiles().hide();
                ContextMenuForFiles.getContextMenuForFiles().hide();
                ContextMenuForTrash.getContextMenuForTrash().show(flowPane, mouseEvent.getScreenX(), mouseEvent.getScreenY());
            } else {
                Config config = new Config();
                if (config.loadAddedProgram(selected.getName()) != null) {
                    contextMenuForFiles.resetList();
                    MenuItem menuItem = new MenuItem(config.loadAddedProgram(selected.getName()));
                    contextMenuForFiles.contextMenuForFile(Optional.of(menuItem));
                    ContextMenuForFiles.getContextMenuForFiles().show(flowPane, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                } else if (config.loadAddedProgram(selected.getName()) == null) {
                    contextMenuForFiles.resetList();
                    contextMenuForFiles.contextMenuForFile(Optional.empty());
                    ContextMenuForFiles.getContextMenuForFiles().show(flowPane, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                }
            }
        }
        if (ContextMenuForFiles.contextMenuForFiles.isShowing() || ContextMenuForDirectory.getDirContext().isShowing()) {
            ContextMenuForFlowPane.getContextMenuFlowPane().hide();
        } else if (mouseEvent.getButton() == MouseButton.SECONDARY && !path.getText().equals(FileSys.HOME.getPath() + FileSys.TRASH.getPath())) {
            ContextMenuForFlowPane.getContextMenuFlowPane().show(flowPane, mouseEvent.getScreenX(), mouseEvent.getScreenY());
        }
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            ContextMenuForFlowPane.getContextMenuFlowPane().hide();
        }
    }

    public void author() {
        ImageView imageView = new ImageView(getImageFromName("java.png", 96, 96));
        showAlerts.Alert(Alert.AlertType.INFORMATION, "'SylwesterFileManager' SFM to program który umożliwia obsługę plików.\n Autor: Sylwester Gawroński", "Wersja:\n " +
                "0.9.0.5", "O programie", imageView);
    }

    /**
     * Automatic refresh view when it detects that the number of labels does not match the number of files
     * or else
     */
   synchronized void autoUpdateSystem() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(AutoRefresh.getMilis());//
                    FileFilter fileFilter = pathname -> pathname.isHidden() ? isHiddenFilter : true; // wypierdol do nowej klasy bez sensu pisać do 20 razy
                    fileStatusControl = new File(path.getText());
                    File[] filesList = file.listFiles(fileFilter);
                    try {
                        assert filesList != null;
                        actObjects = filesList.length;
                    } catch (NullPointerException e) {
                        actObjects = 0;
                    }
                    backButton.setDisable(path.getText().equals("/"));
                } catch (InterruptedException e) {
                    showAlerts.Alert(Alert.AlertType.ERROR, "Bład systemu automatycznego odświerzania widoku", e.getMessage(), "Błąd wątku");
                }
                Platform.runLater(() -> { // update view
                    if (objects != actObjects) {
                        refresh(this.flowPane, path, lastPath, goPatch, isHiddenFilter,loadedFiles);
                        System.out.println("Auto Refresh");
                    }
                });
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
    // END
}