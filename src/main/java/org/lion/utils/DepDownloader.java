package org.lion.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class DepDownloader {
    public static void main(String[] args) {
        new DepDownloader().download();
    }
    public void download() {
        try {
            new ProcessBuilder()
                    .command("/bin/sleep 10")
                    .directory(Paths.get("/tmp").toFile())
                    .redirectOutput(new File("/tmp/dddd"))
                    .start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
