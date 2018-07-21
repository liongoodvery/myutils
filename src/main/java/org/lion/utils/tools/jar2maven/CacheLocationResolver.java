package org.lion.utils.tools.jar2maven;

import org.apache.commons.lang3.StringUtils;
import org.lion.utils.tools.jar2maven.pojo.MavenLocation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheLocationResolver extends LocationSolver {
    private Map<String, String> cache;

    public CacheLocationResolver(String cacheFile) {
        cache = new HashMap<>();
        Path path = Paths.get(cacheFile);
        if (Files.isReadable(path)) {
            try {
                List<String> lines = Files.readAllLines(path);
                for (String line : lines) {
                    handleLine(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleLine(String line) {
        String[] parts = StringUtils.split(line, "=>");
        if (parts.length != 2) {
            return;
        }

        String name = parts[0];
        String ids = parts[1];

        if (StringUtils.split(ids, ":").length == 3) {
            cache.put(name, ids);
        }
    }

    @Override
    public boolean solve(JarInfo jarInfo) {
        String path = jarInfo.path;
        String key = StringUtils.substringAfterLast(path, "/");
        String ids = cache.get(key);
        if (!StringUtils.isEmpty(ids)) {
            String[] parts = StringUtils.split(ids, ":");
            if (parts.length == 3) {
                MavenLocation location = new MavenLocation();
                location.setGroupId(parts[0]);
                location.setArtifactId(parts[1]);
                location.setVersion(parts[2]);
                jarInfo.location = location;
                System.out.println(key + " use cache");
                return true;
            }
        }
        return false;

    }
}
