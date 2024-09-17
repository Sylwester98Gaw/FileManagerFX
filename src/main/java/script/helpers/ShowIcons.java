package script.helpers;

import java.io.IOException;
import java.io.InputStream;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;

public class ShowIcons{
    static int x;
    static int y;
    static int xBookmark;
    static int yBookmark;
    static String theme;

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

    private final InputStream errorIcon = getClass().getResourceAsStream("/icons/errorico.png");
    ShowAlerts showAlerts = new ShowAlerts();

    public Image getImageFromNameFileIcons(String nameOfImage) {
        try {
            final InputStream image = getClass().getResourceAsStream("/icons" + theme + "/" + nameOfImage);
            final Image img = new Image(image, x, y, true, true);
            image.close();
            return img;
        } catch (NullPointerException | IOException e) {
            showAlerts.Alert(Alert.AlertType.ERROR, "Bład wczytywania ikony", "Nie można odczytać ikony więc zostanie zastąpiona inną, kod błędu: " + e, "Błąd");
            return new Image(errorIcon, 32, 32, true, true);
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
}
