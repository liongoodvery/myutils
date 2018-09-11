package org.lion.utils.tools.commons;

import org.lion.utils.Commons;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UrlFetcher {
    Path mInPath;
    Path mOutPath;


    public UrlFetcher(Path inPath, Path outPath) {
        this.mInPath = inPath;
        this.mOutPath = outPath;
    }

    public UrlFetcher(Path inPath) {
        this.mInPath = inPath;
    }

    public static void main(String[] args)  {
        try {
            run(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void run(String[] args) throws IOException {
        Path inPath = Paths.get("z:/Bookmarks.html");
        Path outPath = Paths.get("z:/bookmark_host");
        if (args.length >= 1) {
            inPath = Paths.get(args[0]);
        }
        new UrlFetcher(inPath, outPath).fetchUrls();
    }

    public void fetchUrls() throws IOException {
        String str = readLines();
        Set<String> matchLines = match(str);
        Commons.print(matchLines);
        writeOut(matchLines);
    }

    private Set<String> match(String str) {
        Set<String> ret = new HashSet<>();
        Pattern pattern = Pattern.compile("(f|ht){1}(tp|tps):\\/\\/(([\\w-]+\\.)+[\\w-]+)(\\/[-\\w ./?%&=]*)?");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            try {
                ret.add(matcher.group(3));
            } catch (Exception e) {
                System.err.println(matcher.group() + " " + e.getMessage());
            }
        }
        return ret;
    }

    private void writeOut(Set<String> lines) throws IOException {
        if (mOutPath != null) {
            Files.write(mOutPath, lines, Charset.forName("UTF-8"));
        } else {
            for (String line : lines) {
                System.out.println(line);
            }
        }
    }

    private String readLines() throws IOException {
        byte[] bytes = Files.readAllBytes(mInPath);
        return new String(bytes);
    }
}
