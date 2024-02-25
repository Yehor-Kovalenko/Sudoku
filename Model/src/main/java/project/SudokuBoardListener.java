package project;

import javafx.beans.value.ObservableValue;

import java.io.Serializable;

/**
 * abstract interface for adding listeners to the SudokuBoard objects
 */
public interface SudokuBoardListener extends Serializable {
    void changed(ObservableValue<? extends SudokuField> value, int oldValue, int newValue, int row, int col);
}
