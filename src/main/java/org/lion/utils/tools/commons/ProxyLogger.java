package org.lion.utils.tools.commons;

import org.lion.utils.Commons;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by more on 2016-08-13 16:47:36.
 */
public abstract class ProxyLogger
{

    private String filename;
    private String extra_info;
    private String platform;

    public ProxyLogger(String[] args)
    {
        if (args.length != 2)
        {
            System.out.println(args.length);
            Commons.printArray(args);
            throw new IllegalArgumentException("Usage: class_file filename extra_info");
        }
        filename = args[0];
        extra_info = args[1];
        platform = getPlatform();
    }

    public void log() throws IOException
    {
        writeOut(getLines(),filename);
    }

    protected abstract void writeOut(List<String> lines,String filename);


    public List<String> getLines() throws IOException
    {
        List<String> lines = Files.readAllLines(Paths.get(filename), Charset.forName("UTF-8"));
        for (int i = 0; i < lines.size(); i++)
        {
            String line = lines.get(i);
            if (line.contains("super."))
            {
                int start = line.indexOf(".");
                if (-1 == start)
                    continue;
                int end = line.indexOf("(");
                if (-1 == end)
                    continue;
//                System.out.println("=========="+line);
                String function = line.substring(start + 1, end);
                line = addInfo( function) + line;
//                System.out.println(line);
                lines.set(i, line);
            }
        }
        return lines;
    }


    public String addInfo(String function)
    {
        switch (platform)
        {
            case "android":
                return getAndroidFormat(function);
            case "java":
                return getJavaFormat(function);
            default:
                throw new IllegalArgumentException("wrong platform");
        }
    }

    protected abstract String getJavaFormat(String function);

    protected abstract String getAndroidFormat(String function);


    private String getPlatform()
    {
        if (extra_info.contains("android"))
            return "android";
        else
            return "java";
    }



}
