package script.properties;

import javafx.scene.control.Alert;
import org.apache.commons.io.FileUtils;
import script.helpers.Colors;
import script.helpers.ShowAlerts;
import script.helpers.FileSys;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Config {
    private final File programDirs = new File(FileSys.HOME.getPath() + "/.SFM_data");
    private final File programConfigFile = new File(FileSys.HOME.getPath() + "/.SFM_data/FileManagerPROP.properties");
    private final File programExec = new File(FileSys.HOME.getPath() + "/.SFM_data/exec");
    private final File programFileColorConfig = new File(FileSys.HOME.getPath() + "/.SFM_data/fileColorConfig");
    private final File programUserBookmarks = new File(FileSys.HOME.getPath() + "/.SFM_data/UserBookmarks");
    private final ShowAlerts showAlerts = new ShowAlerts();
    static String icoTheme;
    static String sfmTheme;
    public static Map<String, String> bookmarkMap = new HashMap<>();
    static int autoRefreshMilis;
    Boolean bookmarks;
    static String blinkingHidden;
    static String x;
    static String fileX;
    static String y;
    static String fileY;
    static String xBookmark;
    static String yBookmark;
    static String movingIcons;
    static String langOption;
    static String start_Position_Without_Argument;


    /////////////////////// CSS
    static String flowPane_CSS_Light,flowPane_CSS_Dark; // why not ?
    static String buttonEntered_CSS_Light,buttonEntered_CSS_Dark;
    static String buttonExited_CSS_Light,buttonExited_CSS_Dark;
    static String labelSelectItem_CSS_Light,labelSelectItem_CSS_Dark;
    static String labelSelectTwoClickItem_CSS_Light,labelSelectTwoClickItem_CSS_Dark;
    static String labelEnteredMouseItem_CSS_Light,labelEnteredMouseItem_CSS_Dark;
    ////////////////////////
    public String getStart_Position_Without_Argument() {
        return start_Position_Without_Argument;
    }
    public String getFlowPane_CSS_Light() {
        return flowPane_CSS_Light;
    }

    public String getFlowPane_CSS_Dark() {
        return flowPane_CSS_Dark;
    }


    public String getButtonEntered_CSS_Light() {
        return buttonEntered_CSS_Light;
    }

    public String getButtonEntered_CSS_Dark() {
        return buttonEntered_CSS_Dark;
    }

    public String getButtonExited_CSS_Light() {
        return buttonExited_CSS_Light;
    }

    public String getButtonExited_CSS_Dark() {
        return buttonExited_CSS_Dark;
    }

    public String getLabelSelectItem_CSS_Light() {
        return labelSelectItem_CSS_Light;
    }

    public String getLabelSelectItem_CSS_Dark() {
        return labelSelectItem_CSS_Dark;
    }

    public String getLabelSelectTwoClickItem_CSS_Light() {
        return labelSelectTwoClickItem_CSS_Light;
    }

    public String getLabelSelectTwoClickItem_CSS_Dark() {
        return labelSelectTwoClickItem_CSS_Dark;
    }

    public String getLabelEnteredMouseItem_CSS_Light() {
        return labelEnteredMouseItem_CSS_Light;
    }

    public String getLabelEnteredMouseItem_CSS_Dark() {
        return labelEnteredMouseItem_CSS_Dark;
    }

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

    public void checkDefaultProgramDirOrCreateIt() {
        if (!programDirs.exists() && !programExec.exists() && !programUserBookmarks.exists() && !programConfigFile.exists() && !programFileColorConfig.exists()) {
            try {
                Properties propertiesConfig = getProperties();
                FileUtils.forceMkdir(programDirs);
                FileUtils.forceMkdir(programExec);
                FileUtils.forceMkdir(programUserBookmarks);
                FileUtils.forceMkdir(programFileColorConfig);
                new File(programDirs + "/FileManagerPROP" + ".properties");
                System.out.println("make config");
                propertiesConfig.store(new FileOutputStream(programDirs + "/FileManagerPROP" + ".properties"), "config");
                showAlerts.Alert(Alert.AlertType.INFORMATION, "Podstawowe pliki programu zostały utworzone.",
                        "Pliki konfiguracyjne znajdują się w " + programDirs, "SFM - Pierwsze uruchomienie WITAJ");
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }

    private static Properties getProperties() {
        Properties propertiesConfig = new Properties();
        propertiesConfig.setProperty("x", "48");
        propertiesConfig.setProperty("y", "48");
        propertiesConfig.setProperty("xBookmark", "24");
        propertiesConfig.setProperty("yBookmark", "24");
        propertiesConfig.setProperty("icoTheme", "/light_Mc26");
        propertiesConfig.setProperty("sfmTheme", "light");
        propertiesConfig.setProperty("moving", "false");
        propertiesConfig.setProperty("autoRefreshMilis", "500");
        propertiesConfig.setProperty("blinkingHidden", "true");
        propertiesConfig.setProperty("lang", "null");
        propertiesConfig.setProperty("showFileImageX", "48");
        propertiesConfig.setProperty("showFileImageY", "48");
        propertiesConfig.setProperty("start_Position_Without_Argument", "/");

        propertiesConfig.setProperty("flowPane_CSS_Light","#FAFAFA");
        propertiesConfig.setProperty("flowPane_CSS_Dark","#2e2f35");
        propertiesConfig.setProperty("buttonEntered_CSS_Light","#DFDFDF");
        propertiesConfig.setProperty("buttonEntered_CSS_Dark","#44484d");
        propertiesConfig.setProperty("buttonExited_CSS_Light","White");
        propertiesConfig.setProperty("buttonExited_CSS_Dark","#202224");
        propertiesConfig.setProperty("labelSelectItem_CSS_Light","");
        propertiesConfig.setProperty("labelSelectItem_CSS_Dark","");
        propertiesConfig.setProperty("labelSelectTwoClickItem_CSS_Light","");
        propertiesConfig.setProperty("labelSelectTwoClickItem_CSS_Dark","");
        propertiesConfig.setProperty("labelEnteredMouseItem_CSS_Light","");
        propertiesConfig.setProperty("labelEnteredMouseItem_CSS_Dark","");
        return propertiesConfig;
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
            start_Position_Without_Argument = prop.getProperty("start_Position_Without_Argument");
            flowPane_CSS_Dark = prop.getProperty("flowPane_CSS_Dark");
            flowPane_CSS_Light = prop.getProperty("flowPane_CSS_Light");
            buttonEntered_CSS_Light = prop.getProperty("buttonEntered_CSS_Light");
            buttonExited_CSS_Light = prop.getProperty("buttonExited_CSS_Light");
            buttonEntered_CSS_Dark = prop.getProperty("buttonEntered_CSS_Dark");
            buttonExited_CSS_Dark = prop.getProperty("buttonExited_CSS_Dark");
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
            properties.store(new FileOutputStream(FileSys.HOME.getPath() + "/.SFM_data/UserBookmarks/" + nameOfDir + ".properties"), "Zakładka");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//    public void putIntoMap(String key, String value){
//        bookmarkMap.put(key, value);
//    }

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
            properties.store(new FileOutputStream(FileSys.HOME.getPath() + "/.SFM_data/" + nameOfDir + ".properties"), "Ten plik przechowuje osobiste ustawienia " +
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
            properties.load(new FileInputStream(FileSys.HOME.getPath() + "/.SFM_data/" + nameOfDir + ".properties"));
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
            properties.load(new FileInputStream(FileSys.HOME.getPath() + "/.SFM_data/exec/" + nameOfDir + ".properties"));
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
            properties.store(new FileOutputStream(FileSys.HOME.getPath() + "/.SFM_data/exec/" + nameOfDir + ".properties"), "dodatkowe oprogramowanie");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String loadFileViewColor(String nameOfDir) {
        String value = null;
        var prop = new Properties();
        try {
            prop.load(new FileInputStream(FileSys.HOME.getPath() + "/.SFM_data/fileColorConfig/" + nameOfDir + ".properties"));
            value = prop.getProperty("color");
        } catch (FileNotFoundException e) {
            e.getStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return value;
    }
    public void saveFileViewColor(String nameOfDir, String colors) {
        var prop = new Properties();
        prop.setProperty("color", String.valueOf(colors));
        try {
            new File(FileSys.HOME.getPath() + "/.SFM_files/fileColorConfig/" + nameOfDir + ".properties");
            prop.store(new FileOutputStream(FileSys.HOME.getPath() + "/.SFM_data/fileColorConfig/" + nameOfDir + ".properties"),"Ten plik przechowuje osobiste ustawienia " +
                    "wyświetlania pliku/katalogu");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

