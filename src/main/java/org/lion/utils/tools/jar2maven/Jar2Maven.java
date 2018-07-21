package org.lion.utils.tools.jar2maven;

import org.apache.commons.lang3.StringUtils;
import org.lion.utils.tools.jar2maven.pojo.MavenLocation;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Jar2Maven {

    public static void main(String[] args) {
        Path root = null;
        if (args.length == 1) {
            root = Paths.get(args[0]);
            if (!Files.exists(root)) {
                throw new RuntimeException(root + " does no exist");
            }
        } else {
            root = Paths.get(System.getProperty("user.dir"));
        }

        new Jar2Maven().run(root);

    }


    public void run(Path root) {
        try {
            List<JarInfo> jarInfos = walkResult(root);
            LocalLocationSolver locationSolver = new LocalLocationSolver();
            NetworkLocationSolver networkLocationSolver = new NetworkLocationSolver();
            CacheLocationResolver cacheLocationResolver = new CacheLocationResolver("/home/lion/config/jar2maven.cache.db");
            for (JarInfo jarInfo : jarInfos) {
                if (cacheLocationResolver.solve(jarInfo) ||
                        locationSolver.solve(jarInfo) ||
                        networkLocationSolver.solve(jarInfo)) {
                } else {
                    System.err.println(jarInfo.path + "not solved");
                }
            }

            output(jarInfos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void output(List<JarInfo> jarInfos) throws IOException {
        List<String> lines = new ArrayList<>();
        List<String> report = new ArrayList<>();

        for (JarInfo jarInfo : jarInfos) {
            StringBuilder sb = new StringBuilder();
            sb.append(StringUtils.substringAfterLast(jarInfo.path, "/"));
            sb.append("=>");
            if (checkInfo(jarInfo)) {
                MavenLocation l = jarInfo.location;
                lines.add(l.toString());
                sb.append(l.getGroupId() + ":" + l.getArtifactId() + ":" + l.getVersion());
            }

            if (jarInfo.penddingLocations.size() > 0) {
                sb.append("[");
                lines.add("        <!--");
                for (MavenLocation l : jarInfo.penddingLocations) {

                    sb.append(l.getGroupId() + ":" + l.getArtifactId() + ":" + l.getVersion());
                    sb.append("\t,");
                    lines.add(l.toString());
                }
                lines.add("        -->");
                sb.append("]");

            }

            report.add(sb.toString());
        }

        long timeMillis = System.currentTimeMillis();
        String reportFile = "/tmp/" + "jar2maven.report." + timeMillis;
        String outFile = "/tmp/" + "jar2maven.out." + timeMillis + ".xml";
        Files.write(Paths.get(reportFile), report);
        Files.write(Paths.get(outFile), lines);
        System.out.println("===========================");
        System.out.println("write report to " + reportFile);
        System.out.println("write result to " + outFile);
        System.out.println("===========================");
    }


    private List<JarInfo> walkResult(Path root) throws IOException {
        List<JarInfo> jarInfos = new ArrayList<>();
        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".jar")) {
                    JarInfo jarInfo = new JarInfo();
                    jarInfo.path = file.toAbsolutePath().toString();
                    jarInfos.add(jarInfo);
                }

                return super.visitFile(file, attrs);
            }
        });
        return jarInfos;
    }


    public static boolean checkInfo(JarInfo jarInfo) {
        MavenLocation location = jarInfo.location;
        return Objects.nonNull(location) &&
                StringUtils.isNoneEmpty(location.getGroupId(),
                        location.getArtifactId(),
                        location.getVersion());
    }
}
