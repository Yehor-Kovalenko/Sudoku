package project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


class FileSudokuBoardDaoTest {
    @Test
    void writeAndReadTest() throws Exception {
        String filename = "readerTest.txt";
        DaoFactory<SudokuBoard> factory = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> file = factory.getFileDao(filename);

        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudoku1 = new SudokuBoard(solver);
        sudoku1.solveGame();
        file.write(sudoku1);
        SudokuBoard sudoku2 = file.read();
        assertTrue(sudoku1.equals(sudoku2));
    }
}