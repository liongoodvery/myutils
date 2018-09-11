package org.lion.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RemoveDumplication {
    public static void main(String[] args) {
        if (args.length == 0) {
            return;
        }
        try {
            run(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void run(String[] args) throws IOException {
        Set<String> ret = new HashSet<>();
        Set<String> exist = new HashSet<>();
        Set<String> src = new HashSet<>();
        src.addAll(Files.readAllLines(Paths.get(args[0])));
        for (int i = 1; i < args.length; i++) {
            exist.addAll(Files.readAllLines(Paths.get(args[i])));
        }

        List<String> list = exist.stream()
                .map(s -> s.replace("http://", "https://"))
                .collect(Collectors.toList());
        for (String s : src) {
            if (list.contains(s.replace("http://", "https://"))) {
                System.out.println("exist: " + s);
                continue;
            }
            ret.add(s);
        }

        ret.forEach(System.out::println);

        String arg = args[0];
        String bak = args[0] + System.currentTimeMillis() + ".bak";

        System.out.println(String.format("move %s to %s", arg, bak));
        Files.move(Paths.get(arg), Paths.get(bak));
        Files.write(Paths.get(arg), ret);
    }
}
