package org.lion.utils.tools.android;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by lion on 9/30/16.
 */
public class ReplaceRClass {
    public static void main(String[] args) throws IOException {
        Files.walkFileTree(Paths.get("/home/lion/work/worktrans/yjyx-android-teacher/yjyx/src/main/java/"),new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".java")){
                    handleFile(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void handleFile(Path file) throws IOException {
        List<String> lines = Files.readAllLines(file);
        List<String>  newLines = new ArrayList<>(lines.size());
        boolean isReplaced = false;
        for (String line : lines) {
            if (line.equals("import edu.yjyx.R;")){
                System.out.println("replace R.java in file " + file);
                newLines.add("import edu.yjyx.teacher.R;");
                isReplaced = true;
            }else {
                newLines.add(line);
            }
        }

        if (isReplaced){
            Files.write(file,newLines, Charset.forName("utf-8"), StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

}
