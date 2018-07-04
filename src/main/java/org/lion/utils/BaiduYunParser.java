package org.lion.utils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class BaiduYunParser {
    public static void main(String[] args) {

        String root = "/home/lion/vmshare/Downloads";
        try {
            Files.walkFileTree(Paths.get(root), new SimpleFileVisitor<Path>() {
                int c = 0;

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (c++ >= 7) {
                        List<String> lines = Files.readAllLines(file);
                        if (lines.size() > 0) {
                            String line = lines.get(0);
                            System.out.println();
                            System.out.println(line);
                        }
                    }

                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
