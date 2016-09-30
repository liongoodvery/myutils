package org.lion.utils.windows;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by more on 2016-07-12 20:37:49.
 */
public class FireWareBlock {
    //.COM;.EXE;.BAT;.CMD;.VBS;.VBE;.JS;.JSE;.WSF;.WSH;.MSC;.PY
    static String[] pathexc = {".msi", ".exe", ".bat", ".cmd", ".lnk", ".vbs", ".msc", ".py"};

    static Set<Path> executeFiles = new TreeSet<>();

    static List<String> scanFiles = new ArrayList<>();

    static Path outFile = Paths.get("outabc.ps");

    static String outFormat  ="//    New-NetFirewallRule -DisplayName \"nvSCPAPISvr.exe_12269b12\" -Direction Outbound -Program \"\" -Action Block -Enabled True -Group \"%s\"\n";
    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            for (String arg : args) {
                scanFiles.add(arg);
            }
        }

        for (String scanFile : scanFiles) {

            Path path = Paths.get(scanFile);
            if (Files.exists(path))
                scanPath(path);
        }
    }

    private static void scanPath(Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file);
                if (isExecutable(file)) {
                    System.out.println(file);
                }
                return FileVisitResult.CONTINUE;
            }

        });

    }

    public static boolean isExecutable(Path path) {
        String str = path.toString();
        for (String suffix : pathexc) {
            if (str.endsWith(suffix)) {
                return true;
            }
        }
        return false;
    }

    //    New-NetFirewallRule -DisplayName "nvSCPAPISvr.exe_12269b12" -Direction Inbound -Program "C:\Program Files (x86)\NVIDIA Corporation\3D Vision\nvSCPAPISvr.exe" -Action Block -Enabled True -Group "C__Program_Files__x86__NVIDIA_Corporation"
//    New-NetFirewallRule -DisplayName "nvSCPAPISvr.exe_12269b12" -Direction Outbound -Program "C:\Program Files (x86)\NVIDIA Corporation\3D Vision\nvSCPAPISvr.exe" -Action Block -Enabled True -Group "C__Program_Files__x86__NVIDIA_Corporation"
    public static void writeResult(Path init) {
        StringBuilder sb = new StringBuilder();

    }
}
