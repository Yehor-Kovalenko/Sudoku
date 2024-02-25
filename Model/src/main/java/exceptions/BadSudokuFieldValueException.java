package exceptions;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * BadSudokuFieldValueException is thrown whent the SudokuField class has an unappropriate value
 * namely not in the range from 0 to 9
 */
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
