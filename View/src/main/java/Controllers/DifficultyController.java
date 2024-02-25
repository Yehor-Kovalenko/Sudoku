package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.comp.java.Difficulty;
import pl.comp.java.MainApplication;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class DifficultyController {
    public Button buttonEasy;
    public Button buttonMedium;
    public Button buttonHard;
    private Stage stage;
    private Scene scene;
    private static final Logger logger = LogManager.getLogger();

    public void switchToSudokuBoard(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("SudokuBoard.fxml"));
        fxmlLoader.setResources(ResourceBundle.getBundle("AppBundle.AppBundle", Locale.getDefault()));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), stage.getWidth(), stage.getHeight());
        stage.setTitle(ResourceBundle.getBundle("AppBundle.AppBundle", Locale.getDefault()).getString("title"));
        stage.setScene(scene);
        logger.info("Loading SudokuBoard scene");
        stage.show();
    }

    public void switchLang(ActionEvent event, Locale locale) throws IOException {
        Locale.setDefault(locale);
        logger.info("Changing language to " + locale.getLanguage());
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("DifficultyChoice.fxml"));
        fxmlLoader.setResources(ResourceBundle.getBundle("AppBundle.AppBundle", locale));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), ((Node) event.getSource()).getScene().getWidth(), ((Node) event.getSource()).getScene().getHeight());
        stage.setTitle(ResourceBundle.getBundle("AppBundle.AppBundle", locale).getString("title"));
        stage.setScene(scene);
        stage.show();
    }

    public void easyButtonClicked(ActionEvent event) throws IOException {
        logger.info("Difficulty level 'Easy' has been chosen");
        SudokuBoardController.difficulty = Difficulty.EASY;
        switchToSudokuBoard(event);
    }
    public void mediumButtonClicked(ActionEvent event) throws IOException {
        logger.info("Difficulty level 'Medium' has been chosen");
        SudokuBoardController.difficulty = Difficulty.MEDIUM;
        switchToSudokuBoard(event);
    }
    public void hardButtonClicked(ActionEvent event) throws IOException {
        logger.info("Difficulty level 'Hard' has been chosen");
        SudokuBoardController.difficulty = Difficulty.HARD;
        switchToSudokuBoard(event);
    }

   public void languageEnglishChosen(ActionEvent event) throws IOException {
        logger.info("'English' language has been chosen as a default language");
        Locale engLoc = new Locale("en", "EN");
        switchLang(event, engLoc);
    }

    public void languagePolishChosen(ActionEvent event) throws IOException {
        logger.info("'Polish' language has been chosen as a default language");
        Locale plLoc = new Locale("pl", "PL");
        switchLang(event, plLoc);
    }

    public void languageBelarusianChosen(ActionEvent event) throws IOException {
        logger.info("'Belarusian' language has been chosen as a default language");
        Locale belLoc = new Locale("bel", "BY");
        switchLang(event, belLoc);
    }

    public void languageUkrainianChosen(ActionEvent event) throws IOException {
        logger.info("'Ukrainian' language has been chosen as a default language");
        Locale ukrLoc = new Locale("ukr", "UA");
        switchLang(event, ukrLoc);
    }
}