package org.lion.utils.tools.commons;

/**
 * Created by lion on 17-5-26.
 */
public class UnderLine2Camel {
    public static void main(String[] args) {

        System.out.println();
        for (String arg : args) {
            String[] splits = arg.split("_");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < splits.length; i++) {
                sb.append(i == 0 ? splits[i] : capWord(splits[i]));
            }
            System.out.println(sb.toString());
        }
    }

    public static String capWord(String word) {
        if (word == null || "".equals(word)) {
            return "";
        }

        String firstChar = word.substring(0, 1);

        return firstChar.toUpperCase() + word.substring(1);
    }
}
