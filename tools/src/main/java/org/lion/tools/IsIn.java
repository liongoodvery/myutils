package org.lion.tools;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IsIn {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.exit(1);
        }


        contains(args[0]);
    }

    static ArrayList<String> list;
    static Set<String> strings;

    static {
        list = getStrings();
        strings = list.stream()
                .map(String::toLowerCase)
                .map(IsIn::stringH)
                .collect(Collectors.toSet());

    }

    public static boolean contains(String name) {


        boolean ret = false;
        for (String s : strings) {
            if (s.contains(stringH(name).toLowerCase())) {
                System.out.println(s);
                ret = true;
            }
        }

        return ret;
    }

    private static ArrayList<String> getStrings() {
        List<String> files = new ArrayList<>();
        files.add("/mnt/heap/vv/all");
        files.add("/mnt/heap/vv/all_file");
        ArrayList<String> list = new ArrayList<>();
        for (String file : files) {
            list.addAll(readFileLines(file));
        }
        return list;
    }

    public static List<String> readFileLines(String file) {
        try {
            return Files.readAllLines(Paths.get(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private static String stringH(String s) {
        if (!s.contains("/")) {
            return s;
        }
        s = StringUtils.substringAfterLast(s, "/");
        String replace = StringUtils.replace(s, "_", "");
        replace = StringUtils.replace(replace, "-", "");
        replace = StringUtils.replace(replace, " ", "");
        return replace;
    }
}
