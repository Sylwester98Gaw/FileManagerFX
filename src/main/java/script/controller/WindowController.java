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
import javafx.scene.input.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import org.apache.commons.io.FilenameUtils;
import script.contextMenus.*;
import script.helpers.GetTheme;
import script.labels.Labels;
import script.namespacesSecurity.NamesChecker;
import script.properties.Config;

import script.actions.*;
import script.exec.ExecuteCommand;
import script.helpers.*;

public class WindowController extends Labels implements Initializable, Runnable {
    ExecuteCommand executeCommand = new ExecuteCommand();
    AddToList addToList = new AddToList();
    Config config = new Config();
    ShowAlerts showAlerts = new ShowAlerts();
    ContextMenuForFiles contextMenuForFiles;
    ContextMenuForFlowPane contextMenuForFlowPane;
    ContextMenuForDirectory contextMenuForDirectory;
    //
    private String lastPathDir;
    private final boolean updateCountEnable = false;
    public static File selected;
    public static File file;
    public static File fileStatusControl;
    public boolean isHiddenFilter = false;
    public static int actObjects;
    private int updateCount;
    /*
    FXML
     */
    public Button deleteAllTrash;
    public ListView<Label> bookmarks;
    public Label path;
    public Label copyItemName;
    public Label lastPath;
    public Label labelSelected;
    public TextField goPatch;
    public Button goButton;
    public Button backButton;
    public Button forwardButton;
    public MenuItem authorButton;
    public Button terminalButton;
    public CheckBox hidden;
    public FlowPane flowPane;
    public VBox vBox;
    public ImageView status; // coping icon ? To remove ?
    public ProgressBar progressBar;
    public Label loadedFiles;
    public CheckBox checkMultipleSelect;
    ///
    public void run() {
        contextMenuForFlowPane = new ContextMenuForFlowPane();

        contextMenuForDirectory = new ContextMenuForDirectory();

        ContextMenuForTrash contextMenuForTrash = new ContextMenuForTrash();
        contextMenuForTrash.contextMenuForTrash();

        ContextMenuMultipleSelect contextMenuMultipleSelect = new ContextMenuMultipleSelect();
        contextMenuMultipleSelect.contextMenuMultipleSelect();
        contextMenuForFiles = new ContextMenuForFiles();

        GetProgress.setProgressBar(progressBar);
        GetBookmarksList.setBookmark(bookmarks);
        GetMultipleSelect.setCheckBox(checkMultipleSelect);
        path.setText(Arg.getArgument()); // start position
        file = new File(path.getText());

        status.setImage(getImageFromName("java.png", 96, 96));
        goButton.setGraphic(new ImageView(getImageFromName("go.png", "manager", 26, 26)));
        authorButton.setGraphic(new ImageView(getImageFromName("info.png", "manager", 16, 16)));
        backButton.setGraphic(new ImageView(getImageFromName("back.png", "manager", 16, 16)));
        forwardButton.setGraphic(new ImageView(getImageFromName("forward.png", "manager", 16, 16)));
        terminalButton.setGraphic(new ImageView(getImageFromName("term.png", "manager", 16, 16)));
        checkMultipleSelect.setGraphic(new ImageView(getImageFromName("multiple.png", "manager", 16, 16)));
        hidden.setGraphic(new ImageView(getImageFromName("hidden.png", "manager", 32, 32)));
        deleteAllTrash.setGraphic(new ImageView(getImageFromName("trashButton.png", "manager", 16, 16)));
        deleteAllTrash.setVisible(false);

        goButton.setOnMouseEntered(_ -> buttonEntered(goButton));
        goButton.setOnMouseExited(_ -> buttonExited(goButton));
        backButton.setOnMouseEntered(_ -> buttonEntered(backButton));
        backButton.setOnMouseExited(_ -> buttonExited(backButton));
        terminalButton.setOnMouseEntered(_ -> buttonEntered(terminalButton));
        terminalButton.setOnMouseExited(_ -> buttonExited(terminalButton));
        forwardButton.setOnMouseEntered(_ -> buttonEntered(forwardButton));
        forwardButton.setOnMouseExited(_ -> buttonExited(forwardButton));
        forwardButton.setDisable(true);

        refresh(flowPane, path, lastPath, goPatch, isHiddenFilter, loadedFiles);
        createBookmarks();
        createTooltips();
        flowPaneColor();
        autoRefreshSystem();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        run();
    }

