/**
 * Copyright 2023  Yehor Kovalenko
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

import javafx.beans.value.ObservableValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Test the sudoku board number generation")
class SudokuBoardTest {
    @Test
    @DisplayName("Validity of the numbers on the board")
    void validNumbers() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudoku = new SudokuBoard(solver);
        sudoku.solveGame();

        for (int i = 0; i <= 6; i += 3) {
            for (int j = 0; j <= 6; j += 3)  {
                if (!sudoku.getBox(i, j).verify()) {
                    fail("There is duplicate in the box");
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            if (!sudoku.getRow(i).verify()) {
                fail("There is duplicate number in the row " + i);
            }
        }
        for (int i = 0; i < 9; i++) {
            if (!sudoku.getColumn(i).verify()) {
                fail("There is duplicate number in a column " + i);
            }
        }
        assertTrue(true);
    }

    @Test
    @DisplayName("Uniqueness of the next 2 generated boards")
    void uniqueBoard() {
        BacktrackingSudokuSolver solver1 = new BacktrackingSudokuSolver();
        SudokuBoard sudoku1 = new SudokuBoard(solver1);
        sudoku1.solveGame();

        BacktrackingSudokuSolver solver2 = new BacktrackingSudokuSolver();
        SudokuBoard sudoku2 = new SudokuBoard(solver2);
        sudoku2.solveGame();
        assertFalse(sudoku1.equals(sudoku2));
    }

    @Test
    @DisplayName("Test if there are already numbers on the board")
    void partiallyFilled() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudoku = new SudokuBoard(solver);
        sudoku.set(0, 5, 6);
        sudoku.solveGame();
        assertEquals(sudoku.get(0, 5), 6);
    }

    @DisplayName("Row from the sudoku board is correct")
    @Test
    void getRow() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudoku = new SudokuBoard(solver);
        sudoku.solveGame();
        assertNotNull(sudoku.getRow(3));
        for (int i = 0; i < 9; i++) {
            if (sudoku.getRow(3).get(i) != sudoku.get(3, i)) {
                fail();
            }
        }
        assertTrue(true);
    }

    @DisplayName("Column from sudoku board is correct")
    @Test
    void getColumn() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudoku = new SudokuBoard(solver);
        sudoku.solveGame();
        assertNotNull(sudoku.getColumn(3));
        for (int i = 0; i < 9; i++) {
            if (sudoku.getColumn(2).get(i) != sudoku.get(i, 2)) {
                fail();
            }
        }
        assertTrue(true);
    }

    @DisplayName("Box from the sudoku board is correct")
    @Test
    void getBox() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudoku = new SudokuBoard(solver);
        sudoku.solveGame();
        assertNotNull(sudoku.getBox(0, 0));
        for (int i = 0; i < 9; i++) {
            if (sudoku.getBox(0, 0).get(i) != sudoku.get(i / 3, i % 3)) {
                fail();
            }
        }
        assertTrue(true);
    }

    @DisplayName("equals() method Positive Test")
    @Test
    void equalsPosTest() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudoku1 = new SudokuBoard(solver);
        sudoku1.solveGame();

        SudokuBoard sudoku2 = new SudokuBoard(solver);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudoku2.set(i, j, sudoku1.get(i, j));
            }
        }

        assertTrue(sudoku1.equals(sudoku2));
        assertTrue(sudoku1.equals(sudoku1));
    }

    @DisplayName("equals() method Negative Test")
    @Test
    void equalsNegTest() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudoku = new SudokuBoard(solver);
        sudoku.solveGame();

        if (sudoku.equals(null)) {
            fail();
        }
        assertTrue(true);
    }

    @DisplayName("hashCode() method Positive Test")
    @Test
    void hashCodePosTest() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudoku1 = new SudokuBoard(solver);
        sudoku1.solveGame();

        SudokuBoard sudoku2 = new SudokuBoard(solver);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudoku2.set(i, j, sudoku1.get(i, j));
            }
        }
        if (sudoku1.equals(sudoku2)) {
            assertTrue(sudoku1.hashCode() == sudoku2.hashCode());
        } else {
            fail();
        }
    }

    @DisplayName("hashCode() method Negative Test")
    @Test
    void hashCodeNegTest() {
        BacktrackingSudokuSolver solver1 = new BacktrackingSudokuSolver();
        SudokuBoard sudoku1 = new SudokuBoard(solver1);
        sudoku1.solveGame();

        SudokuBoard sudoku2 = new SudokuBoard(solver1);
        sudoku2.solveGame();

        if (sudoku1.hashCode() == sudoku2.hashCode() && !sudoku1.equals(sudoku2)) {
            fail();
        }
        assertTrue(true);
    }

    @DisplayName("toString() method Test")
    @Test
    void toStringTest() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudoku = new SudokuBoard(solver);
        sudoku.solveGame();

        String tmp = sudoku.toString();
        assertEquals(tmp, sudoku.toString());
    }

    @DisplayName("clone() method Test")
    @Test
    void cloneTest() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudoku = new SudokuBoard(solver);
        sudoku.solveGame();
        int x = 1;
        try {
            SudokuBoard sudoku2 = sudoku.clone();
            if (sudoku.get(0,0) == 1) {
                x = 2;
            }
            sudoku2.set(0, 0, x);
            assertTrue(sudoku.get(0, 0) != sudoku2.get(0, 0));
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("SudokuBoard Listener test")
    @Test
    void listenerTest() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudoku = new SudokuBoard(solver);
        sudoku.solveGame();
        SudokuBoardListener listener = new SudokuBoardListener() {
            @Override
            public void changed(ObservableValue<? extends SudokuField> value, int oldValue, int newValue,
                                int row, int col) {
                assertEquals(newValue, 5);
                assertEquals(row, 2);
                assertEquals(col, 3);
            }
        };
        sudoku.addListener(listener);

        sudoku.set(2, 3, 5);
        sudoku.removeListener(listener);

    }
}