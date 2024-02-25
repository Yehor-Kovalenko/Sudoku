package pl.comp.java;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.SudokuBoard;

import java.util.Random;

public enum Difficulty {
    EASY(15),
    MEDIUM(35),
    HARD(55);

    private final int amountOfNumbers;
    private static final Logger logger = LogManager.getLogger();

    Difficulty(int amountOfNumbers) {
        this.amountOfNumbers = amountOfNumbers;
    }

    public static void removeNumbersFromSudokuBoard(SudokuBoard board, Difficulty difficulty) {
        Random rand = new Random(System.nanoTime());
        for (int i = 0; i < difficulty.amountOfNumbers; i++) {
            int row = rand.nextInt(9);
            int col = rand.nextInt(9);

            if (board.get(row, col) != 0) {
                board.set(row, col, 0);
            } else {
                i -= 1;
            }
        }
        logger.info(difficulty.amountOfNumbers + " elements have been removed from the sudoku board");
    }
}