    private void clipboardAddFile(String file){
        ClipboardContent clipboardContent = new ClipboardContent();
        if (GetMultipleSelect.getCheckBox().isSelected()){
            clipboardContent.putFiles(AddToList.list);
            Clipboard.getSystemClipboard().setContent(clipboardContent);
        }else {
            List<String> list = new ArrayList<>();
            list.add(file);
            clipboardContent.putFilesByPath(list);
            Clipboard.getSystemClipboard().setContent(clipboardContent);
            list.clear();
        }
    }

    // test zmiany koloru flowPane dla konkretnego katalogu TODO
    private void flowPaneColor() {
        if (path.getText().equals(FileSys.HOME.getPath()+FileSys.TRASH.getPath())) {
            flowPane.setStyle("-fx-background-color:" + "rgba(72,69,73,0.62)");
        }else {
            if (GetTheme.isLight()){
                loadedFiles.setTextFill(Colors.DEFAULT.getColor());
                flowPane.setStyle("-fx-background-color:"+config.getFlowPane_CSS_Light());
            }else {
                flowPane.setStyle("-fx-background-color:"+config.getFlowPane_CSS_Dark());
            }
        }
    }

    void buttonEntered(Button button) {
        if (GetTheme.isLight()){
            button.setStyle("-fx-background-color:"+config.getButtonEntered_CSS_Light());
        }else {
            button.setStyle("-fx-background-color:"+config.getButtonEntered_CSS_Dark());
        }
    }

    void buttonExited(Button button) {
        if (GetTheme.isLight()){
            button.setStyle("-fx-background-color:"+config.getButtonExited_CSS_Light());
        }else {
            button.setStyle("-fx-background-color:"+config.getButtonExited_CSS_Dark());
        }

    }

    void createTooltips() {
        Tooltip tooltipBackButton = new Tooltip("Wróć");
        Tooltip tooltipForwardButton = new Tooltip("Wcześniej otwarty");
        Tooltip tooltipGoButton = new Tooltip("Przejdź do...");
        Tooltip tooltipHidden = new Tooltip("Ukryte pliki/katalogi");
        Tooltip tooltipTerminal = new Tooltip("Terminal");
        Tooltip tooltipTrashButton = new Tooltip("Opróżni kosz");
        Tooltip tooltipMultiple = new Tooltip("Tryb zaznaczania");
        Tooltip tooltipLoaded = new Tooltip("Liczba załadowanych elementów");
        terminalButton.setTooltip(tooltipTerminal);
        hidden.setTooltip(tooltipHidden);
        goButton.setTooltip(tooltipGoButton);
        backButton.setTooltip(tooltipBackButton);
        forwardButton.setTooltip(tooltipForwardButton);
        checkMultipleSelect.setTooltip(tooltipMultiple);
        loadedFiles.setTooltip(tooltipLoaded);
        deleteAllTrash.setTooltip(tooltipTrashButton);
    }

