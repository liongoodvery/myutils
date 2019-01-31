package org.lion.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class IsInFIle {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.exit(1);
        }

        String file = args[0];
        try {
            List<String> lines = Files.readAllLines(Paths.get(file));
            List<String> out = new ArrayList<>();

            for (String line : lines) {
                if (IsIn.contains(line)) {
                    System.out.println("skip " + line);
                    continue;
                }
                out.add(line);

            }

            LinkedHashSet<String> outSet = new LinkedHashSet<>(out);
            if (outSet.size() != lines.size()) {
                Files.move(Paths.get(file), Paths.get(file + System.currentTimeMillis() + ".bak"));
                Files.write(Paths.get(file), outSet);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
