/**
 * Copyright 2023 Yehor Kovalenko and Andrei Pivavarau
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package project;

import exceptions.CloningException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//di - https://en.wikipedia.org/wiki/Dependency_injection
//Source of inspiration - https://jeffe.cs.illinois.edu/teaching/algorithms/book/02-backtracking.pdf
public class SudokuBoard implements Serializable, Cloneable, Prototype {
    private static final Logger logger = LogManager.getLogger();
    private final SudokuField[][] board = new SudokuField[9][9];
    private final List<SudokuBoardListener> listeners = new ArrayList<>();

    private final SudokuSolver solver;

    public SudokuBoard(SudokuSolver solver) {
        this.solver = solver;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuField();
            }
        }
    }

    private boolean checkBoard() {
        for (int i = 0; i <= 6; i += 3) {
            for (int j = 0; j <= 6; j += 3)  {
                if (!this.getBox(i, j).verify()) {
                    return false;
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            if (!this.getRow(i).verify() || !this.getColumn(i).verify()) {
                return false;
            }
        }

        return true;
    }

    //filling in the number on the sudoku board according to the sudoku's rules using backtracking algorithm


    public void solveGame() {
        this.solver.solve(this);
        logger.info("SudokuBoard has been successfully solved");
    }

    public int get(int row, int col) {
        return this.board[row][col].getFieldValue();
    }

    public void set(int row, int col, int value) {
        int oldValue = this.board[row][col].getFieldValue();
        this.board[row][col].setFieldValue(value);
        notifyListeners(oldValue, this.board[row][col].getFieldValue(), row, col);
        if (!this.checkBoard()) {
            return;
        }
    }

    public SudokuRow getRow(int y) {
        SudokuRow row = new SudokuRow();
        for (int i = 0; i < 9; i++) {
            row.set(i, this.board[y][i].getFieldValue());
        }
        return row;
    }

    public SudokuColumn getColumn(int col) {
        SudokuColumn column = new SudokuColumn();
        for (int i = 0; i < 9; i++) {
            column.set(i, this.get(i, col));
        }
        return column;
    }

    public SudokuBox getBox(int row, int col) {
        SudokuBox box = new SudokuBox();
        int index = 0;
        int boxRow = row / 3; //get the first row of the box
        int boxCol = col / 3; //get the first column of the box
        //checking the box for the provided number(coordinates on the sudoku board)
        for (int i = boxRow * 3; i < (boxRow * 3) + 3; i++) {
            for (int j = boxCol * 3; j < (boxCol * 3) + 3; j++) {
                box.set(index++, this.get(i, j));
            }
        }
        return box;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SudokuBoard that = (SudokuBoard) o;

        return new EqualsBuilder().append(board, that.board).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(board).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("board", board).append("solver", solver).toString();
    }

    @Override
    public SudokuBoard clone() throws CloningException {
        try {
            SudokuSolver solver1 = this.solver.clone();
            SudokuBoard clonedBoard = new SudokuBoard(solver1);
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    clonedBoard.set(i, j, this.get(i, j));
                }
            }
            return clonedBoard;
        } catch (CloneNotSupportedException e) {
            logger.catching(Level.ERROR, new CloningException(e));
            throw new CloningException("sudokuBoard_clone");
        }
    }

    public void addListener(SudokuBoardListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(SudokuBoardListener listener) {
        this.listeners.remove(listener);
    }

    public void notifyListeners(int oldValue, int newValue, int row, int col) {
        for (SudokuBoardListener listener : this.listeners) {
            listener.changed(null, oldValue, newValue, row, col);
        }
    }
}
