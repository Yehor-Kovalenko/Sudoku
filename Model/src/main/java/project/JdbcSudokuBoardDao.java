package project;

import exceptions.DatabaseException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {

    PreparedStatement stmt = null;
    private final String url = "jdbc:postgresql://localhost:5432/Sudoku";
    private final String user = "postgres";
    private final String password = "kompo";
    private final String boardname;
    private static final Logger logger = LogManager.getLogger();

    public JdbcSudokuBoardDao(String boardname) {
        this.boardname = boardname;
    }

    @Override
    public SudokuBoard read() throws DatabaseException {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        String sql = "SELECT * FROM board_values WHERE board_id = "
                + "(SELECT MAX(board_id) FROM boards WHERE board_name = '" + this.boardname + "');";
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
            Statement statement = connection.createStatement();) {
            connection.setAutoCommit(false);
            logger.info("Opened database successfully");
            createTableIfNotExists();
            ResultSet rs = statement.executeQuery(sql);
            //ResultSet rs = statement.executeQuery("SELECT * FROM board_values;");
            while (rs.next()) {
                int value = rs.getInt("cell_value");
                int x = rs.getInt("x_position");
                int y = rs.getInt("y_position");
                sudokuBoard.set(x, y, value);
            }
            rs.close();
            logger.info("Successfully read from the database");
        } catch (SQLException e) {
            logger.catching(Level.ERROR, new DatabaseException(e));
            throw new DatabaseException("jdbc_read_msg");
        }
        return sudokuBoard;
    }

    @Override
    public void write(SudokuBoard sudokuBoard) throws DatabaseException {
        String queryBoardName = "INSERT INTO boards(board_name) VALUES (?)";
        String queryBoardValues = "INSERT INTO board_values(cell_value, x_position, y_position, board_id)"
            + " VALUES (?, ?, ?, ?);";
        int boardNumberIdFK = 0;
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);) {
            connection.setAutoCommit(false);
            logger.info("Opened database successfully");
            //create tables
            createTableIfNotExists();
            this.stmt = connection.prepareStatement(queryBoardName);
            if (sudokuBoard == null) {
                throw new SQLException();
            }
            this.stmt.setString(1, this.boardname);
            //insert name of the board to boards
            this.stmt.executeUpdate();

            ResultSet rs = connection.createStatement().executeQuery("SELECT board_id FROM boards;");
            while (rs.next()) {
                boardNumberIdFK = rs.getInt("board_id");
            }
            rs.close();

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    try (PreparedStatement preparedStatement = connection.prepareStatement(queryBoardValues)) {
                        preparedStatement.setInt(1, sudokuBoard.get(i, j));
                        preparedStatement.setInt(2, i);
                        preparedStatement.setInt(3, j);
                        preparedStatement.setInt(4, boardNumberIdFK);
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                connection.commit();
            }
            logger.info("Successfully written to the database");
        } catch (SQLException e) {
            logger.catching(Level.ERROR, new DatabaseException(e));
            throw new DatabaseException("jdbc_write_msg");
        }
    }

    @Override
    public void close() throws Exception {
        this.stmt.close();

    }

    /**
     * If there is no such tables in the database - creates ones
     * @throws DatabaseException if there has been an issue with creating the tables and accessing the database
     */
    private void createTableIfNotExists() throws DatabaseException {
        String createBoardsTable = "CREATE TABLE IF NOT EXISTS boards "
                + "("
                + "board_id SERIAL PRIMARY KEY,"
                + "board_name CHARACTER VARYING(255)"
                + ");";
        String createBoardValuesTable = "CREATE TABLE IF NOT EXISTS board_values "
                + "("
                + "cell_value INT NOT NULL,"
                + "x_position INT NOT NULL,"
                + "y_position INT NOT NULL,"
                + "board_id INT NOT NULL,"
                + "CONSTRAINT pk PRIMARY KEY (board_id, x_position, y_position),"
                + "CONSTRAINT fkboard_id FOREIGN KEY(board_id) REFERENCES boards (board_id)"
                + ");";
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             Statement statement = connection.createStatement();) {
            statement.executeUpdate(createBoardsTable);
            statement.executeUpdate(createBoardValuesTable);
            logger.info("tables already exists");
        } catch (SQLException e) {
            logger.catching(Level.ERROR, new DatabaseException(e));
            throw new DatabaseException("jdbc_creation_msg");
        }
    }
}
