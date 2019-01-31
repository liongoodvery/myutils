package org.lion.utils.tools.android;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/9/28 13:54.
 */
public class DeleteUnusedResources {
    public static final String prefix = "E:\\worktrans\\yjyx-android-teacher\\yjyx\\src\\main\\res\\";
    public static String[] dirFilters = {prefix + "anim", prefix + "drawable", prefix + "mipmap", prefix + "layout"};

    public static void main(String[] args)  {
//        if (args.length !=1){
//            throw new IllegalArgumentException("Usage DeleteUnusedResources fileListName");
//        }
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void run() throws IOException {
        Path delListFile = Paths.get("f:/UnusedResources.txt");
        List<String> allLines = Files.readAllLines(delListFile, Charset.forName("UTF-8"));
        Collections.sort(allLines);
        Files.write(Paths.get(delListFile.getParent().toString(), "UnusedResources-sort.txt"), allLines, Charset.forName("UTF-8"),
                StandardOpenOption.CREATE);
        for (String line : allLines) {
            deleteIfNeeded(line);
        }
    }

    private static void deleteIfNeeded(String fileName) throws IOException {
        Path file  = Paths.get(fileName);
        if (!Files.exists(file)||!Files.isRegularFile(file)){
            return;
        }
        for (String dirFilter : dirFilters) {
            if (fileName.startsWith(dirFilter)){
                Files.deleteIfExists(file);
                System.out.printf("delete %s\n",file);
            }
        }
    }


}
