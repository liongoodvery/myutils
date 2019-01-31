package org.lion.utils.tools.android;

import org.lion.utils.Strings;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by more on 2016-05-08 10:13:54.
 * The class generate code for selected line of a android activity source file.
 */
public class AndroidGetSelectedView {
    public static void main(String[] args) {
        if (args.length < 3) {
            for (String arg : args) {
                System.out.println(arg);
            }
            System.out.flush();
            throw new RuntimeException("Usage java AndroidGetSelectedView source startLine endLine [Optional]");
        }

        Path sourceFile = Paths.get(args[0]);
        int startLine = Integer.parseInt(args[1]);
        int endLine = Integer.parseInt(args[2]);

        List<String> lines = null;
        try {
            lines = Files.readAllLines(sourceFile, Charset.forName("UTF-8"));
            Map<String, String> vars = new LinkedHashMap<>();
            getVars(startLine, endLine, lines, vars);
//        System.out.println(vars);


            for (Map.Entry<String, String> entry : vars.entrySet()) {
                System.out.printf("%s=(%s)findViewById(R.id.%s);\n",
                        entry.getKey(), entry.getValue(),
                        entry.getKey());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
        private EditText et_qq;
        private EditText et_passwd;
     */
    private static void getVars(int startLine, int endLine, List<String> lines, Map<String, String> vars) {
        for (int i = startLine - 1; i < endLine; i++) {
            String line = lines.get(i);
            if (line.contains("=")) {
                continue;
            }
            int i1 = line.lastIndexOf(";");
            if (-1 == i1) {
                continue;
            }

            int i2 = line.lastIndexOf(" ");

            if (-1 == i2) {
                continue;
            }

            if (i2 + 1 > i1) {
                continue;
            }

            String subLine = Strings.trimRight(line.substring(0, i2));
            int i3 = subLine.lastIndexOf(" ");
            if (-1 == i3) {
                return;
            }
            if (i3 + 1 > i2)
                return;
            String key = line.substring(i2 + 1, i1).trim();
            String value = subLine.substring(i3 + 1, i2);
            vars.put(key, value);

        }


    }
}
