package org.lion.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

/**
 * Created by more on 2016-05-28 09:33.
 */
public class HtmlAddMetaUTF {
    public static void main(String[] args) throws IOException {
        Path init = null;
        if (args.length == 0) {
            init = Paths.get(System.getProperty("user.dir"));
        } else {
            init = Paths.get(args[0]);
        }

        Files.walkFileTree(init, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (!file.toString().endsWith("html")) {
                    return FileVisitResult.CONTINUE;
                }
                List<String> lines = Files.readAllLines(file, Charset.forName("UTF-8"));
                for (int i = 0; i < lines.size(); i++) {
                    String line = lines.get(i);
                    if (line.contains("<head>")) {
                        System.out.printf("handle %s\n",file.toString());
                        line = line.replace("<head>", "<head><meta charset=\"utf-8\">");
                        lines.set(i, line);
                        Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
                        return FileVisitResult.CONTINUE;
                    }
                }
                System.out.printf("==========%s do not have a <head> label\n", file.toString());
                return FileVisitResult.CONTINUE;
            }
        });
        System.out.println(Strings.repeat("=", 80));
        System.out.println(System.getProperty("user.dir"));
        Commons.printArray(args);
    }
}
