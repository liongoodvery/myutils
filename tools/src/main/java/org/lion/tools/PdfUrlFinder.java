package org.lion.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author: lion
 * @time: 2019-01-30 15:26
 * @email: lion.good.very.first@gmail.com
 */
public class PdfUrlFinder implements Lifecycle {
    private final String baseDir;
    private static Logger logger = Logger.getLogger(HttpUrlFinder.class.getName());

    public PdfUrlFinder(String baseDir) {
        this.baseDir = baseDir;
    }


    public static void main(String[] args) {
        new PdfUrlFinder("/home/lion/Downloads/aaa/all_it").start();
    }

    @Override
    public void start() {
        try {
            doStart();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doStart() throws IOException {
        List<String> list = Files.walk(Paths.get(baseDir))
                .filter(path -> Files.isRegularFile(path))
                .map(path -> {
                    logger.info(path.toString());
                    return path;
                })
                .parallel()
                .map(this::readUrlForm)
                .filter(strings -> !strings.isEmpty())
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
        Path books = Files.createTempFile("all_it_books", ".txt");
        System.out.println(books);
        Files.write(books, list);
    }

    private List<String> readUrlForm(Path path) {
        try {
            return Files.readAllLines(path)
                    .stream()
                    .filter(s -> {
                        return s.contains("http://") && s.contains(".pdf");
                    })
                    .map(this::extractHttpUrl)
                    .filter(s -> !s.isEmpty())
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }


    private List<String> extractHttpUrl(String line) {
        List<String> results = new ArrayList<>();
        Pattern pattern = Pattern.compile(".*(http://file.allitebooks.com/.*?\\.pdf).*");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            results.add(matcher.group(1));
        }
        return results;
    }
}
