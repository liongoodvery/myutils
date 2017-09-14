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
 * Created by more on 2016-05-08 12:17:13.
 * The class generate code according the ids in the android layout xml file.
 */
public class AndroidLayoutPhaseID {

    public static void main(String[] args)  {
        if (args.length != 1) {
            for (String arg : args) {
                System.out.println(arg);
            }
            throw new RuntimeException("Usage java AndroidLayoutPhaseID filePath");

        }
        try {
            run(args[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void run(String arg) throws IOException {
        Path source = Paths.get(arg);
        List<String> lines = Files.readAllLines(source, Charset.forName("UTF-8"));
        Map<String, String> vars = new LinkedHashMap<>();

//        System.out.println(source.getFileName());
        String fileName = source.getFileName().toString();
        boolean isItem = fileName.startsWith("item");
        getVars(lines, vars);
        StringBuilder sb_declare = new StringBuilder();
        StringBuilder sb_assign = new StringBuilder();
        StringBuilder sb_assign_view = new StringBuilder();
        StringBuilder sb_assign_convertView = new StringBuilder();
        StringBuilder sb_assign_holder = new StringBuilder();
        StringBuilder sb_holder_set = new StringBuilder();
        StringBuilder sb_holder_declare = new StringBuilder();

        sb_holder_declare.append("private class ViewHolder{\n");
        for (Map.Entry<String, String> entry : vars.entrySet()) {
            String resId = entry.getKey();
            String viewType = entry.getValue();
            sb_declare.append(String.format("private %s %s;\n", viewType, nameDroid(resId)));
            sb_assign.append(String.format("%s=(%s)findViewById(R.id.%s);\n", nameDroid(resId), viewType, resId));
            sb_assign_view.append(String.format("%s %s = (%s)view.findViewById(R.id.%s);\n", viewType, resId, viewType, resId));
            if (isItem) {
                sb_assign_convertView.append(String.format("%s %s = (%s)convertView.findViewById(R.id.%s);\n", viewType, resId, viewType, resId));
                sb_assign_holder.append(String.format("holder.%s = (%s)convertView.findViewById(R.id.%s);\n", resId, viewType, resId));
                sb_holder_declare.append(String.format("public %s %s;\n", viewType, resId));
                sb_holder_set.append(String.format("holder.%s;\n", resId));
            }

        }

        sb_holder_declare.append("}\n");
        System.out.println(sb_declare);
        System.out.println(Strings.repeat("=", 60));
        System.out.println(sb_assign);
        System.out.println(Strings.repeat("=", 60));
        System.out.println(sb_assign_view);
        System.out.println(Strings.repeat("=", 60));
        if (isItem) {
            System.out.println(sb_assign_convertView);
            System.out.println(Strings.repeat("=", 60));
            System.out.println(sb_holder_declare);
            System.out.println(Strings.repeat("=", 60));
            StringBuilder sb_get_view = new StringBuilder();
            sb_get_view.append("ViewHolder holder = null;\n")
                       .append(" if (convertView == null) {\n")
                       .append("convertView = View.inflate(mContext, R.layout.")
                       .append(fileName.substring(0, fileName.lastIndexOf(".")))
                       .append(", null);\n")
                       .append(" holder = new ViewHolder();\n")
                       .append(sb_assign_holder)
                       .append(" \n convertView.setTag(holder);\n")
                       .append("  } else {\n")
                       .append(" holder = (ViewHolder) convertView.getTag();\n")
                       .append("}\n")
                       .append(sb_holder_set)
                       .append("\nreturn convertView;\n");
            System.out.println(sb_get_view);
        }
    }

    private static String nameDroid(String str) {
        if (str == null || str.length() == 0) {
            throw new RuntimeException("nameDroid: argument can not be null or empty");
        }
        return "m" + str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private static void getVars(List<String> lines, Map<String, String> vars) {
        for (int i = 0; i < lines.size(); ++i) {
            String line = lines.get(i);
            if (line.contains("<!--")) {
                continue;
            }
            if (line.contains("android:id=\"@+id/")) {
                if (i - 1 < 0) {
                    continue;
                }
                String key = getKey(line);
                if (key == null)
                    continue;
                String value = getValue(lines.get(i - 1));

                if (value != null) {
                    vars.put(key, value);
                }
            }
        }
    }

    private static String getValue(String line) {
        line = Strings.trimLeft(line);
        int i2 = line.indexOf("<");
        if (i2 == -1) {
            return null;
        }
        int i1 = line.indexOf(" ");
        if (i1 == -1) {
            i1 = line.length();
        }

        if (i2 + 1 > i1) {
            return null;
        }
        return line.substring(i2 + 1, i1);
    }

    private static String getKey(String line) {
        int i1 = line.lastIndexOf("\"");
        if (i1 == -1) {
            return null;
        }
        int i2 = line.lastIndexOf("/");
        if (i2 == -1) {
            return null;
        }

        if (i2 + 1 > i1) {
            return null;
        }

        return line.substring(i2 + 1, i1);
    }
}
