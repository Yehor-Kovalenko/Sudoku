package AppBundle;

import java.util.ListResourceBundle;

public class AppBundle_pl_PL extends ListResourceBundle {
    protected Object[][] getContents() {
        return new Object[][] {
                {"language", "Język"},
                {"level1", "Łatwo"},
                {"level2", "Średnio"},
                {"level3", "Ciężko"},
                {"load_board", "Załadować Grę Sudoku"},
                {"reset_board", "Odnow Grę"},
                {"save_board", "Zachowaj Grę Sudoku"},
                {"show_answers", "Pokaż odpowiedzi"},
                {"title", "Sudoku"},
                {"load_dialog_header", "Załadować Grę Sudoku"},
                {"load_dialog_text", "Wprowadź nazwę gry, którą byś chciał(-a) załadować:"},
                {"save_dialog_header", "Zachowaj Grę Sudoku"},
                {"save_dialog_text", "Wprowadź nazwę, pod którą byś chciał(-a) zachować swoją grę:"}
        };
    }
}