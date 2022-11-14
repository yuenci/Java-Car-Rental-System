package com.example.car_rental_sys.ToolsLib;

public class StringTools {
    // capitalize  a word
    public static String capitalizeWord(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    // capitalize a sentence of words
    public static String capitalizeFirstLetter(String str) {
        String[] words = str.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }
        return result.toString().trim();
    }

    public static String replaceSpacialChar(String str) {
        String regEx = "[\n`~!@#$%^&*()+=|{}':;,\\[\\].<>/?！￥（）【】‘；：”“’。， 、？]";
        return str.replaceAll(regEx, " ");
    }

    public static boolean ifStringContainsNumberAndSpecialCharacter(String str) {
        String regEx = "[\n`~!@#$%^&*()+=|{}':;,\\[\\].<>/?！￥（）【】‘；：”“’。， 、？]";
        return str.matches(".*\\d+.*") || str.matches(regEx);
    }
}
