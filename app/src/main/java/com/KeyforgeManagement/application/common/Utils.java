package com.KeyforgeManagement.application.common;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    private Utils() {
    }


    public static boolean checkForUpdate(Context c) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = preferences.edit();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String curDate = formatter.format(Calendar.getInstance().getTime());
        if (!preferences.contains("Date")) {
            editor.putString("Date", curDate);
            editor.apply();
            return true;
        }
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = formatter.parse(curDate);
            date2 = formatter.parse(preferences.getString("Date", ""));
        } catch (ParseException e) {
            return true;
        }
        long diff = date2.getTime() - date1.getTime();

        return (diff / (1000 * 60 * 60 * 24)) >= 3;
    }
}
