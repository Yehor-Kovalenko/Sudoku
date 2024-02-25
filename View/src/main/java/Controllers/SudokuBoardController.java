package Controllers;

import exceptions.CloningException;
import exceptions.DatabaseException;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.comp.java.Difficulty;
import pl.comp.java.NumberStringConverter;
import pl.comp.java.SudokuBoardMemento;
import project.*;

import java.util.Locale;
import java.util.ResourceBundle;

public class SudokuBoardController {
    //FXML controls
    @FXML
    public GridPane sudokuBoardGrid;
    @FXML
    public MenuItem saveItem;
    @FXML
    public MenuItem loadItem;
    @FXML
    public MenuItem resetItem;
    @FXML
    public MenuItem originalBoardItem;
    @FXML
    public MenuButton menuButton;
    //other fields
    private SudokuBoard sudokuBoard;
    private SudokuBoardMemento sudokuBoardMemento;
    private final TextField[][] textFields = new TextField[9][9];
    final private String defaultFilename = "sudokuGameFile.txt";
    private String databaseName = "Sudoku";
    final private String defaultOriginalSudokuBoardFilename = "originalSudokuBoard.txt";
    private final NumberStringConverter converter = new NumberStringConverter();
    public static Difficulty difficulty;
    private static final Logger logger = LogManager.getLogger();


