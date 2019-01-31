package org.lion.tools;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class NormalFileName {
    public static void main(String[] args) {
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void run() throws IOException {
        Files.walkFileTree(Paths.get("/media/lion/PLUS/2018-12-12"), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String orig = file.toAbsolutePath().toString();
                String result = orig.trim();
                if (!orig.equals(result)) {
                    System.out.println("rename :" + result);
                    Files.move(file, Paths.get(result));
                }

                return FileVisitResult.CONTINUE;
            }
        });
    }
}
