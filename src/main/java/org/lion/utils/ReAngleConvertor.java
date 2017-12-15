package org.lion.utils;

public class ReAngleConvertor {
    public static void main(String[] args) {
        String text = args.length >= 1 ? args[0] : "";
        new ReAngleConvertor().run(text);
    }

    private void run(String text) {
        text = text.replace("&lt;", "<");
        text = text.replace("&gt;", ">");
        System.out.println(text);
    }
}
