package AppBundle;

import java.util.ListResourceBundle;

public class AppBundle_ukr_UA extends ListResourceBundle {
    protected Object[][] getContents() {
        return new Object[][] {
                {"language", "Мова"},
                {"level1", "Легко"},
                {"level2", "Середньо"},
                {"level3", "Важко"},
                {"load_board", "Завантажити Дошку"},
                {"reset_board", "Обнулити дошку"},
                {"save_board", "Зберегти Дошку"},
                {"show_answers", "Показати відповіді"},
                {"title", "Судоку"},
                {"load_dialog_header", "Завантажити Дошку"},
                {"load_dialog_text", "Введіть назву гри, яку ви хочете завантажити:"},
                {"save_dialog_header", "Зберегти Дошку"},
                {"save_dialog_text", "Введіть назву, під якою ви хочете зберегти гру:"}
        };
    }
}