import atlantafx.base.theme.*;

import javafx.scene.control.Alert;
import script.criticException.PanicException;
import script.helpers.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import script.labels.GetMove;
import script.properties.Config;
import script.labels.GetBlinking;


import java.io.IOException;

public class App extends Application {
    static String arguments = null;
    private static final String osName = System.getProperty("os.name");
    ShowAlerts showAlerts = new ShowAlerts();
    /*
    Todo dodaj obsługę języka angielskiego
     */
    @Override
    public void start(Stage stage) throws IOException {
        checkSystem();
        if (arguments.contains("%20")){
           String newArg = arguments.replace("%20", " ");
           Arg.setArgument(newArg);
        }else {
            Arg.setArgument(arguments);
        }
        Config config = new Config();
        config.checkDefaultProgramDirOrCreateIt();
        config.openConfigFile();
        config.loadAddedBookmarks();
        String typeOf_FXML = null;
        try {
            if (config.getSfmTheme().equals("dark")) {
                typeOf_FXML = "Window.fxml";
                Application.setUserAgentStylesheet((new CupertinoDark()).getUserAgentStylesheet());
            } else if (config.getSfmTheme().equals("light")) {
                typeOf_FXML = "Window_light.fxml"; // light option
                Application.setUserAgentStylesheet((new CupertinoLight()).getUserAgentStylesheet());
            } else {
                typeOf_FXML = "Window.fxml";
                Application.setUserAgentStylesheet((new CupertinoDark()).getUserAgentStylesheet());
            }
        }catch (NullPointerException e){
            showAlerts.Alert(Alert.AlertType.ERROR,e.toString(),"nie można wczytać sceny","błąd");
            e.printStackTrace();
        }

        AutoRefresh.setMilis(config.getAutoRefreshMilis());
        ShowIcons.setX(Integer.parseInt(config.getX()));
        ShowIcons.setY(Integer.parseInt(config.getY()));
        ShowIcons.setxBookmark(Integer.parseInt(config.getXBookmark()));
        ShowIcons.setyBookmark(Integer.parseInt(config.getYBookmark()));
        ShowIcons.setTheme(config.getTheme());
        GetBlinking.setBlinking(config.getBlinking());
        GetMove.setMoveIcon(config.getMovingIcons());
        // GetBookmarksVisible.setVisibleBookmarks(config.getBookmarks());
        ShowIcons showIcons = new ShowIcons();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(typeOf_FXML));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 850, 470);
            stage.setTitle("SFM - " + arguments);
            stage.getIcons().add(showIcons.getImageFromName("fmfxicon.png", 96, 96));
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }catch (RuntimeException e){
            throw new PanicException("The program cannot load the scene!");
        }
    }

    void checkSystem() {
        if (!osName.contains("Linux")) {
            showAlerts.Alert(Alert.AlertType.WARNING, "Ten system " + osName + " nie rozpoznany", "Użyto innego systemu niż ten program jest w stanie obsłużyć", "Problem z systemem");
            throw new PanicException("The system is not valid, the program is terminated as a result of an unsupported system. " + osName);
        }
    }

    public static void main(String[] args) {
        try {
            arguments = args[0];
            if (arguments.isBlank() || arguments.isEmpty()) {
                arguments = FileSys.HOME.getPath();
            } // fixes a bug when running a program without an argument
        } catch (ArrayIndexOutOfBoundsException e) {
            arguments = FileSys.HOME.getPath(); //TODO dodaj opcje wyboru gdzie startować w przypadku braku argumentu
        }
        launch();
    }
}