package org.lion.utils.tools;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class OpenCvCC {
    private String start = "/home/lion/OpenCV3-examples/src/【8】第八章";
    private String dest = "/home/lion/msrc/opencv/008";
    private String cmakeTemp = "add_executable(%s %s.cpp)\n" +
            "target_link_libraries(%s ${OPENCV_LIB})";

    public static void main(String[] args) {
        try {
            new OpenCvCC().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void run() throws Exception {
        Files.newDirectoryStream(Paths.get(start))
                .forEach(path -> {
                    try {
                        handle(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void handle(Path dir) throws IOException {
        System.out.println("handle" + dir);
        List<Path> cpp = new ArrayList<>();
        List<Path> pic = new ArrayList<>();
        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".cpp")) {
                    cpp.add(file);
                }
                if (file.toString().endsWith(".jpg")) {
                    pic.add(file);
                }
                return super.visitFile(file, attrs);
            }
        });

        int num = 0;
        for (Path path : cpp) {
            String s = path.getFileName().toString();
            int index = s.indexOf("_");
            if (index != -1) {
                try {
                    num = Integer.valueOf(s.substring(0, index));
                } catch (Exception e) {

                }
            }

        }

        if (num == 0) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Path path : cpp) {
            String fileName = path.getFileName().toString();
            Files.copy(path, Paths.get(dest, fileName), StandardCopyOption.REPLACE_EXISTING);
            int index = fileName.lastIndexOf(".");
            if (index != -1) {
                String s = fileName.substring(0, index);
                sb.append(String.format(cmakeTemp, s, s, s)).append("\n\n");
            }
        }

        Files.createTempDirectory(Paths.get("/tmp"), "opencv");

        for (int i = 0; i < pic.size(); i++) {
            Path path = pic.get(i);
            Path tar = Paths.get(dest, num + "_" + path.getFileName().toString());
            Files.copy(path, tar, StandardCopyOption.REPLACE_EXISTING);
        }

        Files.write(Paths.get(dest, "CMakeLists.txt"), sb.toString().getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
    }
}
