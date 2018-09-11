package org.lion.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HttpUrlReader {
    public static void main(String[] args) {
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void run() throws IOException {
        List<String> list = Files.readAllLines(Paths.get("/home/lion/tmp/abc"));
        Pattern pattern = Pattern.compile("href=\"(http://katfile.com/.*?)\"");
        Set<String> collect = list.stream()
                .filter(s -> s.contains("http://katfile.com"))
                .flatMap(s -> {
                    Matcher matcher = pattern.matcher(s);
                    List<String> stringList = new ArrayList<>();
                    while (matcher.find()) {
                        stringList.add(matcher.group(1));
                    }

                    return stringList.stream();
                }).distinct()
                .collect(Collectors.toSet());

        String s = "/mnt/heap/vv/" + System.currentTimeMillis();
        Files.write(Paths.get(s), collect);
    }
}
