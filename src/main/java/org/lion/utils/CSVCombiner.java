package org.lion.utils;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVCombiner {
    private static final List<String> typeFilter = Arrays.asList(
            "java.lang.NullPointerException",
            "java.lang.RuntimeException"

    );

    private static final List<String> prefixFilter = Arrays.asList(


    );


    public static void main(String[] args) {

        String csvPath = "/home/lion/tmp/csv";

        try {
            Map<String, List<Issue>> collect = Files.list(Paths.get(csvPath))
                    .filter(p -> p.toString().endsWith("csv"))
                    .map(CSVCombiner::handle)
                    .flatMap(Function.identity())
                    .collect(Collectors.groupingBy(o -> {
                        String summary = o.getSummary();
                        int index = summary.indexOf(":");
                        return index != -1 ? summary.substring(0, index) : "NOPE";
                    }));

            printCollect(collect, System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printCollect(Map<String, List<Issue>> collect, PrintStream out) {
        collect.entrySet()
                .stream()
                .filter(entry -> !typeFilter.contains(entry.getKey()))
                .sorted((o1, o2) -> {
                    List<Issue> value1 = o2.getValue();
                    List<Issue> value2 = o1.getValue();
                    long o1Count = value1.stream()
                            .mapToInt(Issue::getHappenCount)
                            .count();
                    long o2Count = value2.stream()
                            .mapToInt(Issue::getHappenCount)
                            .count();
                    return (int) (o2Count - o1Count);
                })
                .forEach(entry -> {
                    String s = entry.getKey();
                    List<Issue> issues = entry.getValue();
                    out.println(Strings.repeat("=", 80));
                    out.println(s);
                    out.println(Strings.repeat("=", 80));
                    issues.stream()
                            .sorted(Comparator.comparingInt(Issue::getHappenCount).reversed())
                            .forEach(issue -> printIssue(issue, out));
                    out.println();
                    out.println();
                    out.println();
                });

    }

    private static void printIssue(Issue issue, PrintStream out) {
        out.println();
        out.println();
        out.print("错误摘要:");
        out.println(issue.getSummary());
        out.print("发生次数:");
        out.println(issue.getHappenCount());
        out.print("影响用户数:");
        out.println(issue.getUseCount());
        out.print("首次发生时间:");
        out.println(issue.getFirstTime());
        out.print("版本:");
        out.println(issue.getVersion());
        out.println("错误详情:");
        issue.getDetail().stream()
                .forEach(s -> {
                    out.print("\t");
                    out.println(s);
                });

        out.println();
        out.println(Strings.repeat("-", 80));
        out.println();

    }

    private static Stream<Issue> handle(Path path) {
        try {
            List<String> lines = Files.readAllLines(path, Charset.forName("UTF-16LE"));
            List<String> result = new ArrayList<>();

            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                sb.append(line);
                if ("\"".equals(line)) {
                    result.add(sb.toString());
                    sb.setLength(0);
                }
            }


            return result.stream()
                    .map(s -> {
                        String[] split = s.split("\\t");
                        if (split.length < 6) {
                            return Issue.EMPTY;
                        }


                        return new Issue(
                                split[0],
                                Integer.valueOf(split[1]),
                                Integer.valueOf(split[2]),
                                split[3],
                                split[4],
                                Arrays.asList(split).subList(5, split.length));
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Stream.of(Issue.EMPTY);
    }

    private static final class Issue {
        private final String summary;
        private final Integer happenCount;
        private final Integer useCount;
        private final String firstTime;
        private final String version;
        private final List<String> detail;


        public static final Issue EMPTY = empty();

        public Issue(String summary, Integer happenCount, Integer useCount, String firstTime, String version, List<String> detail) {
            this.summary = summary;
            this.happenCount = happenCount;
            this.useCount = useCount;
            this.firstTime = firstTime;
            this.version = version;
            this.detail = detail;
        }

        public String getSummary() {
            return summary;
        }


        public Integer getHappenCount() {
            return happenCount;
        }


        public Integer getUseCount() {
            return useCount;
        }


        public String getFirstTime() {
            return firstTime;
        }


        public String getVersion() {
            return version;
        }


        public List<String> getDetail() {
            return detail;
        }


        public static Issue empty() {
            return new Issue("", 0, 0, "", "", new ArrayList<>());
        }

        @Override
        public String toString() {
            return "Issue{" +
                    "summary='" + summary + '\'' +
                    ", happenCount=" + happenCount +
                    ", useCount=" + useCount +
                    ", firstTime='" + firstTime + '\'' +
                    ", version='" + version + '\'' +
                    ", detail='" + detail + '\'' +
                    '}';
        }
    }
}
