package org.lion.utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lion on 17-11-11.
 */
public class MagnetParser {
    public static void main(String[] args) {
        try {
            act();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void act() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("/tmp/abc.html"));
        Pattern pattern = Pattern.compile("href=\"(magnet:.*?)\"");
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                System.out.println(matcher.group(1));
            }

        }
    }
}