    @FXML
    private void initialize() throws Exception {
        //solve sudoku
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        sudokuBoard = new SudokuBoard(solver);
        sudokuBoard.solveGame();
        sudokuBoardMemento = new SudokuBoardMemento(sudokuBoard);
        Difficulty.removeNumbersFromSudokuBoard(sudokuBoard, difficulty);
        //intialize gridpane with textfields and asign them indexes in order to be able to call them by index
        for (int i = 0; i < 9; i ++) {
            for (int j = 0; j < 9; j++) {
                TextField textField = new TextField();
                textField.setMinSize(10, 10);
                textField.setPrefSize(100, 100);
                textField.setStyle("-fx-fill-height: true;-fx-alignment:CENTER; -fx-font-size:16; -fx-font-family: Forte; -fx-text-alignment: CENTER; -fx-opacity: 100;");
                //textField.setText("6");
                textFields[i][j] = textField;
                if (sudokuBoard.get(i, j) != 0) {
                    this.textFields[i][j].setDisable(true);
                }
                sudokuBoardGrid.add(textField, j, i);
            }
        }
        bindings();
        fillGrid(this.sudokuBoard);
        initializeMenuItems();
        //save original sudokuBoard state
        DaoFactory<SudokuBoard> factory = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> database = factory.getDbDao(databaseName);
        //JdbcSudokuBoardDao database = new JdbcSudokuBoardDao(databaseName);
        database.write(sudokuBoardMemento.restoreOriginalSudokuBoard());
    }
    private void initializeMenuItems() {
        this.saveItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    //saveToFile(defaultFilename);
                    saveToDatabase();
                } catch (Exception e) {
                    logger.catching(Level.ERROR, e);
                }
                logger.info("Button " + saveItem.getText() + " has been clicked");
            }
        });
        this.loadItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    getFromDatabase();
                } catch (Exception e) {
                    logger.catching(Level.ERROR, e);
                }
                logger.info("Button " + loadItem.getText() + " has been clicked");
            }
        });
        this.resetItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    logger.info("Button " + resetItem.getText() + " has been clicked");
                    fillGrid(sudokuBoardMemento.restoreWithDeletedFieldsSudokuBoard());
                } catch (CloningException e) {
                    logger.catching(Level.ERROR, e);
                }
            }
        });
        this.originalBoardItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    logger.info("Button " + originalBoardItem.getText() + " has been clicked");
                    fillGrid(sudokuBoardMemento.restoreOriginalSudokuBoard());
                } catch (CloningException e) {
                    logger.catching(Level.ERROR, e);
                }
            }
        });
    }

    private void fillGrid(SudokuBoard sudokuBoard) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                textFields[i][j].setText(converter.toString(sudokuBoard.get(i, j)));
            }
        }
        logger.info("Sudoku board grid for GUI has been refreshed");
    }
    @FXML
    public void saveToFile(String filename) throws Exception {
        DaoFactory<SudokuBoard> factory = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> database = factory.getDbDao(databaseName);
        //JdbcSudokuBoardDao database = new JdbcSudokuBoardDao(databaseName);
        database.write(this.sudokuBoard);
        logger.info("Current sudoku board has been saved to database " + databaseName);
        logger.info(sudokuBoard.toString());
    }

    @FXML
    public void loadFromFile(String dbName) throws Exception {
        //while loading my value that were not created originally are immutable, maybe memento can fix it
        DaoFactory<SudokuBoard> factory = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> database = factory.getDbDao(dbName);
        //JdbcSudokuBoardDao database = new JdbcSudokuBoardDao(databaseName);
        //this.sudokuBoard = database.read();
        logger.info("Current sudoku board has been loaded from database " + dbName);
        logger.info(sudokuBoard.toString());
        this.fillGrid(this.sudokuBoard);
    }
    public void bindings() {
        for (int row = 0; row < sudokuBoardGrid.getRowCount(); row++) {
            for (int col = 0; col < sudokuBoardGrid.getColumnCount(); col++) {
                //binding StringProperty of the TextField and the IntegerProperty(SudokuField) using customised NumberStringConverter
                Bindings.bindBidirectional(this.textFields[row][col].textProperty(),
                        getIntegerProperty(row, col),
                        converter);
                //TextFormatter(Validator of our string in textField)
                TextFormatter<Number> textFormatter = new TextFormatter<>(
                        change -> {
                            String newString  = change.getControlNewText();
                            if (newString.matches("[1-9 ]?")) {
                                return change;
                            } else {
                                return null; // Reject the change if it doesn't match the pattern
                            }
                        }
                );

                //setting TextFormatter for the TextField (aka Validator) and adding the logging listener
                textFields[row][col].setTextFormatter(textFormatter);
                int finalRow = row;
                int finalCol = col;
                textFields[row][col].textProperty().addListener((observable, oldValue, newValue) -> {
                    logger.info("TextField (" + finalRow + ", " + finalCol + ") has been changed: " + oldValue + " -> " + newValue);
                });
            }
        }
    }

    private IntegerProperty getIntegerProperty(int x, int y) {
        IntegerProperty integerProperty = new SimpleIntegerProperty();
        integerProperty.setValue(this.sudokuBoard.get(x, y));
        this.sudokuBoard.addListener(new SudokuBoardListener() {
            @Override
            public void changed(ObservableValue<? extends SudokuField> value, int oldValue, int newValue, int row, int col) {
                if(newValue != oldValue && row == x && col == y) {
                    integerProperty.setValue(newValue);
                }
            }
        });
        integerProperty.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(!newValue.equals(oldValue)) {
                    logger.info("Sudoku field (" + (x) + ", " + (y) + ") has been changed: " + oldValue + " -> " + newValue);
                    sudokuBoard.set(x, y, integerProperty.getValue());
                }
            }
        });
        return integerProperty;
    }

    public String loadDialogName() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(ResourceBundle.getBundle("AppBundle.AppBundle", Locale.getDefault()).getString("title"));
        dialog.setHeaderText(ResourceBundle.getBundle("AppBundle.AppBundle", Locale.getDefault()).getString("load_dialog_header"));
        dialog.setContentText(ResourceBundle.getBundle("AppBundle.AppBundle", Locale.getDefault()).getString("load_dialog_text"));

        dialog.showAndWait().ifPresent(result -> {
            databaseName = result.trim();
        });
        return databaseName;
    }

    public void getFromDatabase() {
        try {
            databaseName = loadDialogName();
            DaoFactory<SudokuBoard> factory = new SudokuBoardDaoFactory();
            Dao<SudokuBoard> database = factory.getDbDao(databaseName);
            sudokuBoard = database.read();
            logger.info("Sudoku has been loaded from file " + databaseName);
            this.fillGrid(sudokuBoard);
        } catch (DatabaseException e) {
            logger.catching(Level.ERROR, new DatabaseException(e));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String saveDialogName() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(ResourceBundle.getBundle("AppBundle.AppBundle", Locale.getDefault()).getString("title"));
        dialog.setHeaderText(ResourceBundle.getBundle("AppBundle.AppBundle", Locale.getDefault()).getString("save_dialog_header"));
        dialog.setContentText(ResourceBundle.getBundle("AppBundle.AppBundle", Locale.getDefault()).getString("save_dialog_text"));

        dialog.showAndWait().ifPresent(result -> {
            databaseName = result.trim();
        });
        return databaseName;
    }

    public void saveToDatabase() {
        try {
            databaseName = saveDialogName();
            DaoFactory<SudokuBoard> factory = new SudokuBoardDaoFactory();
            Dao<SudokuBoard> database = factory.getDbDao(databaseName);
            database.write(sudokuBoard);
            logger.info("Sudoku has been saved to database as " + databaseName);
        } catch (DatabaseException e) {
            logger.catching(Level.ERROR, new DatabaseException(e));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
