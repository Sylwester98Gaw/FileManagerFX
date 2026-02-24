package script.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;

public class ShowIcons{
    static int x;
    static int y;
    static int xBookmark;
    static int yBookmark;
    static String theme;

    private static final HashMap<String,Image> iconsCache_Exec = new HashMap<>();
    private static final HashMap<String,Image> iconsCache_Bookmarks = new HashMap<>();
   // private static HashMap<String,Image> iconsCache_Manager = new HashMap<>(); todo

    public static int getX() {
        return x;
    }

    public static void setX(int x) {
        ShowIcons.x = x;
    }

    public static int getY() {
        return y;
    }

    public static void setY(int y) {
        ShowIcons.y = y;
    }

    public static int getxBookmark() {
        return xBookmark;
    }

    public static void setxBookmark(int xBookmark) {
        ShowIcons.xBookmark = xBookmark;
    }

    public static int getyBookmark() {
        return yBookmark;
    }

    public static void setyBookmark(int yBookmark) {
        ShowIcons.yBookmark = yBookmark;
    }

    public static String getTheme() {
        return theme;
    }

    public static void setTheme(String theme) {
        ShowIcons.theme = theme;
    }
    static final InputStream errorIcon = ShowIcons.class.getResourceAsStream("/icons/errorico.png");
    ShowAlerts showAlerts = new ShowAlerts();
/*

 */
    public void loadAllIcons(){
//        String[] iconNamesManager = {
//                "back.png",
//                "forward.png",
//                "go.png",
//                "hidden.png",
//                "hidden-on.png",
//                "info.png",
//                "multiple.png",
//                "null.png",
//                "status1.png",
//                "status2.png",
//                "term.png",
//                "trash.png",
//                "trash-full.png"
//        };
        String[] iconNamesBookmarks = {
               // "desktop.png",
              //  "documents.png",
              //  "download.png",
                "home.png",
                "mark.png",
              //  "music.png",
              //  "photo.png",
                "system.png",
                "disk.png",
                "trash.png"
              //  "video.png"
        };
        String[] iconNamesExec = {
                "0.jpg",
                "apk.png",
                "app.png",
                "avi.png",
                "bash.png",
                "bin.png",
                "blank.png",
                "cfg.png",
                "class.png",
                "cmd.png",
                "css.png",
                "conf.png",
                "config.png",
                "cur.png",
                "data.png",
                "dat.png",
                "deb.png",
                "dll.png",
                "doc.png",
                "folder.png",
                "folder-open.png",
                "gitignore.png",
                "html.png",
                "jar.png",
                "java.png",
                "json.png",
                "mov.png",
                "mp3.png",
                "mp4.png",
                "mpeg.png",
                "rar.png",
                "rpm.png",
                "so.png",
                "sh.png",
                "txt.png",
                "wav.png",
                "xml.png",
                "xz.png",
                "gz.png",
                "pdf.png",
                "exe.png",
                "7z.png",
                "zip.png"
        };
        for (String name : iconNamesExec){
            loadImages_Exec(name);
        }
        for (String name : iconNamesBookmarks){
            loadImages_Bookmarks(name);
        }
    }

    private static void loadImages_Exec(String img){
        try {
            InputStream loadImage = ShowIcons.class.getResourceAsStream("/icons" + theme + "/" + img);
            Image image = null;
            if (loadImage != null) {
                image = new Image(loadImage,x,y,true,true);
                iconsCache_Exec.put(img,image);
                loadImage.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadImages_Bookmarks(String img){
        try {
            InputStream loadImage = ShowIcons.class.getResourceAsStream("/icons" + theme + "/bookmarks/" + img);
            Image image = null;
            if (loadImage != null) {
                image = new Image(loadImage,xBookmark,yBookmark,true,true);
                iconsCache_Bookmarks.put(img,image);
                loadImage.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Image getImageFromName(String nameOfImage, int x, int y) {
        try {
            final InputStream image = getClass().getResourceAsStream("/icons/" + nameOfImage);
            final Image img = new Image(image, x, y, true, true);
            image.close();
            return img;
        } catch (NullPointerException | IOException e) {
            showAlerts.Alert(Alert.AlertType.ERROR, "Bład wczytywania ikony", "Nie można odczytać ikony więc zostanie zastąpiona inną, kod błędu: " + e, "Błąd");
            return new Image(errorIcon, 32, 32, true, true);
        }
    }

    public Image getImageFromName(String nameOfImage, String dir, int x, int y) {
        try {
            final InputStream image = getClass().getResourceAsStream("/icons" + theme + "/" + dir + "/" + nameOfImage);
            final Image img = new Image(image, x, y, true, true);
            image.close();
            return img;
        } catch (NullPointerException | IOException e) {
            showAlerts.Alert(Alert.AlertType.ERROR, "Bład wczytywania ikony", "Nie można odczytać ikony więc zostanie zastąpiona inną, kod błędu: " + e, "Błąd");
            return new Image(errorIcon, 32, 32, true, true);
        }
    }
    public static Image getImageExec(String name){
        return iconsCache_Exec.get(name);
    }
    public static Image getImageBookmarks(String name){
        return iconsCache_Bookmarks.get(name);
    }
}
