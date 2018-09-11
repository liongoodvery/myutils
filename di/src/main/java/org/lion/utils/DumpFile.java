package org.lion.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DumpFile {
    public static void main(String[] args) {
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void run() throws IOException {
        List<String> lines = FileUtils.readLines(new File("/home/lion/ram/dir"), "UTF-8");
        HashMap<String, List<String>> map = new HashMap<>();
        for (String line : lines) {
            String name = FilenameUtils.getName(line);
            List<String> list = map.get(name);
            if (list == null) {
                list = new ArrayList<>();
                map.put(name, list);
            }
            list.add(line);
        }

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String name = entry.getKey();
            List<String> files = entry.getValue();
            if (files.size() > 1) {
                System.out.println(name + ":");


                for (String file : files) {
                    System.out.println(file);
                }

                System.out.println(StringUtils.repeat("=", 80));

            }
        }
    }
}
