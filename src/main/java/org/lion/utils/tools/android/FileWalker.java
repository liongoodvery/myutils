package org.lion.utils.tools.android;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by lion on 17-6-14.
 */
public class FileWalker {
    public static void main(String[] args) {
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void run() throws IOException {
        Path startPoint = Paths.get("/mnt/heap/android/aosp");
        Path out = Paths.get("/home/lion/data/dir_file2");
        Path sql = Paths.get("/home/lion/data/dir_file2.sql");

        ArrayList<String> lines = new ArrayList<>();
        ArrayList<String> sqlLines = new ArrayList<>();
        String sql_format = " INSERT INTO `src_path`(path_id, path_name) VALUES ('%s','%s') ;";

        SimpleFileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (lines.size() >= 1024) {
                    Files.write(out, lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                    lines.clear();
                }

                if (sqlLines.size() >= 1024) {
                    Files.write(sql, sqlLines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                    sqlLines.clear();
                }

                Path relativize = startPoint.relativize(file);
                String pathName = relativize.toString();
                if (pathName.startsWith(".") ||
                        pathName.startsWith("prebuilts/") ||
                        pathName.contains(".repo") ||
                        pathName.contains(".git") ||
                        pathName.startsWith("out/")) {

                } else {
                    lines.add(pathName);
                    sqlLines.add(String.format(sql_format, UUID.randomUUID().toString(), pathName));
                }

                return super.visitFile(file, attrs);
            }
        };

        Files.walkFileTree(startPoint, visitor);
        Files.write(out, lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        Files.write(sql, sqlLines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
