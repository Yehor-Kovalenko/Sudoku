package project;

import exceptions.FileOperationException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {
    private final String filename;
    private static final Logger logger = LogManager.getLogger();

    public FileSudokuBoardDao(String filename) {
        this.filename = filename;
    }

    @Override
    public SudokuBoard read() {
        try (FileInputStream ifstream = new FileInputStream(this.filename);
             ObjectInputStream inputStream = new ObjectInputStream(ifstream)) {
            SudokuBoard sudokuBoard = (SudokuBoard) inputStream.readObject();
            logger.info("File" + filename + " has been read successfully");
            return sudokuBoard;
        } catch (IOException | ClassNotFoundException e) {
            logger.catching(Level.ERROR, new FileOperationException(e));
            throw new FileOperationException("fileSBDao_read");
        }
    }

    @Override
    public void write(final SudokuBoard sudokuBoard) {
        try (FileOutputStream ofstream = new FileOutputStream(this.filename);
             ObjectOutputStream oostream = new ObjectOutputStream(ofstream)) {
            oostream.writeObject(sudokuBoard);
            logger.info("File" + filename + " has been written to successfully");
        } catch (IOException e) {
            logger.catching(Level.ERROR, new FileOperationException(e));
            throw new FileOperationException("fileSBDao_write");
        }

    }

    @Override
    public void close() {
    }
}
