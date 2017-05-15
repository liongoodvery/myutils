package org.lion.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by more on 2016-06-20 08:41:00.
 * The program to count word frequcy in a specific path
 */
public class WordCount {
    private static Map<String, Integer> table;
    private static Path outPath;
    private static IWordFilter wordFilter = new WordFilter();

    public static void main(String[] args) throws Exception {
        Path initPath = Paths.get("E:\\Bin\\Android\\SDK\\docs\\guide\\practices");
        outPath = Paths.get("z:/ret.out");
        table = new TreeMap<>();
        Files.walkFileTree(initPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                List<String> lines = Files.readAllLines(file, Charset.forName("UTF-8"));
                handleLines(lines);
                return FileVisitResult.CONTINUE;
            }
        });

        Commons.printMap(table,20,20,null,null,true);
    }

    private static void handleLines(List<String> lines) {
        for (String line : lines) {
            String[] words = line.split("[^a-zA-Z0-9_-]");
            for (String word : words) {
                if (wordFilter.filter(word)) {
                    add2Map(word.trim().toLowerCase());
                }
            }
        }
    }

    private static void add2Map(String word) {
        if (table.containsKey(word)) {
            table.put(word, table.get(word) + 1);
        } else {
            table.put(word, 1);
        }
    }
private static class WordFilter implements IWordFilter{

    @Override
    public boolean filter(String word) {
        // FIXME: 2016-06-20 
        return true;
    }
}

}
