package pl.comp.java;

public class NumberStringConverter extends javafx.util.converter.NumberStringConverter {
    @Override
    public String toString(Number value) {
        if (value == null || value.equals(0)) {
            return "";
        } else {
            return String.valueOf(value);
        }
    }

    @Override
    public Number fromString(String value) {
        if (value.length() != 1 || value == null || value.equals(" ")) {
            return 0;
        } else {
            return Integer.parseInt(value);
        }
    }
}
