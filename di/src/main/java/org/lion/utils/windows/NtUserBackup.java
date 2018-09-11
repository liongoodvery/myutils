package org.lion.utils.windows;

import org.lion.utils.DateFormatFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

/**
 * Created by more on 2016-05-10 17:23:14.
 * Backup NTUSER or other config file when the system startup .
 * See the script e:/bin/iproc/ntuser_backup.bat
 */
public class NtUserBackup {
    public static void main(String[] args)  {
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void run() throws IOException {
        String root = "C:\\Users\\more";
        String fileName = "NTUSER.zip";
        Path source = Paths.get(root, fileName);
        Path dest = Paths.get(root, "NTUSER", fileName + System.currentTimeMillis());

        try {
            Files.copy(source, dest);
        } catch (IOException e) {
            Path log = Paths.get(root, "ntuser.log.txt");
            Files.write(log,
                        String.format("%s : %s", DateFormatFactory.yyyy_MM_dd_HH_mm_ss.format(new Date()), e.getMessage())
                              .getBytes(),
                        StandardOpenOption.APPEND
                       );
        }
    }
}
