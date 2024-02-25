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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

import static org.apache.logging.log4j.Level.ERROR;

public class BacktrackingSudokuSolver implements SudokuSolver, Serializable {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void solve(SudokuBoard board) {
        this.fill(board,0,0);
    }

    //method to check if there is already the number in the row, column or 3x3 block
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