    void createBookmarks() {
        executeCommand.commandsWithResult("lsblk --output MOUNTPOINTS");
        try {
            Label fileSys = new Label("System");
            Label home = new Label("Home");
            Label trash = new Label("Trash");
            fileSys.setGraphic(new ImageView(getImageBookmarks("system.png")));
            home.setGraphic(new ImageView(getImageBookmarks("home.png")));
            trash.setGraphic(new ImageView(getImageBookmarks("trash.png")));
            GetBookmarksList.getBookmark().getItems().add(fileSys);
            GetBookmarksList.getBookmark().getItems().add(home);
            GetBookmarksList.getBookmark().getItems().add(trash);

            for (int i = 0; i < executeCommand.getList().size(); i++) {
                Label mountPoints = new Label(executeCommand.getList().get(i).toString());
                Tooltip tooltip = new Tooltip(executeCommand.getList().get(i).toString());
                mountPoints.setTooltip(tooltip);
                mountPoints.setGraphic(new ImageView(getImageBookmarks("disk.png")));
                GetBookmarksList.getBookmark().getItems().add(mountPoints);
            }
            for (String addedBookmark : Config.bookmarkMap.keySet()) {
                Tooltip tooltip = new Tooltip(addedBookmark);
                Label label = new Label(addedBookmark);
                label.setTooltip(tooltip);
                label.setGraphic(new ImageView(getImageBookmarks("mark.png")));
                GetBookmarksList.getBookmark().getItems().add(label);
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Przenoszenie do kosza");
        if (GetMultipleSelect.getCheckBox().isSelected()) {
            alert.setHeaderText("Przenoszenie do kosza " + AddToList.list.toString());
        } else {
            alert.setHeaderText("Przenoszenie do kosza " + path.getPath());
        }
        ButtonType okButton = new ButtonType("OK");
        ButtonType cancelButton = new ButtonType("Anuluj", ButtonType.CANCEL.getButtonData());
        alert.getButtonTypes().setAll(okButton, cancelButton);
        TrashIntegration trashIntegration = new TrashIntegration();
        alert.showAndWait().ifPresent(response -> {
            if (response == okButton)
                if (GetMultipleSelect.getCheckBox().isSelected()) {
                    trashIntegration.moveToTrashAll();
                } else {
                    trashIntegration.moveToTrash(path);
                }
        });
    }

    /*


     */
    public void contextMenuCreateDir(File path) {
        CreateDirOrFiles createDirectory = new CreateDirOrFiles();
        createDirectory.makeNewDirectory(path, true);
    }

    public void contextMenuCreateFile(File path) {
        CreateDirOrFiles createDirectory = new CreateDirOrFiles();
        createDirectory.makeNewDirectory(path, false);
    }

    public void setViewColor(String color, File path) {
        Config config = new Config();
        switch (color) {
            case "red":
                config.saveFileViewColor(String.valueOf(path), "RED");
                break;
            case "green":
                config.saveFileViewColor(String.valueOf(path), "GREEN");
                break;
            case "blue":
                config.saveFileViewColor(String.valueOf(path), "BLUE");
                break;
            case "silver":
                config.saveFileViewColor(String.valueOf(path), "SILVER");
                break;
            case "white":
                config.saveFileViewColor(String.valueOf(path), "WHITE");
                break;
            case "yellow":
                config.saveFileViewColor(String.valueOf(path), "YELLOW");
                break;
            case "purple":
                config.saveFileViewColor(String.valueOf(path), "PURPLE");
                break;
            case "none":
                config.saveFileViewColor(String.valueOf(path), "none");
                break;
        }
        objects++; // ! Force refresh
    }


//    public void contextMenuCreateZipFile() {
//      TODO !!!
//    }

    public void addDirToBookmarks() {
        Config config = new Config();
        config.saveBookmarks(selected.getName(), new File(selected.getPath()));
        Label label = new Label(selected.getName()+".properties");
        Tooltip tooltip = new Tooltip(selected.getName());
        label.setTooltip(tooltip);
        label.setGraphic(new ImageView(getImageBookmarks("mark.png")));
        GetBookmarksList.bookmark.getItems().add(label);
        config.loadAddedBookmarks();
    }

    public void contextMenuOpenTerminal(File path) {
        Terminal terminal = new Terminal();
        terminal.openTerminal(path);
    }

    public void contextMenuDeleteDir(File fileToDelete) {
        TrashIntegration trashIntegration = new TrashIntegration();
        trashIntegration.delete(fileToDelete);
    }
    public void contextMenuRestoreFromTrash(File path){
        TrashIntegration trashIntegration = new TrashIntegration();
        trashIntegration.restore(path);
    }

    public void contextMenuMove() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(FileSys.HOME.getPath()));
        directoryChooser.setTitle("Przenoszenie");
        Move move = new Move();
        if (GetMultipleSelect.getCheckBox().isSelected()) {
            move.moveAll(directoryChooser.showDialog(null), GetProgress.getProgressBar());
        } else {
            move.move(selected, directoryChooser.showDialog(null), GetProgress.getProgressBar());
        }
    }

