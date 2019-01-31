package org.lion.utils.tools.jar2maven;

import org.lion.utils.tools.jar2maven.pojo.MavenLocation;

import java.util.ArrayList;
import java.util.List;

public class JarInfo {
        String path;
        MavenLocation location;
        final List<MavenLocation> penddingLocations = new ArrayList<>();
    }