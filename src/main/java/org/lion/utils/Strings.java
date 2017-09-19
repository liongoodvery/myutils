package org.lion.utils;

/**
 * Created by more on 2016-05-08 10:31:26.
 * Some tools of string
 */
public class Strings {
    private Strings() {
    }

    public static String trimRight(CharSequence cs) {
        if (null == cs) {
            return null;
        }
        int index = cs.length();
        for (int i = index - 1; i != -1; --i) {
            if (' ' != cs.charAt(i)) {
                break;
            }
            --index;
        }
        return cs.subSequence(0, index).toString();
    }

    public static String trimLeft(CharSequence cs) {
        if (cs == null) {
            return null;
        }
        int index = 0;
        for (int i = 0; i < cs.length(); i++) {
            if (cs.charAt(i) != ' ') {
                break;
            }
            ++index;
        }
        return cs.subSequence(index, cs.length()).toString();
    }

    public static String repeat(CharSequence cs, int times) {
        if (null == cs) {
            return null;
        }
        if (times <= 0) {
            throw new IllegalArgumentException("times can not be less than 0");
        }
        StringBuilder sb = new StringBuilder(cs.length() * times);
        for (int i = 0; i < times; i++) {
            sb.append(cs);
        }

        return sb.toString();

    }

    public static boolean isEmpty(String s) {
        return null == s || "".equals(s);
    }

    public static String lastPart(String str, String splitor) {
        int index = str.lastIndexOf(splitor);
        return index == -1 ? str : str.substring(index + splitor.length(), str.length());
    }

    public static String underLineToCamel(String prefix, String str) {
        if (isEmpty(str)) {
            return "";
        }

        String[] splits = str.split("_");
        StringBuilder sb = new StringBuilder();
        for (String split : splits) {
            sb.append(capWord(split));
        }

        if (isEmpty(prefix)) {
            String s = sb.substring(0, 1);
            sb.replace(0, 1, s.toLowerCase());
        }else {
            sb.insert(0, prefix);
        }

        return sb.toString();
    }

    public static String capWord(String word) {
        if (null == word || "".equals(word)) {
            return "";
        }

        return word.substring(0, 1).toUpperCase() + word.substring(1, word.length());
    }

    public static boolean isNullOrEmpty(CharSequence cs) {
        return null == cs || cs.length() == 0;
    }
}
