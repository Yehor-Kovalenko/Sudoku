package pl.comp.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainApplication extends Application {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void start(Stage stage) throws IOException {
        Locale locale = new Locale("pl", "PL");
        Locale.setDefault(locale);
        logger.info("Default language set to " + Locale.getDefault().getLanguage());
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("DifficultyChoice.fxml"));
        fxmlLoader.setResources(ResourceBundle.getBundle("AppBundle.AppBundle", locale));
        Scene scene = new Scene(fxmlLoader.load(), 660, 660);
        stage.setTitle(ResourceBundle.getBundle("AppBundle.AppBundle", locale).getString("title"));
        stage.setScene(scene);
        logger.info("Loading DifficultyChoice scene");
        stage.show();
    }

    public static void main(String[] args) {
        logger.info("GUI has been started");
        launch();
    }
}