package script.properties;

import javafx.scene.control.Alert;
import org.apache.commons.io.FileUtils;
import script.helpers.ShowAlerts;
import script.helpers.FileSys;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Config {
    public static Map<String, String> bookmarkMap = new HashMap<>();
    int autoRefreshMilis;
    Boolean bookmarks;
    String blinkingHidden;
    String x;
    static String fileX;
    String y;
    static String fileY;
    String xBookmark;
    String yBookmark;
    String movingIcons;
    String langOption;

    public static String getFileX() {
        return fileX;
    }

    public static String getFileY() {
        return fileY;
    }

    public int getAutoRefreshMilis() {
        return autoRefreshMilis;
    }

    public String getBlinking() {
        return blinkingHidden;
    }

    String icoTheme;
    String sfmTheme;

    public String getSfmTheme() {
        return sfmTheme;
    }

    public String getMovingIcons() {
        return movingIcons;
    }

    public String getTheme() {
        return this.icoTheme;
    }

    public String getX() {
        return this.x;
    }

    public String getY() {
        return this.y;
    }

    public String getXBookmark() {
        return this.xBookmark;
    }

    public String getYBookmark() {
        return this.yBookmark;
    }

    public Boolean getBookmarks() {
        return bookmarks;
    }

    public String getLangOption() {
        return langOption;
    }

    public static Map<String, String> getBookmarkMap() {
        return bookmarkMap;
    }

    public static void setBookmarkMap(Map<String, String> bookmarkMap) {
        Config.bookmarkMap = bookmarkMap;
    }

    private final File programDirs = new File(FileSys.HOME.getPath() + "/.SFM_files");
    private final File programConfigFile = new File(FileSys.HOME.getPath() + "/.SFM_files/FileManagerPROP.properties");
    private final File programTmps = new File(FileSys.HOME.getPath() + "/.SFM_files/tmps");
    private final File programExec = new File(FileSys.HOME.getPath() + "/.SFM_files/exec");
    private final File programUserBookmarks = new File(FileSys.HOME.getPath() + "/.SFM_files/UserBookmarks");
    private ShowAlerts showAlerts = new ShowAlerts();

    public void checkDefaultProgramDirOrCreateIt() {
        if (!programDirs.exists() && !programExec.exists() && !programTmps.exists() && !programUserBookmarks.exists() && !programConfigFile.exists()) {
            try {
                Properties propertiesConfig = new Properties();
                propertiesConfig.setProperty("x", "48");
                propertiesConfig.setProperty("y", "48");
                propertiesConfig.setProperty("xBookmark", "16");
                propertiesConfig.setProperty("yBookmark", "16");
                propertiesConfig.setProperty("icoTheme", "/pack3");
                propertiesConfig.setProperty("sfmTheme", "light");
                propertiesConfig.setProperty("moving", "false");
                propertiesConfig.setProperty("autoRefreshMilis", "1000");
                propertiesConfig.setProperty("blinkingHidden", "true");
                propertiesConfig.setProperty("lang", "null");
                propertiesConfig.setProperty("showFileImageX", "56");
                propertiesConfig.setProperty("showFileImageY", "56");
                FileUtils.forceMkdir(programDirs);
                FileUtils.forceMkdir(programTmps);
                FileUtils.forceMkdir(programExec);
                FileUtils.forceMkdir(programUserBookmarks);
                new File(programDirs + "/FileManagerPROP" + ".properties");
                System.out.println("make config");
                propertiesConfig.store(new FileOutputStream(programDirs + "/FileManagerPROP" + ".properties"), "config");
                showAlerts.Alert(Alert.AlertType.INFORMATION, "Podstawowe pliki programu zostały utworzone, i plik konfiguracyjny ", "znajdują się w " + programDirs, "SFM - Pierwsze uruchomienie WITAJ");
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }

    public void openConfigFile() {
        InputStream input = null;
        try {
            input = new FileInputStream(programConfigFile);
        } catch (FileNotFoundException e) {
            showAlerts.Alert(Alert.AlertType.ERROR, "Brak pliku konfiguracyjnego !", "Nie ma pliku konfiguracyjnego usuń katalog .SFM_files z katalogu domowego i zrestartuj aplikacje", "Błąd");
            throw new RuntimeException(e);
        }
        try {
            Properties prop = new Properties();
            prop.load(input);
            x = prop.getProperty("x");
            y = prop.getProperty("y");
            xBookmark = prop.getProperty("xBookmark");
            yBookmark = prop.getProperty("yBookmark");
            icoTheme = prop.getProperty("icoTheme");
            sfmTheme = prop.getProperty("sfmTheme");
            blinkingHidden = prop.getProperty("blinkingHidden");
            bookmarks = Boolean.valueOf(prop.getProperty("bookmarks"));
            movingIcons = prop.getProperty("moving");
            langOption = prop.getProperty("lang");
            autoRefreshMilis = Integer.parseInt(prop.getProperty("autoRefreshMilis"));
            fileX = String.valueOf(Integer.parseInt(prop.getProperty("showFileImageX")));
            fileY = String.valueOf(Integer.parseInt(prop.getProperty("showFileImageY")));
            input.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveBookmarks(String nameOfDir, File file) {
        Properties properties = new Properties();
        properties.setProperty("where", file.getPath());
        try {
            new File(programUserBookmarks + nameOfDir + ".properties");
            properties.store(new FileOutputStream(FileSys.HOME.getPath() + "/.SFM_files/UserBookmarks/" + nameOfDir + ".properties"), "Zakładka");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadAddedBookmarks() {
        File[] filesList = programUserBookmarks.listFiles();
        bookmarkMap.clear();
        try {
            for (File file : filesList) {
                Properties properties = new Properties();
                properties.load(new FileInputStream(file.getAbsolutePath()));
                bookmarkMap.put(file.getName(), properties.getProperty("where"));
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void saveTranslate(double x, double y, String nameOfDir) {
        Properties properties = new Properties();
        properties.setProperty("x", String.valueOf(x));
        properties.setProperty("y", String.valueOf(y));
        try {
            new File(FileSys.HOME.getPath() + "/.SFM_files/" + nameOfDir + ".properties");
            properties.store(new FileOutputStream(FileSys.HOME.getPath() + "/.SFM_files/" + nameOfDir + ".properties"), "Ten plik przechowuje osobiste ustawienia " +
                    "wyświetlania pliku/katalogu");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param a
     * @param nameOfDir
     * @return
     */
    public double loadTranslate(boolean a, String nameOfDir) {
        double value = 0;
        /**
         * boolean a
         * true = x
         * false = y
         */
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(FileSys.HOME.getPath() + "/.SFM_files/" + nameOfDir + ".properties"));
            if (a) {
                value = Double.parseDouble(properties.getProperty("x"));
            } else {
                value = Double.parseDouble(properties.getProperty("y"));
            }

        } catch (FileNotFoundException e) {
            e.getStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return value;
    }

    public String loadAddedProgram(String nameOfDir) {
        String value = null;
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(FileSys.HOME.getPath() + "/.SFM_files/exec/" + nameOfDir + ".properties"));
            value = properties.getProperty("program");

        } catch (FileNotFoundException e) {
            e.getStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return value;
    }

    public void saveAddedProgram(String program, String nameOfDir) {
        Properties properties = new Properties();
        properties.setProperty("program", program);
        try {
            new File(FileSys.HOME.getPath() + "/.SFM_files/exec/" + nameOfDir + ".properties");
            properties.store(new FileOutputStream(FileSys.HOME.getPath() + "/.SFM_files/exec/" + nameOfDir + ".properties"), "dodatkowe oprogramowanie");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

