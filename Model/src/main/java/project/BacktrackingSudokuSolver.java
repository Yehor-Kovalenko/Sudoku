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

import exceptions.CloningException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

import static org.apache.logging.log4j.Level.ERROR;

/**
 * The concrete implementation of the "Solver" interface,
 * its purpose is to solve the Sudoku (SudokuBoard)
 */
public class BacktrackingSudokuSolver implements SudokuSolver, Serializable {
    private static final Logger logger = LogManager.getLogger();

    /**
     * Publicly accessible method overriden from the super interface "Solver", solves the sudoku
     * @param board - "SudokuBoard" object that needs to be solved
     */
    @Override
    public void solve(SudokuBoard board) {
        this.fill(board,0,0);
    }

    /**
     * Private method to check if there is already the number in the row, column or 3x3 block
     * @param board - SudokuBoard object that needs to be checked
     * @param row - index of the board's row where the checking needs to be performed
     * @param col - index of the board's column where the checking needs to be performed
     * @return true if the board is valid (no duplicates) and false otherwise
     */
    private boolean isValid(SudokuBoard board, int row, int col) {
        //checking the row and column
        for (int i = 0; i < 9; i++) {
            if (board.get(row, i) == board.get(row, col) && i != col) {
                return false;
            }
            if (board.get(i, col) == board.get(row, col) && i != row) {
                return false;
            }
        }
        //checking the block
        int blockRow = row / 3; //get the first row of the block
        int blockCol = col / 3; //get the first column of the block
        //checking the block for the provided number(coordinates on the sudoku board)
        for (int i = blockRow * 3; i < (blockRow * 3) + 3; i++) {
            for (int j = blockCol * 3; j < (blockCol * 3) + 3; j++) {
                if (board.get(i, j) == board.get(row, col) && i != row && j != col) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Private method that actually solves the Sudoku using recursion, implements the BackTracking Algorithm
     * @param board - SudokuBoard object that needs to be solved
     * @param row - row algorithm currently working on
     * @param col - column algorithm currently working on
     * @return true if the whole board had been solved or number placed correctly
     * and false if there is no possible solution for this number placement
     */
    private boolean fill(SudokuBoard board, int row, int col) {

        if (col == 9) {
            if (row == 8) { //if it is end of the board, return true
                return true;
            } else { //if it is end of the line, go to the next one
                return fill(board, row + 1, 0);
            }
        }
        //if cell has not been filled, fill one
        if (board.get(row, col) == 0) {
            //array that stores already generated random numbers in order no to repeatedly generate ones
            int[] occurred = new int[9];
            Random rand = new Random(System.nanoTime());
            //while-loop to generate numbers till there are numbers, that were not generated(there are 0 in the array)
            //if there is no number left to put into the cell, leave while-loop and return false
            while (Arrays.stream(occurred).anyMatch((i) -> occurred[i] == 0)) {
                board.set(row, col, rand.nextInt(9) + 1);
                //generating brand-new number
                while (occurred[board.get(row, col) - 1] == 1) {
                    board.set(row, col, rand.nextInt(9) + 1);
                }
                occurred[board.get(row, col) - 1] = 1; //write down to the array generated number

                //if number placement is according to the rules
                if (isValid(board, row, col) && this.fill(board, row, col + 1)) {
                    //if there is no situation, when there is no number to put, return true
                    //else reset the cell and return to the previous one
                    return true;
                }
                //reset the cell to the default value - 0
                board.set(row, col, 0);
            }
            return false;

        } else {
            return fill(board, row, col + 1);
        }
    }

    /**
     * Cloning BacktrackingSolver object
     * @return deep copy of the current object
     * @throws CloningException if the cloning was not possible
     */
    @Override
    public BacktrackingSudokuSolver clone() throws CloningException {
        try {
            return (BacktrackingSudokuSolver) super.clone();
        } catch (CloneNotSupportedException e) {
            logger.catching(ERROR, new CloningException(e));
            throw new CloningException("btSudokuSolver_clone");
        }
    }
}
