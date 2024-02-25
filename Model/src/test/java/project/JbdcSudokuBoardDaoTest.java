package project;

import exceptions.DatabaseException;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.fail;

class JbdcSudokuBoardDaoTest {
    @Test
    void writeAndReadTest() {
        String dbName = "testDB";
        DaoFactory<SudokuBoard> factory = new SudokuBoardDaoFactory();

        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudoku1 = new SudokuBoard(solver);
        sudoku1.solveGame();
        SudokuBoard sudoku2 = null;
        try (Dao<SudokuBoard> database = factory.getDbDao(dbName);) {
            database.write(sudoku1);
            sudoku2 = database.read();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals(sudoku1, sudoku2);
        assertNotSame(sudoku2, sudoku1);
    }

    @Test
    void exceptionsTest() {
        Locale.setDefault(new Locale("en", "EN"));
        String dbName = "testDB1";
        DaoFactory<SudokuBoard> factory = new SudokuBoardDaoFactory();
        try (Dao<SudokuBoard> database = factory.getDbDao(dbName);) {
            try {
                database.write(null);
                fail("Expected DatabaseException");
            } catch (DatabaseException e) {
                assertNotEquals("", e.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}