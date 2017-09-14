package org.lion.utils.tools.android;

import org.lion.utils.Commons;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by more on 2016-09-02 09:39:48.
 */
public class RSee {
    public Map<Integer, String> R = new HashMap<>();


    public static void main(String[] args)  {
        try {
            new RSee().parse(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parse(String[] args) throws IOException {
        args = new String[2];
        args[0] = "z:/R.java";
        args[1] = "Z:\\gifttalk";
        Path rPath = Paths.get(args[0]);
        Path root = Paths.get(args[1]);
        R = Commons.parseR(rPath);
        replace(root);
    }

    Path currentFile = null;

    private void replace(Path root) throws IOException {
        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                currentFile = file;
                handleFile(file);
                return super.visitFile(file, attrs);
            }
        });
    }

    int lineNo = 0;

    private void handleFile(Path file) throws IOException {
        List<String> lines = Files.readAllLines(file, Charset.forName("UTF-8"));
        for (int i = 0; i < lines.size(); i++) {
            lineNo = i;
            lines.set(i, handleLine(lines.get(i)));
        }
        Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.TRUNCATE_EXISTING);
    }


    String outFormat = "file=%s,lineNo=%s,%s =====> %s";
    String eFormat = "=============file=%s,lineNo=%s,%s";

    private String handleLine(String line) {
        String str = line;
        Pattern pattern = Pattern.compile("-?\\d*");

        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            String group = matcher.group();
            if (group.length() == 10) {

                try {
                    String replacement = R.get(Integer.parseInt(group));
                    if (replacement != null) {
                        System.out.println(String.format(
                                outFormat,
                                currentFile.toAbsolutePath(),
                                lineNo,
                                group,
                                replacement));
                        str = str.replace(group, replacement);
                    }
                } catch (Exception e) {
                    System.out.println(String.format(
                            eFormat,
                            currentFile.toAbsolutePath(),
                            lineNo,
                            line
                    ));
                    e.printStackTrace();
                }
            }

        }

        return str;
    }


}
