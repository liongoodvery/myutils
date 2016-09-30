package org.lion.utils.tools.commons;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by more on 2016-05-07 16:58:58.
 * The Program accepts lineNumbers of a java file and write a to string with a
 * Given format below the last line.
 */
public class VariableToString {
    public static void main(String[] args) throws IOException {

        if (args.length < 4) {
            for (int i = 0; i < args.length; i++) {
                System.out.println(args[i]);
            }
            System.out.flush();
            throw new RuntimeException("Usage java VariableToString source startLine endLine [Optional]");
        }

        Path sourceFile = Paths.get(args[0]);
        int startLine = Integer.parseInt(args[1]);
        int endLine = Integer.parseInt(args[2]);

        List<String> lines = Files.readAllLines(sourceFile,Charset.forName("UTF-8") );
        List<String> vars = new ArrayList<>();
        getVars(startLine, endLine, lines, vars);
        StringBuilder sb_format = new StringBuilder();
        StringBuilder sb_argument = new StringBuilder();
        sb_format.append("\"");
        for (String var : vars) {
            sb_format.append(var).append("=%s, ");
            sb_argument.append(",").append(var);
        }
        sb_format.append("\\n\"");

        String out = getOut(args);

        StringBuilder sb = new StringBuilder();
        sb.append(out)
          .append("String.format(")
          .append(sb_format)
          .append(sb_argument)
          .append(")")
          .append(");");
        System.out.println(sb);


    }

    private static String getOut(String[] args) {
        if (args.length == 4) {
            String sdk = args[3];
            if (sdk.toLowerCase().contains("android")) {
                return "Log.i(TAG, ";
            }
        }
        return "System.out.print(";
    }

    private static void getVars(int startLine, int endLine, List<String> lines, List<String> vars) {
        for (int i = startLine - 1; i < endLine; i++) {
            String line = lines.get(i).trim();

            if (line.contains("=")) {
                int index1 = line.indexOf("=");
                int index2 = line.substring(0, index1).trim().lastIndexOf(" ");
//                System.out.printf("i1=%d,i2=%d,line=%s\n", index1, index2, line);
                if (index2 == -1) {
                    index2 = 0;
                }
                if (index1 > index2) {
                    String s = line.substring(index2, index1);
                    vars.add(s.trim());
                    continue;
                }
            }

            if (line.contains(";")) {
                int index1 = line.indexOf(";");
                System.out.printf("index1=%d,line=%s", index1, line);
                int index2 = line.substring(0, index1).trim().lastIndexOf(" ");
                System.out.printf("i1=%d,i2=%d,line=%s\n", index1, index2, line);
                if (index2 == -1) {
                    index2 = 0;
                }
                if (index1 > index2) {
                    String s = line.substring(index2, index1);
                    vars.add(s.trim());
                }
            }
        }
    }

    private static void get1(int startLine, int endLine, List<String> lines, List<String> vars) {
        for (int i = startLine - 1; i < endLine; i++) {
            String[] splits = lines.get(i).trim().split("\\s+");
            if (splits.length < 2) {
                continue;
            }
            String first = splits[0];
            if (first.equals("private") || first.equals("public") || first.equals("protected"))
                vars.add(splits[2]);
            else
                vars.add(splits[1]);

        }
    }
}
