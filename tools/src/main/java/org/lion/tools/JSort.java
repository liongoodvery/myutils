package org.lion.tools;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JSort {
    public static void main(String[] args) {
        try {
            List<String> lines = read(args);
            lines.stream()
                    .map(s -> {
                        try {
                            return s.getBytes("UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        return new byte[0];
                    })
                    .sorted(JSort::bytesCompare)
                    .map(bytes -> {
                        try {
                            return new String(bytes, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        return "";
                    })
                    .forEach(System.out::println);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> read(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();

        for (String s : args) {
            List<String> lines = Files.readAllLines(Paths.get(s));
            list.addAll(lines);
        }
        if (list.isEmpty()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        }

        return list;
    }

    public static int bytesCompare(byte[] o1, byte[] o2) {
        if (o1 == null) {
            return -1;
        }

        if (o2 == null) {
            return 1;
        }

        if (o1.length != o2.length) {
            return o1.length - o2.length;
        }

        int length = o1.length;
        for (int i = 0; i < length; i++) {
            byte b1 = o1[i];
            byte b2 = o2[i];
            if (b1 - b2 != 0) {
                return b1 - b2;
            }
        }

        return 0;
    }

}
