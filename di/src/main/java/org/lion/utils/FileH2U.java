package org.lion.utils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by more on 2016-06-22 10:57:02.
 */
public class FileH2U {
    public static void main(String[] args) throws IOException {
        final Path init = Paths.get("E:\\Data\\swarm-icon");
        Files.walkFileTree(init, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (Files.isRegularFile(file)) {
                    String fileName = file.getFileName().toString().replace("-", "_");
                    Files.move(file, Paths.get(init.toString(), fileName));
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
