/*
 * Copyright (c) 2024. This code is my creation, my passion, and my contribution to the community. It's open for all to use, learn, and grow from. I sign it with my name proudly, committed to supporting freedom and collaboration in the world of programming.
 */

package script.labels;

import java.io.*;
import java.util.Arrays;

import javafx.application.Platform;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import org.apache.commons.io.FilenameUtils;
import script.helpers.Colors;
import script.helpers.ShowIcons;
import script.properties.Config;

public class Labels extends ShowIcons{
    public static int objects = 0;
    public static int numberOfDirs = 0;
    public static int numberOfFiles = 0;
    static double translateX;
    static double last_TranslateX;
    static double translateY;
    static double last_TranslateY;
    public static Label clicked;
    public static String otherProgram;

    public static Label getClicked() {
        return clicked;
    }

    public static void setClicked(Label clicked) {
        Labels.clicked = clicked;
    }

    public static int getMouse() {
        return mouse;
    }

    public static void setMouse(int mouse) {
        Labels.mouse = mouse;
    }

    public static int mouse;
    BlinkingLabel blinkingLabel = new BlinkingLabel();
    private InputStream inputStream;
    private Image image;
    private int filesLoadedLenght = 1;
    private int filesLenght = 0;

    public synchronized void refresh(FlowPane flowPane, Label path, Label lastpaths, TextField textField, Boolean isHiddenFilter,Label loadedFiles) {
        FileFilter fileFilter = pathname -> pathname.isHidden() ? isHiddenFilter.booleanValue() : true;
        flowPane.getChildren().clear();
        filesLoadedLenght = 1;
        filesLenght = 0;
        File directoryPath = new File(path.getText());
        File[] filesList = directoryPath.listFiles(fileFilter);
        if (filesList.length == 0 ){
            loadedFiles.setText("Katalog jest pusty");
        }
        filesLenght = filesList.length;
        try {
            objects = filesList.length;
            Arrays.sort(filesList);
            Thread thread = new Thread(() -> {
                for (File file : filesList) {
                    Label label = new Label(file.getName());
                    label.setContentDisplay(ContentDisplay.LEFT); // set display
                    if (!isHiddenFilter) { // load translate
                        Config config = new Config();
                        label.setTranslateX(config.loadTranslate(true, file.getName()));
                        label.setTranslateY(config.loadTranslate(false, file.getName()));
                    }
                    if (file.isHidden()) { // showing hidden dirs and files
                        label.setTextFill(Colors.HIDDEN.getColor());
                        label.setGraphic(new ImageView(getImageFromNameFileIcons("bin.png")));
                        if (file.isDirectory()) {
                            label.setTextFill(Colors.HIDDEN.getColor());
                            label.setGraphic(new ImageView(getImageFromNameFileIcons("folder.png")));
                        }
                        if (GetBlinking.getBlinking().equals("true")) { // Checks if blinking is enable
                            blinkingLabel.blinkingHidden(label);
                        }
                    } else if (file.isDirectory()) {
                        label.setTextFill(Colors.WHITE.getColor());
                        label.setGraphic(new ImageView(getImageFromNameFileIcons("folder.png")));
                    } else {
                        label.setTextFill(Colors.FILE.getColor());
                        String extension = FilenameUtils.getExtension(file.getName());
                        switch (extension) {
                            case "bin":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("bin.png")));
                                break;
                            case "cmd":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("cmd.png")));
                                break;
                            case "jar":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("jar.png")));
                                break;
                            case "java":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("java.png")));
                                break;
                            case "json":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("json.png")));
                                break;
                            case "app":
                            case "AppImage":
                            case "desktop":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("app.png")));
                                label.setTextFill(Colors.APP.getColor());
                                break;
                            case "config":
                            case "properties":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("config.png")));
                                break;
                            case "zip":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("zip.png")));
                                break;
                            case "rar":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("rar.png")));
                                break;
                            case "xz":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("xz.png")));
                                break;
                            case "xml":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("xml.png")));
                                break;
                            case "deb":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("deb.png")));
                                break;
                            case "gitignore":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("gitignore.png")));
                                break;
                            case "png":
                                try {
                                    inputStream = new FileInputStream(file.getAbsolutePath());
                                    image = new Image(inputStream, Double.parseDouble(Config.getFileX()), Double.parseDouble(Config.getFileY()), true, true);
                                    inputStream.close();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                //label.setGraphic(new ImageView(getImageFromNameFileIcons("png.png")));
                                label.setGraphic(new ImageView(image));
                                break;
                            case "MOV":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("mov.png")));
                                break;
                            case "mp3":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("mp3.png")));
                                break;
                            case "mp4":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("mp4.png")));
                                break;
                            case "mpeg":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("mpeg.png")));
                                break;
                            case "html":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("html.png")));
                                break;
                            case "bash":
                            case "sh":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("bash.png")));
                                break;
                            case "avi":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("avi.png")));
                                break;
                            case "jpg":
                                try {
                                    inputStream = new FileInputStream(file.getAbsolutePath());
                                    image = new Image(inputStream, Double.parseDouble(Config.getFileX()), Double.parseDouble(Config.getFileY()), true, true);
                                    try {
                                        inputStream.close();
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                } catch (FileNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                                label.setGraphic(new ImageView(image));
                                //    label.setGraphic(new ImageView(getImageFromNameFileIcons("jpg.png")));
                                break;
                            case "txt":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("txt.png")));
                                break;
                            case "class":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("class.png")));
                                break;
                            case "so":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("so.png")));
                                break;
                            case "conf":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("conf.png")));
                                break;
                            case "data":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("data.png")));
                                break;
                            case "wav":
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("wav.png")));
                                break;
                            default:
                                label.setGraphic(new ImageView(getImageFromNameFileIcons("blank.png")));
                                break;
                        }
                    }
                    label.setOnMouseEntered(mouseEvent -> entered(label));
                    label.setOnMouseExited(mouseEvent -> exited(label));
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
                            if (filesLoadedLenght > filesLenght){
                                refresh(flowPane,path,lastpaths,textField,isHiddenFilter,loadedFiles);
                            }
                        }else {
                            loadedFiles.setText(((filesLoadedLenght++)+" Element-ów"));
                        }

                    });

                }
            });
            thread.start();
        } catch (NullPointerException e) {
            objects = 0;
        }
    }

    private void entered(Label label) {
        label.setOnMouseClicked(mouseEvent -> {
            clicked = label;
            label.setStyle("-fx-background-color: #668cff; -fx-background-radius: 5;");
            mouse = mouseEvent.getClickCount();
            if (mouse >= 2) {
                label.setStyle("-fx-background-color: #28a300; -fx-background-radius: 5;");
            }
        });
        label.setStyle("-fx-background-color: #444444; -fx-background-radius: 5;");
    }

    private void exited(Label label) {
        clicked = null; // this fix a bug
        mouse = 0;
        label.setStyle("-fx-background-color: transparent;");
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
}
