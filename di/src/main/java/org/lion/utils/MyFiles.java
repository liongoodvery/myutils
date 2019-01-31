package org.lion.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by more on 2016-06-06 08:49:12.
 * Some tools about file ,Something about java.nio.Files
 */
@Deprecated
public class MyFiles {
    private MyFiles() {
    }

    public static List<String> readAllLines(String file, String charset) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset))) {
            List<String> list = new ArrayList<>();
            String line = null;
            while ((line = in.readLine()) != null) {
                list.add(line);
            }
            return list;
        }

    }

    public static void writeAllLines(String file, Iterable<? extends CharSequence> lines, String charset, FileOpenMode mode) throws FileNotFoundException, UnsupportedEncodingException {
        try (PrintStream out = new PrintStream(getOutputStream(file, mode), true, charset)) {
            for (CharSequence line : lines) {
                out.println(line);
            }
        }
    }

    public static void writeAllLines(String file, Iterable<? extends CharSequence> lines, String charset) throws FileNotFoundException, UnsupportedEncodingException {
        writeAllLines(file, lines, charset, FileOpenMode.CREATE);
    }

    private static OutputStream getOutputStream(String file, FileOpenMode mode) throws FileNotFoundException {
        switch (mode) {
            case APPEND:
                return new FileOutputStream(file, true);
            case CREATE:
                return new FileOutputStream(file);

        }
        return null;
    }

}
