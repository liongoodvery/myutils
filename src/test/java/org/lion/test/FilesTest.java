package org.lion.test;

import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by more on 2016-06-06 09:04:39.
 */
public class FilesTest {
    @Test
    public void test27() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("z:/abc.txt"), Charset.forName("gbk"));
        Files.write(Paths.get("z:/def.txt"), lines, Charset.forName("gbk"));
    }

    @Test
    public void test20() throws Exception {
        int count = 127;
        for (int i = 0; i < count; i++) {
            System.out.println(i);
            System.out.println((char) i);
        }
    }
}
