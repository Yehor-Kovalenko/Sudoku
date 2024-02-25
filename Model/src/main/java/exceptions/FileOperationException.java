package exceptions;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * FileOperationException is thrown while working with the fileStream,
 * provides information on file access errors etc.
 */
public class FileOperationException extends RuntimeException {
    public FileOperationException(String key) {
        super(ResourceBundle.getBundle("ExceptionBundle", Locale.getDefault()).getString(key));
    }

    public FileOperationException(Throwable cause) {
        super(cause);
    }
}
