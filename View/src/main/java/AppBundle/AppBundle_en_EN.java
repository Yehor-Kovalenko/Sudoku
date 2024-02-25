package AppBundle;

import java.util.ListResourceBundle;

public class AppBundle_en_EN extends ListResourceBundle {
    protected Object[][] getContents() {
        return new Object[][] {
                {"language", "Language"},
                {"level1", "Easy"},
                {"level2", "Medium"},
                {"level3", "Hard"},
                {"load_board", "Load Sudoku Game"},
                {"reset_board", "Reset Board"},
                {"save_board", "Save Sudoku Game"},
                {"show_answers", "Show Answers"},
                {"title", "The Sudoku Game"},
                {"load_dialog_header", "Load Sudoku Game"},
                {"load_dialog_text", "Enter the name of the game you would like to load:"},
                {"save_dialog_header", "Save Sudoku Game"},
                {"save_dialog_text", "Enter the name under which you would like to save your game:"}
        };
    }
}