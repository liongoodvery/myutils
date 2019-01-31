package org.lion;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class Main {
    public static void main(String[] args) {

        try {
            walk();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Path walk() throws IOException {
        return Files.walkFileTree(Paths.get("/home/lion/Desktop/abc"), new SimpleFileVisitor<Path>() {
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                new ReplaceText(file.toAbsolutePath().toString(),
                        "/home/lion/Desktop/r/" + file.getFileName(),
                        "www.allitebooks.com",
                        "").run();
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
