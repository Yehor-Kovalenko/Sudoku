package exceptions;

import java.util.Locale;
import java.util.ResourceBundle;

public class CloningException extends CloneNotSupportedException {

    public CloningException(String key) {
        super(ResourceBundle.getBundle("ExceptionBundle", Locale.getDefault()).getString(key));
    }

    public CloningException(Throwable e) {
        super(e.getMessage());
    }
}
