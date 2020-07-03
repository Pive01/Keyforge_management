package com.KeyforgeManagement.application.common;


import android.content.Context;
import android.util.DisplayMetrics;

import com.KeyforgeManagement.application.data.model.Deck;
import com.KeyforgeManagement.application.data.model.adaptation.NewDeckFormat;

import java.util.ArrayList;
import java.util.List;

public final class Utils {

    public static String enumValueToString(String value) {
        String[] tokens = value.split("_");
        String[] nameTokens = new String[tokens.length];

        for (int i = 0; i < tokens.length; i++) {
            nameTokens[i] = tokens[i].charAt(0) + tokens[i].substring(1).toLowerCase();
        }
        return join(" ", nameTokens);
    }

    public static String join(String delimiter, String[] strings) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length - 1; i++) {
            sb.append(strings[i]).append(delimiter);
        }
        return sb.append(strings[strings.length - 1]).toString();
    }

    public static int absolute(int num) {
        return num < 0 ? 0 : num;
    }

    public static List<Deck> convertToOldList(List<NewDeckFormat> newList) {
        List<Deck> oldList = new ArrayList<>();
        newList.forEach(item -> oldList.add(item.convertToOld()));
        return oldList;
    }

    public static float convertDpToPixel(float dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    private Utils() {
    }
}