    public void contextMenuCopy(File path) {
        clipboardAddFile(String.valueOf(path));
        if (GetMultipleSelect.getCheckBox().isSelected()){
            clipboardAddFile(null);
        }
    }

    public void contextMenuPaste(File where) {
        Copy copyAction = new Copy();
        if (!Clipboard.getSystemClipboard().hasFiles()){
            showAlerts.Alert(Alert.AlertType.INFORMATION,"Informacja","Schowek jest pusty !","Kopiowanie");
        }else {
            for (int i = 0; i < Clipboard.getSystemClipboard().getFiles().size(); i++) {
                File checkFile = Clipboard.getSystemClipboard().getFiles().get(i);
                if (checkFile.exists()){
                    copyAction.copy(Clipboard.getSystemClipboard().getFiles().get(i), where, GetProgress.getProgressBar());
                }else {
                    showAlerts.Alert(Alert.AlertType.WARNING,"Uwaga !","Schowek jest pusty ! Lub plik/katalog nie istnieje !","Kopiowanie");
                }
            }
        }
    }

    public void contextMenuRename(File path) {
        String extension = FilenameUtils.getExtension(path.getName());
        NamesChecker namesChecker = new NamesChecker();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Zmień nazwę");
        dialog.setHeaderText("Nowa nazwa dla " + path.getName());
        dialog.setContentText("Nowa nazwa: ");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(_ -> {
            if (!namesChecker.checkName(result.toString())) {
                Rename rename = new Rename(result.get(), extension, selected);
                rename.renameFile();
                objects++; // Force refresh
            } else {
                namesChecker.badName();
            }
        });
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
    // TODO rozwinąć okno właściwości !!
    public void contextMenuGetProperties(File file) {
        GetSize getSize = new GetSize();
        System.out.println(file);
        String name;
        if (file.isDirectory()) {
            name = "Katalog '";
        } else {
            name = "Plik '";
        }
        showAlerts.Alert(Alert.AlertType.INFORMATION, file.getName(), name + file.getName() + "' Zajmuje " +
                getSize.getMb(file) +
                " MB, " + getSize.getKb(file) +
                " KB, " + getSize.getSize(file) +
                " B, " + "Można wykonać " + file.canExecute()
                ,
                "Właściwości");
    }

    public void goButtAction() {
        String patchName = file.getPath(); // Stores the previous name in case of an error, so that it can be used to prevent the program from getting stuck in a refresh loop
        try {
            if (goPatch.getText().contains("?")){
                StringBuilder stringBuilder = new StringBuilder(goPatch.getText());
                stringBuilder.deleteCharAt(0);
                System.out.println(stringBuilder);
                System.out.println("wyszukiwanie w danym katalogu");
                //TODO
            }else {
                file = new File(goPatch.getText());
                if (file.exists()) {
                    path.setText(file.getPath());
                    System.out.println(file.getPath()+" 'Wyszkano'");
                    refreshList();
                } else {
                    file = new File(patchName);
                    showAlerts.Alert(Alert.AlertType.INFORMATION, "Katalog nie istnieje", "Ścieżka nie jest prawidłowa", "Informacja");
                }
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

    /// ////////////////////////////////////
    /// OPENING PROGRAMS
    /// ///////////////////////////////////
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

    /// ///////////////////////////////////////
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
                    refreshList();
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
            if (lastPathDir != null) {
                selected = null;
                path.setText(lastPathDir);
                file = new File(path.getText().trim());
                objects = 0;
                lastPathDir = null;
                forwardButton.setDisable(true);
                refreshList();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();

        }
    }
    //TODO
    public void goToBookmarksDir(MouseEvent mouseEvent) {
        try {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                path.setText(getBookmarkDir());
                goPatch.setText(getBookmarkDir());
                file = new File(path.getText());
                backButton.setDisable(path.getText().equals("/"));
                refreshList();
            } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                System.out.println(this.bookmarks.getSelectionModel().getSelectedItem());
            }
        } catch (NullPointerException e) {
            path.setText(bookmarks.getSelectionModel().getSelectedItem().getText());
            goPatch.setText(bookmarks.getSelectionModel().getSelectedItem().getText());
            file = new File(path.getText());
            backButton.setDisable(path.getText().equals("/"));
            refreshList();
        }

    }

    public String getBookmarkDir() {
        String value = (bookmarks.getSelectionModel().getSelectedItem()).getText();
        lastPathDir = null;
        switch (value) {
            case "Home":
                forwardButton.setDisable(true);
                return FileSys.HOME.getPath();
            case "System":
                forwardButton.setDisable(true);
                return FileSys.SYSTEM.getPath();
            case "Trash":
                forwardButton.setDisable(true);
                return FileSys.HOME.getPath() + FileSys.TRASH.getPath();
        }
        return Config.bookmarkMap.get(value);
    }

    public void refreshList() {//TODO fileFilter modification
        setTooltipTextField();
        refresh(this.flowPane, this.path, this.lastPath, this.goPatch, this.isHiddenFilter, this.loadedFiles);
        deleteAllTrash.setVisible(path.getText().equals(FileSys.HOME.getPath() + FileSys.TRASH.getPath()) && file.length() >= 1);
        flowPaneColor();
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

    public void multipleAction(ActionEvent actionEvent) {
        addToList.removeAll();
        refreshList();
    }

    /// ///////////
    /// ///////////MOUSE EVENTS
    /// ///////////
    public void globalEvent(MouseEvent mouseEvent) {
        ContextMenuForFiles.getContextMenuForFiles().hide();
        ContextMenuForDirectory.getDirContext().hide();
        ContextMenuForTrash.getContextMenuForTrash().hide();
        ContextMenuMultipleSelect.getContextMenuMultipleSelect().hide();
        ContextMenuForFlowPane.getContextMenuFlowPane().hide();

        if (!checkMultipleSelect.isSelected()) {
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
                primaryTwoClickEvent();
            }

            if (!(clicked == null) && mouseEvent.getButton() == MouseButton.SECONDARY) {
                secondaryClickEvent(mouseEvent);
            }
            //////////////////////////////////////////// contextmenus other
            if (ContextMenuForFiles.contextMenuForFiles.isShowing() || ContextMenuForDirectory.getDirContext().isShowing()) {
                ContextMenuForFlowPane.getContextMenuFlowPane().hide();
            } else if (mouseEvent.getButton() == MouseButton.SECONDARY && !path.getText().equals(FileSys.HOME.getPath() + FileSys.TRASH.getPath())) {
                contextMenuForFlowPane.resetList();
                contextMenuForFlowPane.flowPaneContextMenu();
                ContextMenuForFlowPane.getContextMenuFlowPane().show(flowPane, mouseEvent.getScreenX(), mouseEvent.getScreenY());
            }
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                ContextMenuForFlowPane.getContextMenuFlowPane().hide();
            }
        } else { // multiple select event
            try {
                multipleSelectEvent(mouseEvent, addToList, path, clicked, flowPane);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * @param mouseEvent todo description
     * @param addToList todo description
     * @param path todo description
     * @param clicked todo description
     * @param flowPane todo description
     */
    public void multipleSelectEvent(MouseEvent mouseEvent, AddToList addToList, Label path, Label clicked, FlowPane flowPane) {
        if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            ContextMenuMultipleSelect.contextMenuMultipleSelect.show(flowPane, mouseEvent.getScreenX(), mouseEvent.getScreenY());
        } else if (mouseEvent.getButton() == MouseButton.PRIMARY && !(clicked.getText() == null)) {
            ContextMenuMultipleSelect.getContextMenuMultipleSelect().hide();
            if (addToList.checkItem(path.getText() + "/" + clicked.getText())) {
                addToList.removeSelectedFromList(path.getText() + "/" + clicked.getText());
            } else {
                addToList.addSelectedToList(path.getText() + "/" + clicked.getText());
            }
        }
    }

    public void primaryTwoClickEvent() {
        if (!(selected == null)) {
            path.setText(selected.toString());
            file = new File(path.getText().trim());
            if (file.isDirectory()) {
                lastPathDir = null;
                forwardButton.setDisable(true);
                objects = 0;
                refreshList();
                selected = null;
            } else {
                System.out.println("Nie jest to katalog mogę tylko wykonać");
                runInProgram(file);
                file = new File(file.getParent());
                path.setText(file.getPath()); // Fixes a bug related to hidden files
                selected = null;
            }
        }
    }

    public void secondaryClickEvent(MouseEvent mouseEvent) {
        if (selected.isDirectory() && !path.getText().equals(FileSys.HOME.getPath() + FileSys.TRASH.getPath())) {
            contextMenuForDirectory.resetList();
            contextMenuForDirectory.contextMenuForDirectory();
            ContextMenuForDirectory.getDirContext().show(flowPane, mouseEvent.getScreenX(), mouseEvent.getScreenY());

        } else if (selected.isDirectory() || selected.isFile() && path.getText().equals(FileSys.HOME.getPath() + FileSys.TRASH.getPath())) {
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
//////////////
//////////////MOUSE EVENTS
//////////////

    /**
     * Automatic refresh view when it detects that the number of labels does not match the number of files
     * or else
     * ....
     * obiects = loaded files
     * actObjects = number of real files
     */
    void autoRefreshSystem() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(config.getAutoRefreshMilis());
                    if (updateCountEnable) {
                        updateCount++;
                        System.out.println(updateCount + " SFM refresh count " + objects + actObjects);
                    }
                    FileFilter fileFilter = pathname -> pathname.isHidden() ? isHiddenFilter : true;
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
                        System.out.println("Wykryto nie zgodność liczba załadowanych elementów " + objects + " realna liczba plików " + actObjects + " Odświerzam");
                        refreshList();
                    }
                });
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
    /*
    Sets a tooltip when the length of characters exceeds a certain threshold.
     */
    void setTooltipTextField(){
        if (goPatch.getLength() >= 40) {
            Tooltip tooltip = new Tooltip(path.getText());
            goPatch.setTooltip(tooltip);
        }else {
            goPatch.setTooltip(null);
        }
    }
    public void deleteAllFromTrash(ActionEvent actionEvent) {
        TrashIntegration trashIntegration = new TrashIntegration();
        trashIntegration.deleteAllFromTrash();
    }

    public void author() {
        String systemEv = System.getenv("XDG_CURRENT_DESKTOP");
        ImageView imageView = new ImageView(getImageFromName("imgs.png", 96, 96));
        showAlerts.Alert(Alert.AlertType.INFORMATION, "'SylwesterFileManager' SFM to program który umożliwia obsługę plików.\nAutor: Sylwester Gawroński", "SFM version : " +
                "0.9.0.8\n" +"Java version : "+ System.getProperty("java.version")
                +"\nOs name : "
                +System.getProperty("os.name")+"\nOs arch : "
                +System.getProperty("os.arch")+"\nKernel version : "
                +System.getProperty("os.version")+
                "\nenv : "+systemEv+
                "\n<><><><><><><><><><>"
                +"\nGitHub :Sylwester98Gaw/FileManagerFX", "O programie", imageView);
    }
    //
    // END
}
