package org.lion.utils.tools.android;


import org.lion.utils.Strings;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by more on 2016-06-18 10:49:21.
 * Helper to phase sqlite imformation of a model
 */
public class SqliteModelHelper {
    public static void main(String[] args) {
        if (args.length != 3) {
            for (String arg : args) {
                System.out.println(arg);
            }
            throw new RuntimeException("Usage java SqliteModelHelper filePath startLine endLine");
        }

        try {
            run(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void run(String[] args) throws IOException {
        Path path = Paths.get(args[0]);
        int startLine = Integer.parseInt(args[1]);
        int endLine = Integer.parseInt(args[2]);

        List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));
        List<String> vars = getVars(lines, startLine, endLine);
        getDefinition(vars);
        System.out.println("//" + Strings.repeat("=", 80));
        getColumns(vars);
        System.out.println("//" + Strings.repeat("=", 80));
        getColumnIndex(vars);
        System.out.println("//" + Strings.repeat("=", 80));
        createTable(vars);
        System.out.println("//" + Strings.repeat("=", 80));
        insertTable(vars);
    }

    private static void insertTable(List<String> vars) {
    }

    private static void createTable(List<String> vars) {
        StringBuilder sb = new StringBuilder();
        sb.append("public static String CREATE_SQL=").append("\"");
        sb.append("CREATE TABLE $TABLE$ (");
        for (String var : vars) {
            if ("id".equals(var)) {
                sb.append("_id integer primary key autoincrement,");
            } else {
                sb.append(var).append(" text,");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")").append("\";");
        System.out.println(sb);
    }

    private static void getColumnIndex(List<String> vars) {
        String format = "public static int COLUMN_%s_INDEX=%s;\n";
        for (int i = 0; i < vars.size(); i++) {
            System.out.printf(format, vars.get(i).toUpperCase(), i);
        }
    }

    private static void getColumns(List<String> vars) {
        String format = "public static String COLUMN_%s=\"%s\";\n";
        for (String var : vars) {
            if ("id".equals(var)) {
                System.out.printf(format, "ID", "_id");
            } else {
                System.out.printf(format, var.toUpperCase(), var);
            }
        }
    }

    private static void getDefinition(List<String> vars) {
        String format = "public String %s;\n";
        for (String var : vars) {
            System.out.printf(format, var);
        }
    }

    private static List<String> getVars(List<String> lines, int startLine, int endLine) {
        List<String> vars = new ArrayList<>();
        for (int i = startLine - 1; i < endLine; ++i) {
            vars.add(lines.get(i).trim());
        }
        return vars;
    }
}
