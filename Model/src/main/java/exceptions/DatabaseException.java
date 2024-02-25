package exceptions;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class DatabaseException extends SQLException {
    public DatabaseException(String key) {
        super(ResourceBundle.getBundle("ExceptionBundle", Locale.getDefault()).getString(key));
    }

    public DatabaseException(Throwable e) {
        super(e.getMessage());
    }
}
