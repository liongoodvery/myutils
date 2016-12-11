package org.lion.utils.tools.commons;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * Created by more on 2016/12/11 0011.
 */
public class WriteMdCode {

    private static List<String> lines;

    public static void main(String[] args) throws IOException {
        String pwd = System.getProperty("user.dir");
        String tmp = System.getenv("tmp");
        System.out.println("write file at "+tmp);
        String[] filter=System.getProperty("filter").split("-");
        Set<String> filterset = new HashSet<>();
        for (String s : filter) {
            filterset.add(s);
        }
        Path out = null;
        Path log = null;
        if (tmp != null) {
            out = Paths.get(tmp,"out.md");
            log = Paths.get(tmp,"md.log");
        }
        Path path = null;
        if (pwd !=null){
            path = Paths.get(pwd);
        }

        if (path ==null){
            return;
        }

        ArrayList<String> ret = new ArrayList<>();
        final Path finalLog = log;
        Files.walkFileTree(path,new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String fileName = file.toString();
                int lastIndexOf = fileName.lastIndexOf(".");
                if (lastIndexOf == -1 || lastIndexOf+1 > fileName.length()) {
                    return FileVisitResult.CONTINUE;
                }

                String ext = fileName.substring(lastIndexOf+1);

                if (filterset.contains(ext)){

                    String msg = "handle file " + file + "\n";
                    if (finalLog != null) {
                        Files.write(finalLog,msg.getBytes(),StandardOpenOption.CREATE,StandardOpenOption.APPEND);
                    }else {
                        System.out.println(msg);
                    }
                    handleFile(file,ret,ext);
                }
                return FileVisitResult.CONTINUE;
            }
        });
        for (int i = ret.size()-1;i>=0;--i){
            if (ret.get(i).startsWith("package ")) {
                ret.remove(i);
            }
        }
        if (out != null) {
            Files.write(out, ret, Charset.forName("UTF-8"), StandardOpenOption.CREATE ,StandardOpenOption.TRUNCATE_EXISTING);
        } else {
            for (String s : ret) {
                System.out.println(s);
            }
        }
    }

    private static void handleFile(Path file, ArrayList<String> ret,String ext) {
        ret.add("\n");
        ret.add("```"+ext);
        try {
            ret.add("//filename: "+file.getFileName());
            lines = Files.readAllLines(file);
            if (lines.size()>5){
                for (int i = 5; i >= 0; i--) {
                    if (lines.get(i).trim().isEmpty()){
                        lines.remove(i);
                    }
                }
            }
            ret.addAll(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ret.add("```");
        ret.add("\n");
    }
}
