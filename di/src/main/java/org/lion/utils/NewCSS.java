package org.lion.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Created by more on 2015/3/27.
 * Dark Css Theme
 */
public class NewCSS {
    public static void main(String[] args) throws IOException {
        String fileName;
        fileName = getFileName(args);
        System.out.println(fileName);
        List<String> lines = filterCss(fileName);
        List<String> newLines = new ArrayList<>();
        for (String line : lines) {
            if (!line.isEmpty()) {
                newLines.add(line);
            }
        }
        lines = newLines;
        writeResult(fileName, lines);
    }

    private static void writeResult(String fileName, List<String> lines) throws IOException {
        String commonCss = "";
        if (Files.exists(Paths.get(Paths.get("").toAbsolutePath().toString(), "common.css"))) {
            commonCss = Paths.get(Paths.get("").toAbsolutePath().toString(), "common.css").toString();
        } else if (Files.exists(Paths.get("E:\\OneDrive\\common.css"))) {
            commonCss = "E:\\OneDrive\\common.css";
        }

        Files.copy(Paths.get(fileName), Paths.get(fileName + new Date().getTime()));
        if (commonCss != null && !commonCss.isEmpty()) {
            lines.addAll(Files.readAllLines(Paths.get(commonCss), Charset.forName("utf-8")));
        }
        if (lines.size() > 1 && !lines.get(0).contains("@charset \"UTF-8\";"))
            lines.set(0, "@charset \"UTF-8\";\n" + lines.get(0));
        Files.write(Paths.get(fileName), lines, Charset.forName("utf-8"));

    }

    private static List<String> filterCss(String fileName) throws IOException {
        String emptyString;
        List<String> colors = new ArrayList<>();
        emptyString = "";
        colors.add(" color: #31b6cf;");
        colors.add(" color: #c6af6d;");
        colors.add(" color: #59ccb8;");
        colors.add(" color: #d99d24;");
        colors.add(" color: #c9c954;");
        colors.add(" color: #ce9359;");
        colors.add(" color: #3c3cf0;");
        List<String> lines = Files.readAllLines(Paths.get(fileName), Charset.forName("utf-8"));
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.contains("@") || line.contains("{") || line.contains("}"))
                continue;
            if (line.contains("font-size") ||
                    line.contains("background") ||
                    line.contains("text-align")
//                line.toLowerCase().contains("font-family")
                    ) {
                lines.set(i, emptyString);
                continue;
            }
            if (line.trim().matches("^[Cc]olor.*")) {
                lines.set(i, "\t" + colors.get(i % colors.size()));
            }
        }
        return lines;
    }

    private static String getFileName(String[] args) {
        String root = Paths.get("").toAbsolutePath().toString();
        System.out.println(root);
        String fileName = "";
        InputStream stream;
        Scanner scanner;
        if (args.length > 0) {
            fileName = args[0];
        } else if (Files.exists(Paths.get(root, "core.css"))) {
            fileName = Paths.get(root, "core.css").toString();
        } else if (Files.exists(Paths.get(root, "stylesheet.css"))) {
            fileName = Paths.get(root, "stylesheet.css").toString();
        } else if (Files.exists(Paths.get(root, "style.css"))) {
            fileName = Paths.get(root, "style.css").toString();

        } else if (Files.exists(Paths.get(root, "epub.css"))) {
            fileName = Paths.get(root, "epub.css").toString();
        } else if (Files.exists(Paths.get(root, "css.txt"))) {
            try {
                stream = Files.newInputStream(Paths.get(root, "css.txt"));
                scanner = new Scanner(stream);
                fileName = scanner.nextLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }
}
