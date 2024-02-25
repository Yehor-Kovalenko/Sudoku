package exceptions;

import java.util.Locale;
import java.util.ResourceBundle;

public class BadCompareArgument extends NullPointerException {
    public BadCompareArgument(String key) {
        super(ResourceBundle.getBundle("ExceptionBundle", Locale.getDefault()).getString(key));
    }

    public BadCompareArgument(Throwable e) {
        super(e.getMessage());
    }

    public BadCompareArgument() {
        super();
    }
}
