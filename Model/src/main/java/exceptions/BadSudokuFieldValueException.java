package exceptions;

import java.util.Locale;
import java.util.ResourceBundle;

public class BadSudokuFieldValueException extends IllegalArgumentException {

    public BadSudokuFieldValueException(String key) {
        super(ResourceBundle.getBundle("ExceptionBundle", Locale.getDefault()).getString(key));
    }

    public BadSudokuFieldValueException(Throwable e) {
        super(e.getMessage());
    }

    public BadSudokuFieldValueException() {
        super();
    }
}
