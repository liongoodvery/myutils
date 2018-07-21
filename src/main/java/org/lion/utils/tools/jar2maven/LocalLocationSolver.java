package org.lion.utils.tools.jar2maven;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.lion.utils.tools.jar2maven.pojo.MavenLocation;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class LocalLocationSolver extends LocationSolver {


    public boolean solve(JarInfo jarInfo) {

        try (JarFile jarFile = new JarFile(jarInfo.path)) {
            List<JarEntry> jarEntries = jarFile.stream().collect(Collectors.toList());
            for (JarEntry jarEntry : jarEntries) {
                String name = jarEntry.getName();
                if (name.endsWith("pom.properties")) {
                    jarInfo.location = handleEntry(jarFile, jarEntry);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return Jar2Maven.checkInfo(jarInfo);
    }

    private MavenLocation handleEntry(JarFile jarFile, JarEntry jarEntry) throws IOException {

        InputStream inputStream = jarFile.getInputStream(jarEntry);
        List<String> lines = IOUtils.readLines(inputStream);
        MavenLocation location = new MavenLocation();
        for (String line : lines) {
            if (line.trim().startsWith("#")) continue;
            String[] parts = line.split("=");
            if (parts.length != 2) continue;
            try {
                BeanUtils.setProperty(location, parts[0].trim(), parts[1].trim());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return location;
    }
}