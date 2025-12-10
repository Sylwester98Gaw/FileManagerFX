/*
 * Copyright (c) 2024. This code is my creation, my passion, and my contribution to the community. It's open for all to use, learn, and grow from. I sign it with my name proudly, committed to supporting freedom and collaboration in the world of programming.
 */

package script.labels;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import org.apache.commons.io.FilenameUtils;
import script.helpers.*;
import script.properties.Config;

public class Labels extends ShowIcons{
    public static int objects = 0;
    static double translateX;
    static double last_TranslateX;
    static double translateY;
    static double last_TranslateY;
    public static Label clicked;

    ArrayList<Label> specialViewList = new ArrayList<>();
    public static int mouse;
    BlinkingLabel blinkingLabel = new BlinkingLabel();
    private Image image;
    private int filesLoadedLenght = 1;
    private int filesLenght = 0;
    ShowAlerts showAlerts = new ShowAlerts();
   // GetSize getSize = new GetSize();
    StringBuilder name;
    /**
     *
     * @param flowPane todo description
     * @param path todo description
     * @param lastpaths todo description
     * @param textField todo description
     * @param isHiddenFilter todo description
     * @param loadedFiles todo description
     */
    public void refresh(FlowPane flowPane, Label path, Label lastpaths, TextField textField, Boolean isHiddenFilter,Label loadedFiles) {
        FileFilter fileFilter = pathname -> pathname.isHidden() ? isHiddenFilter.booleanValue() : true;
        flowPane.getChildren().clear();
        filesLoadedLenght = 1;
        filesLenght = 0;
        File directoryPath = new File(path.getText());
        File[] filesList = directoryPath.listFiles(fileFilter);
        try {
            if (filesList.length == 0 ){
                loadedFiles.setText("Katalog jest pusty");
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            loadedFiles.setText("Brak dostępu");
            showAlerts.Alert(Alert.AlertType.ERROR,"Brak dostępu ! Brak uprawnień",e.getMessage(), "Poważny błąd");
            path.setText(FileSys.HOME.getPath());
        }
        filesLenght = filesList.length;
        try {
            objects = filesList.length;
            Arrays.sort(filesList);
            Thread thread = new Thread(() -> {
                for (File file : filesList) {
//                    name = null;
//                    if (file.getName().length()>10){
//                        name = new StringBuilder(file.getName()).insert(15,"\n"); TEST
//                    }else {
//                        name = new StringBuilder(file.getName());
//                    }
                    Label label = new Label(file.getName());
                    label.setWrapText(true);
//                    ImageView icon = (ImageView) FileSystemView.getFileSystemView().getSystemIcon(file,48,48); // WARNING new icon view has bugs
                    label.setContentDisplay(ContentDisplay.LEFT); // set display
                    if (GetMultipleSelect.getCheckBox().isSelected()){
                        if (specialViewList.contains(label)){
                            label.setStyle("-fx-background-color: #28a300; -fx-background-radius: 5;");
                        }
                    }
                    if (!isHiddenFilter) { // load translate and color - view settings ignore when hidden filter is true
                        Config config = new Config();
                        label.setTranslateX(config.loadTranslate(true, file.getName()));
                        label.setTranslateY(config.loadTranslate(false, file.getName()));
                        colorView(label,file);
                    }
                    if (file.isHidden()) { // showing hidden dirs and files
                        label.setTextFill(Colors.HIDDEN.getColor());
                        label.setGraphic(new ImageView(getImageExec("bin.png")));
                        if (file.isDirectory()) {
                            label.setTextFill(Colors.HIDDEN.getColor());
                            label.setGraphic(new ImageView(getImageExec("folder.png")));
                        }
                        if (GetBlinking.getBlinking().equals("true")) { // Checks if blinking is enable
                            blinkingLabel.blinkingHidden(label);
                        }
                    } else if (file.isDirectory()) {
                        if (GetTheme.isLight()){
                            label.setTextFill(Colors.DEFAULT.getColor());
                        }else {
                            label.setTextFill(Colors.WHITE.getColor());
                        }
                        label.setGraphic(new ImageView(getImageExec("folder.png")));
                      //  label.setGraphic(new ImageView(String.valueOf(icon))); // WARNING new icon view has bugs
                    } else {
                        label.setTextFill(Colors.FILE.getColor());
                        String extension = FilenameUtils.getExtension(file.getName());
                        switch (extension) {
                            case "bin":
                                label.setGraphic(new ImageView(getImageExec("bin.png")));
                                break;
                            case "cmd":
                                label.setGraphic(new ImageView(getImageExec("cmd.png")));
                                break;
                            case "jar":
                                label.setGraphic(new ImageView(getImageExec("jar.png")));
                                break;
                            case "java":
                                label.setGraphic(new ImageView(getImageExec("java.png")));
                                break;
                            case "json":
                                label.setGraphic(new ImageView(getImageExec("json.png")));
                                break;
                            case "app":
                            case "AppImage":
                            case "desktop":
                            case "x86":
                            case "x86_64":
                                label.setGraphic(new ImageView(getImageExec("app.png")));
                                label.setTextFill(Colors.APP.getColor());
                                break;
                            case "config":
                            case "properties":
                                label.setGraphic(new ImageView(getImageExec("config.png")));
                                break;
                            case "zip":
                                label.setGraphic(new ImageView(getImageExec("zip.png")));
                                break;
                            case "rar":
                                label.setGraphic(new ImageView(getImageExec("rar.png")));
                                break;
                            case "xz":
                                label.setGraphic(new ImageView(getImageExec("xz.png")));
                                break;
                            case "gz":
                                label.setGraphic(new ImageView(getImageExec("gz.png")));
                                break;
                            case "xml":
                                label.setGraphic(new ImageView(getImageExec("xml.png")));
                                break;
                            case "deb":
                                label.setGraphic(new ImageView(getImageExec("deb.png")));
                                break;
                            case "gitignore":
                                label.setGraphic(new ImageView(getImageExec("gitignore.png")));
                                break;
                            case "MOV":
                                label.setGraphic(new ImageView(getImageExec("mov.png")));
                                break;
                            case "mp3":
                                label.setGraphic(new ImageView(getImageExec("mp3.png")));
                                break;
                            case "mp4":
                                label.setGraphic(new ImageView(getImageExec("mp4.png")));
                                break;
                            case "mpeg":
                                label.setGraphic(new ImageView(getImageExec("mpeg.png")));
                                break;
                            case "html":
                                label.setGraphic(new ImageView(getImageExec("html.png")));
                                break;
                            case "bash":
                            case "sh":
                                label.setGraphic(new ImageView(getImageExec("bash.png")));
                                break;
                            case "avi":
                                label.setGraphic(new ImageView(getImageExec("avi.png")));
                                break;
                            case "jpg":
                            case "jpeg":
                            case "png":
                                try {
                                    byte[] imageBytes = java.nio.file.Files.readAllBytes(file.toPath());
                                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
                                    ByteArrayInputStream byteArrayInputStreamBig = new ByteArrayInputStream(imageBytes);
                                    labelWithTooltipImage(label,byteArrayInputStreamBig);
                                    image = new Image(byteArrayInputStream, Double.parseDouble(Config.getFileX()), Double.parseDouble(Config.getFileY()), true, true);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                label.setGraphic(new ImageView(image));
                                break;
                            case "txt":
                                label.setGraphic(new ImageView(getImageExec("txt.png")));
                                break;
                            case "class":
                                label.setGraphic(new ImageView(getImageExec("class.png")));
                                break;
                            case "so":
                            case "o":
                            case "0":
                            case "1":
                            case "2":
                            case "3":
                            case "4":
                            case "5":
                            case "6":
                            case "7":
                            case "8":
                            case "9":
                                label.setGraphic(new ImageView(getImageExec("so.png")));
                                break;
                            case "conf":
                                label.setGraphic(new ImageView(getImageExec("conf.png")));
                                break;
                            case "data":
                                label.setGraphic(new ImageView(getImageExec("data.png")));
                                break;
                            case "wav":
                                label.setGraphic(new ImageView(getImageExec("wav.png")));
                                break;
                            case "pdf":
                                label.setGraphic(new ImageView(getImageExec("pdf.png")));
                                break;
                            case "dll":
                                label.setGraphic(new ImageView(getImageExec("dll.png")));
                                break;
                            case "dat":
                                label.setGraphic(new ImageView(getImageExec("dat.png")));
                                break;
                            case "cfg":
                                label.setGraphic(new ImageView(getImageExec("cfg.png")));
                                break;
                            case "exe":
                                label.setGraphic(new ImageView(getImageExec("exe.png")));
                                break;
                            default:
                                label.setGraphic(new ImageView(getImageExec("blank.png")));
                                break;
                        }
                    }

                    label.setOnMouseEntered(_ -> entered(label,file));
                    label.setOnMouseExited(_ -> exited(label,file,isHiddenFilter));
                    if (GetMove.getMoveIcon().equals("true")) {
                        label.setOnMousePressed(mouseEvent -> press(label, mouseEvent));
                        label.setOnMouseReleased(mouseEvent -> released(label, mouseEvent));
                    }
                    Platform.runLater(() -> { // update view
                        flowPane.getChildren().add(label);
                        textField.setText(path.getText());
                        lastpaths.setText(path.getText());
                        if (filesLenght != filesLoadedLenght){
                            loadedFiles.setText(((filesLoadedLenght++)+" Ładuje..."));
                            double progress = (double) filesLoadedLenght / filesLenght;
                            GetProgress.getProgressBar().setProgress(progress);
                            if (filesLoadedLenght > filesLenght){
                                refresh(flowPane,path,lastpaths,textField,isHiddenFilter,loadedFiles);
                            }
                        }else {
                            loadedFiles.setText(((filesLoadedLenght++)+" Element-ów"));
                            GetProgress.progressBar.setProgress(0);
                        }

                    });

                }
            });
            thread.start();
        } catch (NullPointerException e) {
            objects = 0;
        }
    }

    private void labelWithTooltipImage(Label label, ByteArrayInputStream byteArrayInputStream) {
        image = new Image(byteArrayInputStream, 296,296, true, true);
        Tooltip tooltip = new Tooltip("Podgląd: "+label.getText());
        tooltip.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        tooltip.setGraphic(new ImageView(image));
        label.setTooltip(tooltip);
    }

    private void colorView(Label label,File file){
        Config config = new Config();
        String clr = config.loadFileViewColor(file.getName());
        if (!(clr == null)){
            switch (clr){
                case "RED":
                    label.setStyle("-fx-background-color: red; -fx-background-radius: 5;");
                    break;
                case "BLUE":
                    label.setStyle("-fx-background-color: blue; -fx-background-radius: 5;");
                    break;
                case "GREEN":
                    label.setStyle("-fx-background-color: green; -fx-background-radius: 5;");
                    break;
                case "SILVER":
                    label.setTextFill(Colors.DEFAULT.getColor());
                    label.setStyle("-fx-background-color: silver; -fx-background-radius: 5;");
                    break;
                case "WHITE":
                    label.setTextFill(Colors.DEFAULT.getColor());
                    label.setStyle("-fx-background-color: white; -fx-background-radius: 5;");
                    break;
                case "YELLOW":
                    label.setTextFill(Colors.DEFAULT.getColor());
                    label.setStyle("-fx-background-color: yellow; -fx-background-radius: 5;");
                    break;
                case "PURPLE":
                    label.setStyle("-fx-background-color: purple; -fx-background-radius: 5;");
                    break;
                case "none":
                    label.setStyle("-fx-background-color: transparent;");
                    break;
            }
        }
    }
    private void entered(Label label,File file) {
        label.setCursor(Cursor.HAND);
        label.setOnMouseClicked(mouseEvent -> {
            clicked = label;
            label.setStyle("-fx-background-color: #668cff; -fx-background-radius: 5;");
            if (file.isDirectory()){
                label.setGraphic(new ImageView(getImageExec("folder-open.png")));
            }
            mouse = mouseEvent.getClickCount();
            if (mouse >= 2) {
                label.setStyle("-fx-background-color: #28a300; -fx-background-radius: 5;");
            }
            if (GetMultipleSelect.getCheckBox().isSelected() && mouseEvent.getButton() == MouseButton.PRIMARY){
                if (mouse >=1) {
                    if (checkItem(label)){
                        removeSelectedFromList(label);
                    }else {
                        addSelectedToList(label);
                    }
                }
            }
        });
        if(!GetMultipleSelect.getCheckBox().isSelected()){
            removeAll();
            if (GetTheme.isLight()){
                label.setStyle("-fx-background-color: #DFDFDF; -fx-background-radius: 5;");
            }else {
                label.setStyle("-fx-background-color: #444444; -fx-background-radius: 5;");
            }

        }
    }
    private void exited(Label label,File file,Boolean isHidden) {
        clicked = null; // this fix a bug
        mouse = 0;
        if (file.isDirectory()){
            label.setGraphic(new ImageView(getImageExec("folder.png")));
        }
        if (!specialViewList.contains(label)){
            label.setStyle("-fx-background-color: transparent;");
            if (!isHidden){
                colorView(label,file);
            }

        }
    }

    private void press(Label label, MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            last_TranslateX = mouseEvent.getSceneX();
            last_TranslateY = mouseEvent.getSceneY();
            translateX = label.getTranslateX() - mouseEvent.getSceneX();
            translateY = label.getTranslateY() - mouseEvent.getSceneY();
        }
    }

    private void released(Label label, MouseEvent mouseEvent) {
        Config config = new Config();
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            last_TranslateY = last_TranslateY - mouseEvent.getSceneY();
            last_TranslateX = last_TranslateX - mouseEvent.getSceneX();
            last_TranslateX = Math.abs(last_TranslateX);
            last_TranslateY = Math.abs(last_TranslateY);
            if (last_TranslateX > 20) {
                label.setTranslateX(mouseEvent.getSceneX() + translateX);
                config.saveTranslate(label.getTranslateX(), label.getTranslateY(), label.getText());
            }
            if (last_TranslateY > 20) {
                label.setTranslateY(mouseEvent.getSceneY() + translateY);
                config.saveTranslate(label.getTranslateX(), label.getTranslateY(), label.getText());
            }
        }
    }
    public void addSelectedToList(Label item){
        item.setStyle("-fx-background-color: #28a300; -fx-background-radius: 5;");
        specialViewList.add(item);
        System.out.println(specialViewList);
    }
    public void removeSelectedFromList(Label item){
        specialViewList.remove(item);
        System.out.println(specialViewList);
    }
    public boolean checkItem (Label item){
        return specialViewList.contains(item);
    }
    public void removeAll(){
        try {
            for (int i = 0; i < specialViewList.size(); i++) {
                specialViewList.remove(i);
            }
            if (!specialViewList.isEmpty()){
                removeAll();
            }
        }catch (Exception e){
            e.getStackTrace();
        }
    }
}
