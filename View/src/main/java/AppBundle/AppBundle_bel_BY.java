package AppBundle;

import java.util.ListResourceBundle;

public class AppBundle_bel_BY extends ListResourceBundle {
    protected Object[][] getContents() {
        return new Object[][] {
                {"language", "Мова"},
                {"level1", "Лёгка"},
                {"level2", "Сярэдне"},
                {"level3", "Цяжка"},
                {"load_board", "Загрузiць дошку"},
                {"reset_board", "Абнуліць дошку"},
                {"save_board", "Захаваць дошку"},
                {"show_answers", "Паказаць адказы"},
                {"title", "Судоку"},
                {"load_dialog_header", "Загрузiць дошку"},
                {"load_dialog_text", "Увядзіце назву дошкі, якую вы хочаце загрузіць:"},
                {"save_dialog_header", "Захаваць дошку"},
                {"save_dialog_text", "Увядзіце назву, пад якой вы хочаце захаваць сваю дошку:"}
        };
    }
}