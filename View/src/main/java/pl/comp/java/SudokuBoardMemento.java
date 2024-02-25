package pl.comp.java;

import exceptions.CloningException;
import javafx.beans.value.ObservableValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.SudokuBoard;
import project.SudokuBoardListener;
import project.SudokuField;

import java.io.Serializable;

/**
 * save original state of the sudoku board and return this state
 */
public class SudokuBoardMemento implements Serializable {
    private class SudokuBoardListenerZeroValue implements SudokuBoardListener {

        @Override
        public void changed(ObservableValue<? extends SudokuField> value, int oldValue, int newValue, int row, int col) {
            if (newValue == 0) {
                deletedFields[row][col] = true;
            }
        }
    }
    private SudokuBoardListenerZeroValue listenerZeroValue;
    private static final Logger logger = LogManager.getLogger();
    private final SudokuBoard sudokuBoard;
    //default value for this all falses
    private final boolean[][] deletedFields = new boolean[9][9];

    /**
     * Saves original state of the SudokuBoard
     * @param sudokuBoard
     * @throws CloningException
     */
    public SudokuBoardMemento(SudokuBoard sudokuBoard) throws CloningException {
        this.listenerZeroValue = new SudokuBoardListenerZeroValue();
        this.sudokuBoard = sudokuBoard.clone();
        sudokuBoard.addListener(this.listenerZeroValue);
        logger.info("Original state of the sudokuBoard has been saved");
    }
    public SudokuBoard restoreOriginalSudokuBoard() throws CloningException {
        logger.info("Original state of the sudokuBoard has been restored");
        return sudokuBoard.clone();
    }
    public SudokuBoard restoreWithDeletedFieldsSudokuBoard() throws CloningException {
        SudokuBoard board = this.sudokuBoard.clone();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (deletedFields[i][j]) {
                    board.set(i, j, 0);
                }
            }
        }
        logger.info("State of the sudokuBoard with deleted values has been restored");
        return board;
    }
}
