package exceptions;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * CloningException is thrown when the copy() method is impossible to call because the object has some fields
 * that are not cloneable
 */
public class CloningException extends CloneNotSupportedException {

    public CloningException(String key) {
        super(ResourceBundle.getBundle("ExceptionBundle", Locale.getDefault()).getString(key));
    }

    public CloningException(Throwable e) {
        super(e.getMessage());
    }
}
