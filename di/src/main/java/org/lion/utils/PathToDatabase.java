package org.lion.utils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lion on 2/28/17.
 */
public class PathToDatabase {
    public void toSql(String baseDir, String out) throws IOException {
//        String table = "create table files(id int auto_increment primary key , name varchar(128) not null , filepath varchar(8096) not null);";
        Path pBase = Paths.get(baseDir);
        final String format = "insert into files values(null,'%s','%s');";
        List<String> files = new ArrayList<>();
        Files.walkFileTree(pBase, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String fileName = file.getFileName().toString();
                String path = file.toAbsolutePath().toString();
                if (filter(path) || filter(fileName)) {
                    return FileVisitResult.CONTINUE;
                }
                if (fileName.length() > 128 || path.length() > 8096) {
                    System.out.println(String.format("warning : %s %s exced the limit", fileName, path));
                }


                String insert = String.format(format, fileName, path);
                files.add(insert);
                if (files.size() >= 10240) {
                    write2File(out, files);
                    files.clear();
                }
                return FileVisitResult.CONTINUE;
            }
        });
        write2File(out, files);
    }


    private void write2File(String fileName, List<String> files) throws IOException {
        Files.write(Paths.get(fileName), files, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
    }

    public boolean filter(String s) {
        return s.contains("/.git/") || s.contains("/.repo/") || s.contains("lost+found");

    }

    public static void main(String[] args) throws IOException {
        new PathToDatabase().toSql("/extras", "/home/lion/extras.sql");
    }
}
