import atlantafx.base.theme.*;

import javafx.scene.control.Alert;
import script.criticException.PanicException;
import script.helpers.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import script.labels.GetMove;
import script.helpers.GetTheme;
import script.properties.Config;
import script.labels.GetBlinking;


import java.io.IOException;

public class App extends Application {
    static Config config = new Config();
    static String arguments = null;
    private static final String osName = System.getProperty("os.name");
    ShowAlerts showAlerts = new ShowAlerts();
    /*
    Todo dodaj obsługę języka angielskiego
     */
    @Override
    public void start(Stage stage) throws IOException {
        config.checkDefaultProgramDirOrCreateIt();
        config.openConfigFile();
        config.loadAddedBookmarks();
        checkSystem();
        try {
            if (arguments.isEmpty()) {
                arguments = config.getStart_Position_Without_Argument();
            } // fixes a bug when running a program without an argument
        } catch (NullPointerException e ) {
            arguments = config.getStart_Position_Without_Argument();
        }
        if (arguments.contains("%20")){
           String newArg = arguments.replace("%20", " ");
           Arg.setArgument(newArg);
        }else {
            Arg.setArgument(arguments);
        }
        String typeOf_FXML = null;
        try {
            if (config.getSfmTheme().equals("dark")) {
                typeOf_FXML = "Window.fxml";
                GetTheme.setLight(false);
                Application.setUserAgentStylesheet((new CupertinoDark()).getUserAgentStylesheet());
            } else if (config.getSfmTheme().equals("light")) {
               // showAlerts.Alert(Alert.AlertType.WARNING,"Jasny tryb widoku nie w pełni sprawny","Tryb jasny zawiera błędy","Uwaga");
                typeOf_FXML = "Window_light.fxml"; // light option
                GetTheme.setLight(true);
                Application.setUserAgentStylesheet((new CupertinoLight()).getUserAgentStylesheet());
            } else {
                typeOf_FXML = "Window.fxml";
                GetTheme.setLight(false);
                Application.setUserAgentStylesheet((new CupertinoDark()).getUserAgentStylesheet());
            }
        }catch (NullPointerException e){
            showAlerts.Alert(Alert.AlertType.ERROR,e.toString(),"nie można wczytać sceny","błąd");
            e.printStackTrace();
        }

        ShowIcons.setX(Integer.parseInt(config.getX()));
        ShowIcons.setY(Integer.parseInt(config.getY()));
        ShowIcons.setxBookmark(Integer.parseInt(config.getXBookmark()));
        ShowIcons.setyBookmark(Integer.parseInt(config.getYBookmark()));
        ShowIcons.setTheme(config.getTheme());
        GetBlinking.setBlinking(config.getBlinking());
        GetMove.setMoveIcon(config.getMovingIcons());
        // GetBookmarksVisible.setVisibleBookmarks(config.getBookmarks());
        ShowIcons showIcons = new ShowIcons();
        showIcons.loadAllIcons();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(typeOf_FXML));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 850, 470);
            stage.setTitle("SFM - " + arguments);
            stage.getIcons().add(showIcons.getImageFromName("imgs.png", 96, 96));
            stage.setResizable(false);
            stage.setFullScreen(false);
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
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println(arguments);
        }
        launch();
    }
}
