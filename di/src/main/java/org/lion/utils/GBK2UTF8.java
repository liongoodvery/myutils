package org.lion.utils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.nio.file.Files;

/**
 * Created by more on 2016-06-05 19:10:53.
 * Convert GBK files to Utf-8
 * Run this program in the user.dir current work directory.
 * It accepts some extension as its argument such as "Program java cpp"
 */
public class GBK2UTF8 {
    public static void main(String[] args) {
        final List<String> filters = new ArrayList<>();
        if (0 != args.length) {
            Collections.addAll(filters, args);
        }

        Path initPath = Paths.get(System.getProperty("user.dir"));
        try {
            Files.walkFileTree(initPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (filters.contains(Commons.getExtension(file.toString())))
                        Commons.gbk2utf8(file);
                    return FileVisitResult.CONTINUE;
                }


            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
