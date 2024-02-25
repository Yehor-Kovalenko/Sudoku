package project;

public class SudokuBoardDaoFactory extends DaoFactory<SudokuBoard> {

    @Override
    public Dao<SudokuBoard> getFileDao(final String filename) {
        Dao<SudokuBoard> dao = new FileSudokuBoardDao(filename);
        return dao;
    }

    @Override
    public Dao<SudokuBoard> getDbDao(String databasename) {
        Dao<SudokuBoard> dao = new JdbcSudokuBoardDao(databasename);
        return dao;
    }

}
