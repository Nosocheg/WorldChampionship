package com.mabel.worldchampionship.ui.data;

import com.mabel.worldchampionship.R;

public class FlagFactory {

    public static int getFlagRes(String name) {
        switch (name) {
            case "Нидерланды": return R.drawable.netherlands;
            case "Сенегал": return R.drawable.senegal;
            case "Аргентина": return R.drawable.argentina;
            case "Дания": return R.drawable.denmark;
            case "Эквадор": return R.drawable.ekvador;
            case "Англия": return R.drawable.england;
            case "Франция": return R.drawable.france;
            case "Иран": return R.drawable.iran;
            case "Катар": return R.drawable.katar;
            case "Мексика": return R.drawable.mexica;
            case "Польша": return R.drawable.poland;
            case "Саудовская Аравия": return R.drawable.saud_arabia;
            case "Тунис": return R.drawable.tunis;
            case "США": return R.drawable.usa;
            default: return R.drawable.unknoun_flag;
        }
    }
}
