package org.lion.utils.tools.jar2maven;

import org.lion.utils.tools.jar2maven.pojo.MavenLocation;

import java.util.Objects;

public abstract class LocationSolver {

    public abstract boolean solve(JarInfo jarInfo);


}