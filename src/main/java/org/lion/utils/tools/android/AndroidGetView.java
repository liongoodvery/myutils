package org.lion.utils.tools.android;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by more on 2016-05-06 18:46:22.
 * The Program init the Android view
 */
public class AndroidGetView {
    public static void main(String[] args) throws Exception {
        //accept only one argument , the source file full path.
        if (args.length != 1) {
            for (String arg : args) {
                System.out.println(arg);
            }
            Thread.sleep(200);
            throw new RuntimeException("Usage java AndroidGetView filePath ");
        }
        Path source = Paths.get(args[0]);
        List<String> lines = Files.readAllLines(source,Charset.forName("UTF-8") );
        //the line include android views
        List<String[]> views = new ArrayList<>();
        // the number line the activity started
        int breadLineNumber = -1;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] splits = line.trim().split("\\s+");
            // Get views
            if (line.contains("private") && splits.length == 3) {
                if (splits[2].endsWith(";")) {
                    String three = splits[2];
                    splits[2] = three.substring(0, three.length() - 1);
                    views.add(splits);
                }
            }
            //end condition
            if (line.contains("setContentView(R.layout")) {
                breadLineNumber = i;
                break;
            }
        }
        if (-1 == breadLineNumber) {
            throw new RuntimeException("Do not have onCreate Line");
        }

        List<String> writtenLines = new ArrayList<>();
        for (String[] splits : views) {
            String line = String.format("%s=(%s)findViewById(R.id.%s);", splits[2], splits[1], splits[2]);
            writtenLines.add(line);
        }
        for (String line : writtenLines) {
            System.out.println(line);
        }

//        Files.copy(source,
//                   Paths.get("z:/tmp", String.format("%s-%d", source.toFile().getName(), System.currentTimeMillis()))
//                , StandardCopyOption.REPLACE_EXISTING);
//        Path outFile = Paths.get(args[0]);
//
//        writeFile(outFile, lines.subList(0, breadLineNumber + 1), false);
//        writeFile(outFile, writtenLines, true);
//        writeFile(outFile, lines.subList(breadLineNumber + 1, lines.size()), true);
//
//        System.out.printf("write %s lines\n", writtenLines.size());
//        System.in.read();

    }

//    private static void writeFile(Path out, Iterable<String> lines, boolean append) throws IOException {
//        Files.write(out, lines,
//                    Charset.defaultCharset(),
//                    append ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING);
//    }

}
