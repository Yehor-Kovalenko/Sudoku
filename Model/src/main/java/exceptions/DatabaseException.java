package exceptions;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * DatabaseException is thrown while workin with the database,
 * provides information on a database access error or other errors.
 */
public class DatabaseException extends SQLException {
    public DatabaseException(String key) {
        super(ResourceBundle.getBundle("ExceptionBundle", Locale.getDefault()).getString(key));
    }

    public DatabaseException(Throwable e) {
        super(e.getMessage());
    }
}
