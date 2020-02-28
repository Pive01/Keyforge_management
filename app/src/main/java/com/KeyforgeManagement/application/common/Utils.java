package com.KeyforgeManagement.application.common;



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
}
